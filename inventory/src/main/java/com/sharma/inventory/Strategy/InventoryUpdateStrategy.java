package com.sharma.inventory.Strategy;

import org.springframework.stereotype.Component;

import com.sharma.inventory.models.OrderDTO;
import com.sharma.inventory.models.ProductDTO;

@Component
public interface InventoryUpdateStrategy {

	public ProductDTO update(OrderDTO order);

}
