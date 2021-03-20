package com.app.domain.product.dto;

import com.app.domain.configs.value_objects.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetProductDto {
    private String id;
    private String name;
    private String totalPrice;
}
