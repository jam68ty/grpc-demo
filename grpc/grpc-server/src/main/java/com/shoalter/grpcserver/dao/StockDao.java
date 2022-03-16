package com.shoalter.grpcserver.dao;


import com.shoalter.grpcserver.enitity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockDao extends JpaRepository<Stock, Integer> {


}
