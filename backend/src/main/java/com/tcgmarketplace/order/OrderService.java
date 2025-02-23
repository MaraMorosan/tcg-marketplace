package com.tcgmarketplace.order;

import com.tcgmarketplace.order.dto.CreateOrderDto;
import com.tcgmarketplace.order.dto.OrderDto;
import com.tcgmarketplace.order.dto.UpdateOrderDto;
import com.tcgmarketplace.user.User;

import java.util.List;

public interface OrderService {
    OrderDto getOrderById(Integer id);
    OrderDto createOrder(User buyer, CreateOrderDto dto);
    List<OrderDto> getOrdersForSeller(User seller);
    OrderDto getOrderForSellerById(User seller, Integer orderId);
    List<OrderDto> getOrdersForBuyer(User buyer);
    OrderDto updateOrderForSeller(User seller, Integer id, UpdateOrderDto dto);

}
