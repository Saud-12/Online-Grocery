package com.crio.project.onlineGrocery.service;

import com.crio.project.onlineGrocery.dto.OrderDto;
import com.crio.project.onlineGrocery.entity.OrderEntity;
import com.crio.project.onlineGrocery.entity.ProductEntity;
import com.crio.project.onlineGrocery.exception.ResourceNotFoundException;
import com.crio.project.onlineGrocery.repository.OrderRepository;
import com.crio.project.onlineGrocery.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public OrderService(OrderRepository orderRepository,ModelMapper modelMapper,ProductRepository productRepository){
        this.orderRepository=orderRepository;
        this.modelMapper=modelMapper;
        this.productRepository=productRepository;
    }

    public Optional<OrderDto> getOrderById(Long id){
        return orderRepository.findById(id).map(orderEntity->modelMapper.map(orderEntity,OrderDto.class));
    }

    public List<OrderDto> getAllOrders(){
        return orderRepository.findAll()
                .stream()
                .map(order->modelMapper.map(order,OrderDto.class))
                .collect(Collectors.toList());
    }

    public Boolean deleteOrder(Long id){
        if(!orderRepository.existsById(id)){
            throw new ResourceNotFoundException("Order with id "+id+" does not exist!");
        }
        orderRepository.deleteById(id);
        return true;
    }

    public OrderDto createOrder(OrderDto orderDto){
        OrderEntity orderEntity=modelMapper.map(orderDto,OrderEntity.class);
       List<ProductEntity> productEntities= productRepository.findAllById(orderDto.getProductIds());
       orderEntity.setProducts(productEntities);
        return modelMapper.map(orderRepository.save(orderEntity),OrderDto.class);
    }

    public OrderDto updateOrder(OrderDto orderDto,Long id){
        OrderEntity existingOrder=orderRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Order with id "+id+" does not exist!"));
        modelMapper.map(orderDto,existingOrder);
        return modelMapper.map(orderRepository.save(existingOrder),OrderDto.class);
    }
}
