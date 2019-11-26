package com.aleczheng.star.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aleczheng.star.po.HistorySummary;

public interface HistorySummaryDao extends JpaRepository<HistorySummary, Integer> {
  @Query(nativeQuery = true, value = "select h.* from history_summaries h where h.code_repository_id != 12 order by id desc limit 11 ")
  List<HistorySummary> findHistorySummariesForServiceComb();

}
