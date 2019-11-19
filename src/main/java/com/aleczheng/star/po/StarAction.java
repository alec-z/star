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
@Table(name = "star_actions")
@EntityListeners(AuditingEntityListener.class)

public class StarAction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String actionType;

  @Temporal(TemporalType.TIMESTAMP)
  private Date actionTime;


  @ManyToOne
  @JoinColumn(name = "stargazer_id")
  private Stargazer stargazer;

  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;




}
