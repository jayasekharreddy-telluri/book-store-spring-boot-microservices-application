package com.jailabs.order_service.domain;

import com.jailabs.order_service.domain.models.OrderEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

interface OrderEventRepository extends JpaRepository<OrderEventEntity, Long> {}
