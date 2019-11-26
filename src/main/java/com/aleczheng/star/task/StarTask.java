package com.aleczheng.star.task;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aleczheng.star.dao.CodeRepositoryDao;
import com.aleczheng.star.dao.HistorySummaryDao;
import com.aleczheng.star.dao.StarActionDao;
import com.aleczheng.star.dao.StargazerDao;
import com.aleczheng.star.po.CodeRepository;
import com.aleczheng.star.po.HistorySummary;
import com.aleczheng.star.po.StarAction;
import com.aleczheng.star.po.Stargazer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

@Component
@EnableScheduling
public class StarTask {

  @Autowired
  private CodeRepositoryDao codeRepositoryDao;

  @Autowired
  private HistorySummaryDao historySummaryDao;

  @Autowired
  private StarActionDao starActionDao;

  @Autowired
  private StargazerDao stargazerDao;

  private String requestTemplate = "";

  private String apiToken = "";

  ObjectMapper objectMapper = new ObjectMapper();

  Logger logger = LoggerFactory.getLogger(StarTask.class);

  @PostConstruct
  public void init() {
    try {
      URL requestTemplateUrl = Resources.getResource("stargazer.clientql");
      this.requestTemplate = Resources.toString(requestTemplateUrl, Charsets.UTF_8);
      URL apiTokenUrl = Resources.getResource("api_token.secret");
      this.apiToken = Resources.toString(apiTokenUrl, Charsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public JsonNode invokeQL(String ql) throws IOException {
    ql = ql.replaceAll("\n", " ");
    String clientQLJson = "{\"query\": \"" + ql + "\"}";
    String response = Request.Post("https://api.github.com/graphql")
        .addHeader("Authorization", "Bearer " + this.apiToken)
        .addHeader("Content-Type", "application/json")
        .bodyString(clientQLJson, ContentType.APPLICATION_JSON)
        .execute().returnContent().asString();

    JsonNode jsonNode = objectMapper.readTree(response);
    return jsonNode;
  }

  @Transactional
  public void crawlCodeRepository(CodeRepository codeRepository) throws IOException, ParseException {
    String userName = codeRepository.getUserName();
    String repositoryName = codeRepository.getRepositoryName();

    String ql = String.format(this.requestTemplate, userName, repositoryName, "null");
    JsonNode res = invokeQL(ql).path("data").path("repository").path("stargazers");
    HistorySummary historySummary = new HistorySummary();
    int totalCount = res.path("totalCount").asInt();
    historySummary.setStarCount(totalCount);
    historySummary.setCodeRepository(codeRepository);

    historySummaryDao.save(historySummary);
    codeRepository.setCurrentStarCount(totalCount);
    codeRepositoryDao.save(codeRepository);

    stargazerDao.batchUpdateShowMark(codeRepository.getId());
    JsonNode stargazerNodes = res.path("edges");
    Date now = new Date();

    int addCount = 0;
    int removeCount = 0;

    while (stargazerNodes.size() > 0) {
      for (JsonNode stargazerNode : stargazerNodes) {
        String userId = stargazerNode.path("node").path("id").asText();
        String userLogin = stargazerNode.path("node").path("login").asText();
        String starredAtStr = stargazerNode.get("starredAt").asText();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date starredAt = df.parse(starredAtStr);
        Stargazer stargazer = stargazerDao
            .findByCodeRepositoryIdAndUserId(codeRepository.getId(), stargazerNode.path("node").path("id").asText());
        if (stargazer == null) {
          addCount++;
          stargazer = stargazerDao.save(new Stargazer(userId, userLogin, starredAt, codeRepository, (byte) 1));
        }
        // 之前已经unstar的，又重新star。
        if (stargazer.getLastUnStarTime() != null) {
          stargazer.setLastUnStarTime(null);
          StarAction starAction = new StarAction();
          starAction.setActionType("star");
          starAction.setActionTime(starredAt);
          starAction.setStargazer(stargazer);

          starActionDao.save(starAction);
          stargazerDao.save(stargazer);
          addCount++;
        }
        stargazer.setShowMark((byte) 1);
        stargazerDao.save(stargazer);
      }

      if (res.path("pageInfo").path("hasNextPage").asBoolean()) {
        String afterCur = res.path("pageInfo").path("endCursor").asText();
        ql = String.format(this.requestTemplate, userName, repositoryName, "\\\"" + afterCur + "\\\"");
        res = invokeQL(ql).path("data").path("repository").path("stargazers");
        stargazerNodes = res.path("edges");
      } else {
        break;
      }
    }

    for (Stargazer stargazer : stargazerDao.findByCodeRepositoryIdAndShowMark(codeRepository.getId(), (byte) 0)) {
      if (stargazer.getLastUnStarTime() == null) {
        stargazer.setLastUnStarTime(now);
        StarAction starAction = new StarAction();
        starAction.setActionType("unstar");
        starAction.setActionTime(now);
        starAction.setStargazer(stargazer);
        starActionDao.save(starAction);
        stargazerDao.save(stargazer);
        removeCount++;
      }
    }

    historySummary.setAddCount(addCount);
    historySummary.setRemoveCount(removeCount);
    historySummaryDao.save(historySummary);
  }

  @Scheduled(cron = "0 37 6,9,12,15,16,18,21,23,11,14,8,22,10 * * ?")
  public void doDailyTask() {
    logger.warn("begin task ----------------" + (new Date()).toString());
    for (CodeRepository codeRepository : codeRepositoryDao.findAll()) {
      try {
        crawlCodeRepository(codeRepository);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }




}
