package com.tcgmarketplace.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("select distinct o from Order o join o.orderItems oi where oi.listing.seller.id = :sellerId")
    List<Order> findOrdersBySellerId(@Param("sellerId") Integer sellerId);
}
