package com.sharma.inventory.Strategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sharma.inventory.models.Batch;
import com.sharma.inventory.models.OrderDTO;
import com.sharma.inventory.models.Product;
import com.sharma.inventory.models.ProductDTO;
import com.sharma.inventory.repository.ProductRepository;

@Component
public class FefoInventoryUpdateStrategy implements InventoryUpdateStrategy {
	
	@Autowired
	ProductRepository productRepository;

	@Override
	public ProductDTO update(OrderDTO order) {
		// product not found
				Product product = productRepository.findById(order.getProductId())
		                .orElseThrow(() -> new RuntimeException("Product not found"));
				
				// quantity ordered is more than available or Out of Stock
				Integer totalQuantity = 0;
				ProductDTO productDTO = new ProductDTO();
				productDTO.setProductName(product.getProductName());
				for(Batch b: product.getBatches()) {totalQuantity += b.getQuantity();}
				
				
				// Out of Stock
				if(totalQuantity == 0) {
					throw new RuntimeException("Item Out of Stock");
				}
				
				// limited availability
				if(totalQuantity < order.getQuantity()) {
					throw new RuntimeException("Quantity exceeded, Available quantity is "+ totalQuantity);
				}
				
				// positive scenario
				List<Batch> batches = product.getBatches()
		                .stream()
		                .sorted(Comparator.comparing(Batch::getExpiryDate))
		                .collect(Collectors.toList());
				
				List<Long> batchIdsToBeReturned = new ArrayList<Long>();
				for(Batch b: batches) {
					if(b.getQuantity()>=order.getQuantity()) {
						b.setQuantity(b.getQuantity()-order.getQuantity());
						order.setQuantity(0);
						batchIdsToBeReturned.add(b.getBatchId());
					}
					else {
						order.setQuantity(order.getQuantity()-b.getQuantity());
						b.setQuantity(0);
						batchIdsToBeReturned.add(b.getBatchId());
					}
					if(order.getQuantity() == 0) break;
				}
				product.setBatches(batches);
				productRepository.save(product);
				productDTO.setBatches(batchIdsToBeReturned);
				return productDTO;
	}

	
}
