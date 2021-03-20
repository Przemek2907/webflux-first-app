package com.app.domain.order;

import com.app.domain.order.dto.CreateOrderResponseDto;
import com.app.domain.order.dto.GetOrderDto;
import com.app.domain.order_position.OrderPosition;
import com.app.domain.configs.value_objects.Money;
import com.app.infrastructure.repository.entity.OrderDocument;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Order {

    String id;

    LocalDateTime orderTimeStamp;
    List<OrderPosition> orderPositions;

    public Money totalPrice() {
        return orderPositions
                .stream()
                .map(OrderPosition::totalPrice)
                .reduce(new Money(), Money::add);
    }

    public GetOrderDto toGetOrderDto() {
        return GetOrderDto
                .builder()
                .id(id)
                .date(orderTimeStamp)
                .totalPrice(totalPrice().toString())
                .build();
    }

    public CreateOrderResponseDto toCreateOrderResponseDto() {
        return CreateOrderResponseDto
                .builder()
                .orderId(id)
                .orderTimeStamp(orderTimeStamp)
                .build();
    }

    public OrderDocument toDocument() {
        return OrderDocument
                .builder()
                .id(id)
                .date(orderTimeStamp)
                .orderPositions(orderPositions)
                .build();
    }
}
