package com.aleczheng.star.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


public class StargazerDaoSelfDefinedImpl implements StargazerDaoSelfDefined {
  @PersistenceContext
  private EntityManager em;

  @Transactional
  @Override
  public int batchUpdateShowMark(int codeRepositoryId) {
    return em.createQuery("UPDATE Stargazer s set s.showMark = 0 where s.codeRepository.id=?1").setParameter(1, codeRepositoryId).executeUpdate();
  }
}
