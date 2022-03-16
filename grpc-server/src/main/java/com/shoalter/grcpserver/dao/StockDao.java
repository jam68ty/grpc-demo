package com.shoalter.grcpserver.dao;

import com.shoalter.grcpserver.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockDao extends JpaRepository<Stock, Integer> {

    @Query(value = "select * from stock where description = ?", nativeQuery = true)
    List<Stock> findStockByDescription(String des);

}
