package com.aleczheng.star.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.aleczheng.star.po.CodeRepositoryWatch;

public class CodeRepositoryDaoSelfDefinedImpl implements CodeRepositoryDaoSelfDefined {
  @PersistenceContext
  private EntityManager em;

  @Override
  public List<CodeRepositoryWatch> findAllCodeRepositoryWatch(String githubAccount) {
    String sql = "select cr.*,"
        + "(select count(0) from stargazers s where s.code_repository_id = cr.id and s.user_login=?1) as watched"
        + " from code_repositories cr";
    return em.createNativeQuery(sql, CodeRepositoryWatch.class).setParameter(1, githubAccount).getResultList();
  }
}
