package com.crio.project.onlineGrocery.controller;

import com.crio.project.onlineGrocery.dto.OrderDto;
import com.crio.project.onlineGrocery.exception.ResourceNotFoundException;
import com.crio.project.onlineGrocery.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService=orderService;
    }
    @GetMapping(path="/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId){
        Optional<OrderDto> orderDto=orderService.getOrderById(orderId);
        return orderDto.map(order->ResponseEntity.ok(order)).orElseThrow(()->new ResourceNotFoundException("Order with id "+orderId+" does not exist"));
    }

    @GetMapping()
    public ResponseEntity<List<OrderDto>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PostMapping()
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderDto orderDto){
        return new ResponseEntity<>(orderService.createOrder(orderDto), HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@RequestBody @Valid OrderDto orderDto,@PathVariable Long orderId){
        return ResponseEntity.ok(orderService.updateOrder(orderDto,orderId));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Boolean> deleteOrder(@PathVariable Long orderId){
        boolean isDeleted= orderService.deleteOrder(orderId);
        if(isDeleted){
            return ResponseEntity.ok(true);
        }return ResponseEntity.notFound().build();
    }
}

