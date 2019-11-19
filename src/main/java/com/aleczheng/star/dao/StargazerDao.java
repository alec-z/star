package com.aleczheng.star.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aleczheng.star.po.Stargazer;


public interface StargazerDao extends JpaRepository<Stargazer, Integer>, StargazerDaoSelfDefined {
  Stargazer findByCodeRepositoryIdAndUserId(Integer codeRepositoryId, String userId);
  List<Stargazer> findByCodeRepositoryIdAndShowMark(Integer codeRepositoryId, byte showMark);
}
