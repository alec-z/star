package com.aleczheng.star.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aleczheng.star.po.HistorySummary;

public interface HistorySummaryDao extends JpaRepository<HistorySummary, Integer> {

}
