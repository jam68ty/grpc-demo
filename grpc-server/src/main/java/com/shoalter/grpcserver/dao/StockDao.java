package com.shoalter.grpcserver.dao;


import com.shoalter.grpcserver.enitity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockDao extends JpaRepository<Stock, Integer> {

    @Query(value = "select * from stock where product_name like ?1%", nativeQuery = true)
    List<Stock> findStockByProductNamePrefix(String prefix);

    @Query(value = "select * from stock where id=?1", nativeQuery = true)
    Stock getById(int id);
}
