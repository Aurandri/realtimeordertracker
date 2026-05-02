package com.aurandri.realtimeordertracker.controller;

import com.aurandri.realtimeordertracker.CustomHandling.Resp;
import com.aurandri.realtimeordertracker.dto.CreateOrderDTO;
import com.aurandri.realtimeordertracker.dto.UpdateOrderDTO;
import com.aurandri.realtimeordertracker.entities.OrderEntity;
import com.aurandri.realtimeordertracker.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
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
        try {
            List<OrderEntity> data = orderService.getAllOrders();

            Resp<List<OrderEntity>> response = new Resp<>();
            response.setCode(HttpStatus.OK.value());
            response.setData(data);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Resp<List<OrderEntity>> response = new Resp<>();
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("something went wrong");
            response.setData(null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/create-order")
    public ResponseEntity<Resp<OrderEntity>> createOrder(@RequestBody @Valid CreateOrderDTO createOrderDTO) {
        try {
            OrderEntity data = orderService.createOrder(createOrderDTO);

            Resp<OrderEntity> response = new Resp<>();
            response.setCode(HttpStatus.OK.value());
            response.setData(data);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Resp<OrderEntity> response = new Resp<>();
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("something went wrong");
            response.setData(null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/update-order")
    public ResponseEntity<Resp<OrderEntity>> updateOrder(@RequestBody @Valid UpdateOrderDTO updateOrderDTO) {
        try {
            OrderEntity data = orderService.updateOrder(updateOrderDTO);

            Resp<OrderEntity> response = new Resp<>();
            response.setCode(HttpStatus.OK.value());
            response.setMessage("Updated Order : " + updateOrderDTO.getStatus());
            // response.setData(data);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Resp<OrderEntity> response = new Resp<>();
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("something went wrong");
            response.setData(null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
