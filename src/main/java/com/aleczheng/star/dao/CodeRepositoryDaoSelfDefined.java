package com.aleczheng.star.dao;

import java.util.List;


import com.aleczheng.star.po.CodeRepositoryWatch;

public interface CodeRepositoryDaoSelfDefined {
  List<CodeRepositoryWatch> findAllCodeRepositoryWatch(String githubAccount);


}
