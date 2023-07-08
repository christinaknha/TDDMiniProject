package com.example.TDDMiniProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService  {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    //  Method to create/save an order
    public OrderEntity saveOrder(OrderEntity order){
        return orderRepository.save(order);
    }

    //  Method to read all orders
    public List<OrderEntity> getAllOrders(){
        orderRepository.save(new OrderEntity("Customer 1", LocalDate.now(), "Address 1", 10.0));

        return orderRepository.findAll();
    }

    //  Method to update an order
    public Optional<OrderEntity> updateOrder(Long id, OrderEntity orderToUpdate){
        Optional<OrderEntity> orderExists = orderRepository.findById(id);
        if (orderExists.isPresent()){
            OrderEntity updatedOrder = orderExists.get();
            updatedOrder.setCustomerName(orderToUpdate.getCustomerName());
            updatedOrder.setOrderDate(orderToUpdate.getOrderDate());
            updatedOrder.setShippingAddress(orderToUpdate.getShippingAddress());
            updatedOrder.setTotal(orderToUpdate.getTotal());
            return Optional.of(orderRepository.save(updatedOrder));
        }
        return Optional.empty();
    }


    //  Method to delete order
    public boolean deleteOrder(Long id){
        if (orderRepository.findById(id).isPresent()){
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<OrderEntity> getOrderById(Long id){
        return orderRepository.findById(id);

        }
    }


