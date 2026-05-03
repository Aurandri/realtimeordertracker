package com.aurandri.realtimeordertracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aurandri.realtimeordertracker.CustomHandling.Resp;
import com.aurandri.realtimeordertracker.dto.CreateOrderDTO;
import com.aurandri.realtimeordertracker.dto.UpdateOrderDTO;
import com.aurandri.realtimeordertracker.entities.OrderEntity;
import com.aurandri.realtimeordertracker.entities.OrderStatus;
import com.aurandri.realtimeordertracker.services.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<Resp<List<OrderEntity>>> getAllOrders() {
        List<OrderEntity> data = orderService.getAllOrders();

        Resp<List<OrderEntity>> response = new Resp<>();
        response.setCode(HttpStatus.OK.value());
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resp<OrderEntity>> getOrderById(@PathVariable Long id) {
        OrderEntity data = orderService.getOrderById(id);

        Resp<OrderEntity> response = new Resp<>();
        response.setCode(HttpStatus.OK.value());
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/status")
    public ResponseEntity<Resp<List<OrderEntity>>> getAllOrderByStatus(@RequestParam OrderStatus status) {
        List<OrderEntity> data = orderService.getOrderByStatus(status);

        Resp<List<OrderEntity>> response = new Resp<>();
        response.setCode(HttpStatus.OK.value());
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-order")
    public ResponseEntity<Resp<OrderEntity>> createOrder(@RequestBody @Valid CreateOrderDTO createOrderDTO) {
        OrderEntity data = orderService.createOrder(createOrderDTO);

        Resp<OrderEntity> response = new Resp<>();
        response.setCode(HttpStatus.CREATED.value());
        response.setData(data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update-order")
    public ResponseEntity<Resp<OrderEntity>> updateOrder(@RequestBody @Valid UpdateOrderDTO updateOrderDTO) {
        OrderEntity data = orderService.updateOrder(updateOrderDTO);

        Resp<OrderEntity> response = new Resp<>();
        response.setCode(HttpStatus.OK.value());
        response.setData(data);

        return ResponseEntity.ok(response);
    }
}
