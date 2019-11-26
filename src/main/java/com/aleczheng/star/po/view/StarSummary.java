package com.aleczheng.star.po.view;

import java.util.ArrayList;
import java.util.List;

import com.aleczheng.star.po.HistorySummary;

import lombok.Data;

@Data
public class StarSummary {
  private int currentStarCount;

  private int lastStarCount;

  private int lastUnStarCount;

  private int lastNetStarCount;

  private List<HistorySummarySimple> historySummaries = new ArrayList<>();
}
