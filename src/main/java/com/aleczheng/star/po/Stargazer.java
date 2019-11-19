package com.aleczheng.star.po;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@Entity
@Table(name = "stargazers")
@EntityListeners(AuditingEntityListener.class)
public class Stargazer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String userId;

  private String userLogin;

  private Byte showMark;

  @Temporal(TemporalType.TIMESTAMP)
  private Date firstStarTime;

  @Temporal(TemporalType.TIMESTAMP)
  private Date lastUnStarTime;

  @ManyToOne
  @JoinColumn(name = "code_repository_id")
  private CodeRepository codeRepository;

  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @LastModifiedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;




  public Stargazer(String userId, String userLogin, Date firstStarTime, CodeRepository codeRepository, byte showMark) {
    this.userId = userId;
    this.userLogin = userLogin;
    this.firstStarTime = firstStarTime;
    this.codeRepository = codeRepository;
    this.showMark = showMark;
  }

  public Stargazer() {

  }



}
