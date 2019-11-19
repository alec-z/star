package com.aleczheng.star.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.aleczheng.star.po.StarAction;

public interface StarActionDao extends JpaRepository<StarAction, Integer> {
}
