package com.tcgmarketplace.order;

import com.tcgmarketplace.order.dto.CreateOrderDto;
import com.tcgmarketplace.order.dto.OrderDto;
import com.tcgmarketplace.order.dto.UpdateOrderDto;
import com.tcgmarketplace.user.User;
import com.tcgmarketplace.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    public OrderController(OrderService orderService, UserRepository userRepository) {
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    @GetMapping("/buyer")
    public ResponseEntity<List<OrderDto>> getMyOrders(@AuthenticationPrincipal UserDetails userDetails) {
        User buyer = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<OrderDto> myOrders = orderService.getOrdersForBuyer(buyer);
        return ResponseEntity.ok(myOrders);
    }

    @GetMapping("buyer/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@AuthenticationPrincipal UserDetails userDetails,
                                                 @PathVariable Integer orderId) {
        OrderDto orderDto = orderService.getOrderById(orderId);
        User buyer = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!orderDto.userId().equals(buyer.getId())) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(orderDto);
    }

    @PostMapping
    public OrderDto createOrder(@AuthenticationPrincipal UserDetails userDetails,
                                @RequestBody CreateOrderDto dto) {
        User buyer = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return orderService.createOrder(buyer, dto);
    }

    @GetMapping("/seller")
    public ResponseEntity<List<OrderDto>> getOrdersForSeller(@AuthenticationPrincipal UserDetails userDetails) {
        User seller = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Seller not found"));
        return ResponseEntity.ok(orderService.getOrdersForSeller(seller));
    }

    @GetMapping("/seller/{orderId}")
    public ResponseEntity<OrderDto> getOrderForSeller(@AuthenticationPrincipal UserDetails userDetails,
                                                      @PathVariable Integer orderId) {
        User seller = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Seller not found"));
        OrderDto orderDto = orderService.getOrderForSellerById(seller, orderId);
        return ResponseEntity.ok(orderDto);
    }

    @PutMapping("seller/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@AuthenticationPrincipal UserDetails userDetails,
                                                @PathVariable Integer orderId,
                                                @RequestBody UpdateOrderDto dto) {
        User seller = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Seller not found"));
        OrderDto updatedOrder = orderService.updateOrderForSeller(seller, orderId, dto);
        return ResponseEntity.ok(updatedOrder);
    }
}
