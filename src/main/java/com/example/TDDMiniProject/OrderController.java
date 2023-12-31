package com.example.TDDMiniProject;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Validated
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

//  Reads all orders and shows them
    @GetMapping("/orders")
    public ResponseEntity<List<OrderEntity>> getAllOrders(){
        List<OrderEntity> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

//  Create an order
    @PostMapping("/create")
    public ResponseEntity<OrderEntity> createOrder(@Valid @RequestBody OrderEntity order){
        try{
            OrderEntity orderToCreate = orderService.saveOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderToCreate);
        } catch (ConstraintViolationException cve){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrderEntity> updateOrder(@PathVariable Long id, @RequestBody OrderEntity orderToUpdate){
        Optional<OrderEntity> updatedOrder = orderService.updateOrder(id, orderToUpdate);
        return updatedOrder.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        boolean deleted = orderService.deleteOrder(id);
        if (deleted){
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }


}


