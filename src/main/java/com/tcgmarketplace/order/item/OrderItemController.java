package com.tcgmarketplace.order.item;

import com.tcgmarketplace.order.item.dto.CreateOrderItemDto;
import com.tcgmarketplace.order.item.dto.OrderItemDto;
import com.tcgmarketplace.order.item.dto.UpdateOrderItemDto;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/order-item")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public List<OrderItemDto> getAllOrderItems() {
        return orderItemService.getAllOrderItems();
    }

    @GetMapping("/{id}")
    public OrderItemDto getOrderItemById(@PathVariable Integer id) {
        return orderItemService.getOrderItemById(id);
    }

    @PostMapping
    public OrderItemDto createOrderItem(@RequestBody CreateOrderItemDto dto) {
        return orderItemService.createOrderItem(dto);
    }

    @PutMapping("/{id}")
    public OrderItemDto updateOrderItem(@PathVariable Integer id,
                                        @RequestBody UpdateOrderItemDto dto) {
        return orderItemService.updateOrderItem(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderItem(@PathVariable Integer id) {
        orderItemService.deleteOrderItem(id);
    }
}
