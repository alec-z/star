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

import com.sun.javafx.beans.IDProperty;

import lombok.Data;

@Data
@Entity
@Table(name = "history_summaries")
@EntityListeners(AuditingEntityListener.class)
public class HistorySummary {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Integer starCount;

  private Integer addCount;

  private Integer removeCount;

  @ManyToOne
  @JoinColumn(name = "code_repository_id")
  private CodeRepository codeRepository;

  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @LastModifiedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;
}
