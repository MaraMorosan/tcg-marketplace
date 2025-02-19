package com.tcgmarketplace.order.item;

import com.tcgmarketplace.order.item.dto.CreateOrderItemDto;
import com.tcgmarketplace.order.item.dto.OrderItemDto;
import com.tcgmarketplace.order.item.dto.UpdateOrderItemDto;
import java.util.List;

public interface OrderItemService {
    List<OrderItemDto> getAllOrderItems();
    OrderItemDto getOrderItemById(Integer id);
    OrderItemDto createOrderItem(CreateOrderItemDto dto);
    OrderItemDto updateOrderItem(Integer id, UpdateOrderItemDto dto);
    void deleteOrderItem(Integer id);
}
