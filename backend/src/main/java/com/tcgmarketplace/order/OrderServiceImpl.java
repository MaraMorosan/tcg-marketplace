package com.tcgmarketplace.order;

import com.tcgmarketplace.order.dto.CreateOrderDto;
import com.tcgmarketplace.order.dto.OrderDto;
import com.tcgmarketplace.order.dto.UpdateOrderDto;
import com.tcgmarketplace.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDto getOrderById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return toDto(order);
    }

    @Override
    public OrderDto createOrder(User buyer, CreateOrderDto dto) {
        Order order = new Order();
        order.setUser(buyer);
        order.setTotal(dto.total());

        orderRepository.save(order);
        return toDto(order);
    }

    @Override
    public List<OrderDto> getOrdersForSeller(User seller) {
        List<Order> orders = orderRepository.findOrdersBySellerId(seller.getId());
        return orders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderForSellerById(User seller, Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        boolean belongsToSeller = order.getOrderItems().stream()
                .anyMatch(item -> item.getListing().getSeller().getId().equals(seller.getId()));

        if (!belongsToSeller) {
            throw new RuntimeException("Unauthorized: This order does not belong to your listings.");
        }

        return toDto(order);
    }

    @Override
    public List<OrderDto> getOrdersForBuyer(User buyer) {
        return orderRepository.findAll().stream()
                .filter(order -> order.getUser().getId().equals(buyer.getId()))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto updateOrderForSeller(User seller, Integer id, UpdateOrderDto dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        boolean belongsToSeller = order.getOrderItems().stream()
                .anyMatch(item -> item.getListing().getSeller().getId().equals(seller.getId()));

        if (!belongsToSeller) {
            throw new RuntimeException("Unauthorized: This order does not belong to your listings.");
        }

        order.setStatus(dto.status());
        orderRepository.save(order);

        return toDto(order);
    }


    private OrderDto toDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getUser().getId(),
                order.getOrderDate(),
                order.getStatus(),
                order.getTotal()
        );
    }
}
