package com.tcgmarketplace.order;

import com.tcgmarketplace.order.dto.CreateOrderDto;
import com.tcgmarketplace.order.dto.OrderDto;
import com.tcgmarketplace.order.dto.UpdateOrderDto;
import java.util.List;

public interface OrderService {
    List<OrderDto> getAllOrders();
    OrderDto getOrderById(Integer id);
    OrderDto createOrder(CreateOrderDto dto);
    OrderDto updateOrder(Integer id, UpdateOrderDto dto);
    void deleteOrder(Integer id);
}
