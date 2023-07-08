package com.example.TDDMiniProject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestOrderRepository {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void saveOrderTest(){
//    instantiate an example order
        OrderEntity order = new OrderEntity("hanhi", LocalDate.now(), "1234 Anywhere St.", 59.99);

//    run the function that saves order to db
        orderRepository.save(order);

//    Assert that database contains example order
        Assertions.assertThat(order.getId()).isGreaterThan(0);
    }

    @Test
//    @org.junit.jupiter.api.Order(2)
    public void getOrderTest(){

        OrderEntity order = new OrderEntity("hanhi", LocalDate.now(), "1234 Anywhere St.", 59.99);
        orderRepository.save(order);

        OrderEntity retrievedOrder = orderRepository.findById(order.getId()).orElse(null);

        Assertions.assertThat(retrievedOrder).isNotNull();
        Assertions.assertThat(retrievedOrder.getId()).isEqualTo(order.getId());

    }

    @Test
//    @org.junit.jupiter.api.Order(3)
    public void getAllOrderTest(){

        OrderEntity order = new OrderEntity("hanhi", LocalDate.now(), "1234 Anywhere St.", 59.99);
        orderRepository.save(order);

        List<OrderEntity> orders = orderRepository.findAll();

        Assertions.assertThat(orders).isNotEmpty();
    }

    @Test
//    @org.junit.jupiter.api.Order(4)
    public void updateOrderTest(){

        OrderEntity order = new OrderEntity("hanhi", LocalDate.now(), "1234 Anywhere St.", 59.99);
        orderRepository.save(order);

        OrderEntity retrievedOrder = orderRepository.findById(order.getId()).orElse(null);
        Assertions.assertThat(retrievedOrder).isNotNull();

        retrievedOrder.setCustomerName("newName");
        OrderEntity updatedOrder = orderRepository.save(retrievedOrder);

        Assertions.assertThat(updatedOrder.getCustomerName()).isEqualTo("newName");

    }

    @Test
//    @org.junit.jupiter.api.Order(5)
    public void deleteOrderTest(){

        OrderEntity order = new OrderEntity("hanhi", LocalDate.now(), "1234 Anywhere St.", 59.99);
        orderRepository.save(order);

        orderRepository.delete(order);

        Optional<OrderEntity> optionalOrder = orderRepository.findById(order.getId());
        Assertions.assertThat(optionalOrder).isEmpty();
    }


}
