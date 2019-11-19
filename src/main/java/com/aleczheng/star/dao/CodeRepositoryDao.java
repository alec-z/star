package com.aleczheng.star.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aleczheng.star.po.CodeRepository;

public interface CodeRepositoryDao extends JpaRepository<CodeRepository, Integer>, CodeRepositoryDaoSelfDefined {


}
