package com.jailabs.order_service.domain.models;

public record OrderSummary(String orderNumber, OrderStatus status) {}