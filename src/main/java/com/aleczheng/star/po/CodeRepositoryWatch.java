package com.aleczheng.star.po;

import javax.persistence.Entity;

import lombok.Data;

@Data
public class CodeRepositoryWatch extends CodeRepository{
  private Integer watched;

  public String getRepositoryURL() {
    return "https://github.com/" + getUserName() + "/" + getRepositoryName();
  }
}
