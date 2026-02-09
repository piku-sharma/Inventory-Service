package com.sharma.inventory.Controllers;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sharma.inventory.models.Batch;
import com.sharma.inventory.models.OrderDTO;
import com.sharma.inventory.models.Product;
import com.sharma.inventory.models.ProductDTO;
import com.sharma.inventory.services.InventoryService;

@RestController
@RequestMapping("/inventory")
public class Inventory {

	@Autowired
	private InventoryService inventoryService;

    @GetMapping("/getBatchesOnly/{productId}")
    public ResponseEntity<List<Batch>> getProductsSortedByExpiryDate(@PathVariable("productId") Long productId)
    {
        List<Batch> batches = inventoryService.getProductsSortedByExpiryDate(productId);
        return ResponseEntity.ok(batches); 
    }
    
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductsSortedByExpiry(@PathVariable("productId") Long productId)
    {
        Product p = inventoryService.getProductsSortedByExpiry(productId);
        return ResponseEntity.ok(p); 
    }
    
    
    @PostMapping("/update")
    public ResponseEntity<ProductDTO> order(@RequestBody OrderDTO order){
    	
    	ProductDTO productDTO = inventoryService.updateProduct(order);
    	
    	return ResponseEntity.ok(productDTO);
    }
    	


}
