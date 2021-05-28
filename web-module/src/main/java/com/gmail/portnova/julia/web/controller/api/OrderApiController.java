package com.gmail.portnova.julia.web.controller.api;

import com.gmail.portnova.julia.service.OrderApiService;
import com.gmail.portnova.julia.service.model.ItemApiDTO;
import com.gmail.portnova.julia.service.model.OrderApiDTO;
import com.gmail.portnova.julia.service.model.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderApiService orderApiService;

    @GetMapping
    public List<OrderDTO> getAllOrders() {
       return orderApiService.getOrders();
    }

    @GetMapping("/{id}")
    public OrderApiDTO getOrderByUuid(@PathVariable("id") String uuidString) {
        return orderApiService.getOrderApiByUuid(uuidString);
    }
}
