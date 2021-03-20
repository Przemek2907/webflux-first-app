package com.app.domain.order.dto;

import com.app.domain.configs.value_objects.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetOrderDto {
    private String id;
    private LocalDateTime date;
    private String totalPrice;
}
