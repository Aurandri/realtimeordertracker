package com.aurandri.realtimeordertracker.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class SseController {

    // Map orderId → list of active emitters
    private final Map<Long, List<SseEmitter>> emitters = new ConcurrentHashMap<>();

    @GetMapping(value = "/{orderId}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamOrderStatus(@PathVariable Long orderId) {
        SseEmitter emitter = new SseEmitter(300_000L); // timeout 5 menit

        emitters.computeIfAbsent(orderId, id -> new CopyOnWriteArrayList<>()).add(emitter);

        emitter.onCompletion(() -> removeEmitter(orderId, emitter));
        emitter.onTimeout(() -> removeEmitter(orderId, emitter));
        emitter.onError(e -> removeEmitter(orderId, emitter));

        return emitter;
    }

    public void pushStatusUpdate(Long orderId, String status) {
        List<SseEmitter> orderEmitters = emitters.get(orderId);
        if (orderEmitters == null || orderEmitters.isEmpty()) return;

        List<SseEmitter> deadEmitters = new CopyOnWriteArrayList<>();

        orderEmitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("order-status")
                        .data("{\"orderId\":" + orderId + ",\"status\":\"" + status + "\"}"));
            } catch (Exception e) {
                deadEmitters.add(emitter);
        }
        });

        orderEmitters.removeAll(deadEmitters);
    }

    private void removeEmitter(Long orderId, SseEmitter emitter) {
        List<SseEmitter> orderEmitters = emitters.get(orderId);
        if (orderEmitters != null) {
            orderEmitters.remove(emitter);
        }
    }
}