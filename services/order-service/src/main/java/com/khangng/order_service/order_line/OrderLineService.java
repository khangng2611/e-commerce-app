package com.khangng.order_service.order_line;

import com.khangng.order_service.entity.Order;
import com.khangng.order_service.entity.OrderLine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;
    public OrderLineResponse createOrderLine(int orderId, int productId, int quantity) {
        OrderLine newOrderLine = OrderLine.builder()
                .order(Order.builder().id(orderId).build())
                .productId(productId)
                .quantity(quantity)
                .build();
        var savedOrderLine = orderLineRepository.save(newOrderLine);
        return new OrderLineResponse(
            savedOrderLine.getId(),
            savedOrderLine.getProductId(),
            savedOrderLine.getQuantity()
        );
    }
    
    public List<OrderLineResponse> findOrderLineByOrderId(int orderId) {
        return orderLineRepository.findAllByOrderId(orderId)
                .stream()
                .map(orderLine -> new OrderLineResponse(
                    orderLine.getId(),
                    orderLine.getProductId(),
                    orderLine.getQuantity()
                ))
                .toList();
    }
}
