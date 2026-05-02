package com.aurandri.realtimeordertracker.controller;

import com.aurandri.realtimeordertracker.CustomHandling.Resp;
import com.aurandri.realtimeordertracker.dto.CreateOrderDTO;
import com.aurandri.realtimeordertracker.dto.OrderRequestDTO;
import com.aurandri.realtimeordertracker.dto.UpdateOrderDTO;
import com.aurandri.realtimeordertracker.entities.OrderEntity;
import com.aurandri.realtimeordertracker.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/status")
    public ResponseEntity<Resp<List<OrderEntity>>> getAllOrderByStatus(@RequestBody @Valid OrderRequestDTO orderRequestDTO) {
        List<OrderEntity> data = orderService.getOrderByStatus(orderRequestDTO);

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
