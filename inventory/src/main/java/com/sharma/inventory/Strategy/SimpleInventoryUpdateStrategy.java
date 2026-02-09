package com.sharma.inventory.Strategy;

import org.springframework.stereotype.Component;

import com.sharma.inventory.models.OrderDTO;
import com.sharma.inventory.models.ProductDTO;

@Component
public class SimpleInventoryUpdateStrategy implements InventoryUpdateStrategy {

	@Override
	public ProductDTO update(OrderDTO order) {
		// TODO for future ideas
		return null;
	}

}
