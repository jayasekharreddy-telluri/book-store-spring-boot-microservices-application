package com.jailabs.order_service.web.controllers;

import com.jailabs.order_service.domain.OrderService;
import com.jailabs.order_service.domain.SecurityService;
import com.jailabs.order_service.domain.models.CreateOrderRequest;
import com.jailabs.order_service.domain.models.CreateOrderResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@SecurityRequirement(name = "security_auth")
class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final SecurityService securityService;

    OrderController(OrderService orderService, SecurityService securityService) {
        this.orderService = orderService;
        this.securityService = securityService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
        String userName = securityService.getLogInUsername();
        log.info("Creating order for user: {}", userName);
        return orderService.createOrder(userName, request);
    }
}
