/*
package com.jailabs.order_service.web.rabitmqtest;


@Service
public class RabbitMQListener {

    @RabbitListener(queues = "#{newOrdersQueue.name}")
    public void listenNewOrders(String message) {
        System.out.println("Received on New Orders Queue: " + message);
    }

    @RabbitListener(queues = "#{deliveredOrdersQueue.name}")
    public void listenDeliveredOrders(String message) {
        System.out.println("Received on Delivered Orders Queue: " + message);
    }

    @RabbitListener(queues = "#{cancelledOrdersQueue.name}")
    public void listenCancelledOrders(String message) {
        System.out.println("Received on Cancelled Orders Queue: " + message);
    }

    @RabbitListener(queues = "#{errorOrdersQueue.name}")
    public void listenErrorOrders(String message) {
        System.out.println("Received on Error Orders Queue: " + message);
    }
}
*/
