package com.sharma.inventory.models;

import lombok.Data;

@Data
public class OrderDTO {
	Long productId;
	Integer quantity;

	public OrderDTO() {
	}

	public OrderDTO(Long productId, Integer quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}
}
