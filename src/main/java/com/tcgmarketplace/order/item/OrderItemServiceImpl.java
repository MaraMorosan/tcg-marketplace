package com.tcgmarketplace.order.item;

import com.tcgmarketplace.order.Order;
import com.tcgmarketplace.order.OrderRepository;
import com.tcgmarketplace.order.item.dto.CreateOrderItemDto;
import com.tcgmarketplace.order.item.dto.OrderItemDto;
import com.tcgmarketplace.order.item.dto.UpdateOrderItemDto;
import com.tcgmarketplace.listing.Listing;
import com.tcgmarketplace.listing.ListingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ListingRepository listingRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository,
                                OrderRepository orderRepository,
                                ListingRepository listingRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.listingRepository = listingRepository;
    }

    @Override
    public List<OrderItemDto> getAllOrderItems() {
        return orderItemRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public OrderItemDto getOrderItemById(Integer id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found"));
        return toDto(orderItem);
    }

    @Override
    public OrderItemDto createOrderItem(CreateOrderItemDto dto) {
        Order order = orderRepository.findById(dto.orderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));
        Listing listing = listingRepository.findById(dto.listingId())
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setListing(listing);
        orderItem.setQuantity(dto.quantity());
        orderItem.setPrice(dto.price());
        orderItem.setSubtotal(dto.price().multiply(new BigDecimal(dto.quantity())));

        orderItemRepository.save(orderItem);
        return toDto(orderItem);
    }

    @Override
    public OrderItemDto updateOrderItem(Integer id, UpdateOrderItemDto dto) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found"));
        orderItem.setQuantity(dto.quantity());
        orderItem.setPrice(dto.price());
        orderItem.setSubtotal(dto.price().multiply(new BigDecimal(dto.quantity())));
        orderItemRepository.save(orderItem);
        return toDto(orderItem);
    }

    @Override
    public void deleteOrderItem(Integer id) {
        if (!orderItemRepository.existsById(id)) {
            throw new RuntimeException("OrderItem not found");
        }
        orderItemRepository.deleteById(id);
    }

    private OrderItemDto toDto(OrderItem orderItem) {
        return new OrderItemDto(
                orderItem.getId(),
                orderItem.getOrder().getId(),
                orderItem.getListing().getId(),
                orderItem.getQuantity(),
                orderItem.getPrice(),
                orderItem.getSubtotal()
        );
    }
}
