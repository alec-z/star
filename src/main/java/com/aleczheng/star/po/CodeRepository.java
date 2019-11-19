package com.aleczheng.star.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "code_repositories")
@EntityListeners(AuditingEntityListener.class)
public class CodeRepository {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String userName;

  private String repositoryName;

  private Integer sequence;


  @JsonIgnore
  @OneToMany(mappedBy = "codeRepository")
  private List<Stargazer> stargazers = new ArrayList<>();

  private Integer currentStarCount;

  @JsonIgnore
  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @JsonIgnore
  @LastModifiedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;




}
