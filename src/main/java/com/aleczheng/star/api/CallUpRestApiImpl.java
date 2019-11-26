/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aleczheng.star.api;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aleczheng.star.dao.CodeRepositoryDao;

import com.aleczheng.star.dao.HistorySummaryDao;
import com.aleczheng.star.po.CodeRepositoryWatch;
import com.aleczheng.star.po.HistorySummary;
import com.aleczheng.star.po.view.HistorySummarySimple;
import com.aleczheng.star.po.view.StarSummary;

@RestController
@RequestMapping("/")
public class CallUpRestApiImpl {
  @Autowired
  CodeRepositoryDao codeRepositoryDao;

  @Autowired
  HistorySummaryDao historySummaryDao;


  @GetMapping("stars/{githubAccount}")
  public List<CodeRepositoryWatch> indexStarsForAccount(@PathVariable String githubAccount) {
    return codeRepositoryDao.findAllCodeRepositoryWatch(githubAccount);
  }

  @GetMapping("stars")
  public StarSummary indexStars() {

    List<HistorySummary> historySummaries = historySummaryDao.findHistorySummariesForServiceComb();
    List<HistorySummarySimple> historySummarySimples = new ArrayList<>();

    historySummaries.stream().forEach(h -> {
      HistorySummarySimple historySummarySimple = new HistorySummarySimple();
      historySummarySimple.setCodeRepositoryName(h.getCodeRepository().getRepositoryName());
      historySummarySimple.setId(h.getId());
      historySummarySimple.setAddCount(h.getAddCount());
      historySummarySimple.setRemoveCount(h.getRemoveCount());
      historySummarySimple.setStarCount(h.getStarCount());
      historySummarySimple.setCreatedAt(h.getCreatedAt());
      historySummarySimples.add(historySummarySimple);
    });

    StarSummary starSummary = new StarSummary();
    starSummary.setHistorySummaries(historySummarySimples);
    starSummary.setCurrentStarCount(
        historySummaries.stream().map(h -> h.getStarCount()).reduce((a,b) -> a + b).get()
    );

    starSummary.setLastStarCount(
        historySummaries.stream().map(h -> h.getAddCount()).reduce((a,b) -> a + b).get()
    );

    starSummary.setLastUnStarCount(
        historySummaries.stream().map(h -> h.getRemoveCount()).reduce((a,b) -> a + b).get()
    );

    starSummary.setLastNetStarCount(
        starSummary.getLastStarCount() - starSummary.getLastUnStarCount()
    );

    return starSummary;
  }




}