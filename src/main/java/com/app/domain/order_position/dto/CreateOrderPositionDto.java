package com.app.domain.order_position.dto;

import com.app.domain.product.dto.GetProductDto;

public class CreateOrderPositionDto {

    private String id;
    private GetProductDto productDto;
    private Integer quantity;
}
