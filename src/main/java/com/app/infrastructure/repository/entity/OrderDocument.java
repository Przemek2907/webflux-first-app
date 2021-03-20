package com.app.infrastructure.repository.entity;

import com.app.domain.order.Order;
import com.app.domain.order_position.OrderPosition;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Document(collection = "orders")
public class OrderDocument {

    @Id
    String id;

    LocalDateTime date;
    List<OrderPosition> orderPositions;

    public Order toOrder() {
        return Order
                .builder()
                .id(id)
                .orderTimeStamp(date)
                .orderPositions(orderPositions)
                .build();
    }
}
