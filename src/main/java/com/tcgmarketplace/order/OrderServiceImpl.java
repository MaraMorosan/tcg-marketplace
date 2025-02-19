package com.tcgmarketplace.order;

import com.tcgmarketplace.order.dto.CreateOrderDto;
import com.tcgmarketplace.order.dto.OrderDto;
import com.tcgmarketplace.order.dto.UpdateOrderDto;
import com.tcgmarketplace.user.User;
import com.tcgmarketplace.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public OrderDto getOrderById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return toDto(order);
    }

    @Override
    public OrderDto createOrder(CreateOrderDto dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Order order = new Order();
        order.setUser(user);
        orderRepository.save(order);
        return toDto(order);
    }

    @Override
    public OrderDto updateOrder(Integer id, UpdateOrderDto dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(dto.status());
        order.setTotal(dto.total());
        orderRepository.save(order);
        return toDto(order);
    }

    @Override
    public void deleteOrder(Integer id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteById(id);
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
