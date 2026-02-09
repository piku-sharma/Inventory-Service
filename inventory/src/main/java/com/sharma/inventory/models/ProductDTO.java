package com.sharma.inventory.models;

import java.util.List;

import lombok.Data;

@Data
public class ProductDTO {
	
	private String productName;
	private List<Long> batches;

}
