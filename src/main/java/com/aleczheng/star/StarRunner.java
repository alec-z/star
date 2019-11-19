package com.aleczheng.star;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.aleczheng.star.task.StarTask;

@Component
public class StarRunner implements ApplicationRunner {

  @Autowired
  StarTask starTask;
  @Override
  public void run(ApplicationArguments var1) throws Exception {
    //starTask.doDailyTask();
  }
}
