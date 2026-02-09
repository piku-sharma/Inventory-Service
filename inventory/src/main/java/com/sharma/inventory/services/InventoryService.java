package com.sharma.inventory.services;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sharma.inventory.Strategy.InventoryUpdateStrategy;
import com.sharma.inventory.factory.InventoryUpdateStrategyFactory;
import com.sharma.inventory.models.Batch;
import com.sharma.inventory.models.OrderDTO;
import com.sharma.inventory.models.Product;
import com.sharma.inventory.models.ProductDTO;
import com.sharma.inventory.repository.ProductRepository;

@Component
public class InventoryService {

    @Autowired
    private ProductRepository productRepository;
    
    private final InventoryUpdateStrategyFactory factory;

    public InventoryService(InventoryUpdateStrategyFactory factory) {
        this.factory = factory;
    }

    public List<Batch> getProductsSortedByExpiryDate(Long productId){
    	
    	Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    	
    	return product.getBatches()
                .stream()
                .sorted(Comparator.comparing(Batch::getExpiryDate))
                .collect(Collectors.toList());
    }

	public ProductDTO updateProduct(OrderDTO order) {
		
		  InventoryUpdateStrategy strategy =
	                factory.getStrategy("FEFO");

	        return strategy.update(order);
	}

	public Product getProductsSortedByExpiry(Long productId) {
		return productRepository.findById(productId).get();
	}
}
