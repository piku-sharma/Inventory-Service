package com.sharma.inventory.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.sharma.inventory.Controllers.Inventory;
import com.sharma.inventory.models.Batch;
import com.sharma.inventory.models.OrderDTO;
import com.sharma.inventory.models.Product;
import com.sharma.inventory.models.ProductDTO;
import com.sharma.inventory.services.InventoryService;

@ExtendWith(MockitoExtension.class)
class InventoryControllerTest {

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private Inventory inventoryController;

    @Test
    void testGetProductsSortedByExpiryDate() {
        Batch b1 = new Batch();
        b1.setBatchId(1L);
        b1.setExpiryDate(new Date(1700000000000L));

        Batch b2 = new Batch();
        b2.setBatchId(2L);
        b2.setExpiryDate(new Date(1800000000000L));

        List<Batch> batches = Arrays.asList(b1, b2);
        when(inventoryService.getProductsSortedByExpiryDate(1001L)).thenReturn(batches);

        ResponseEntity<List<Batch>> response = inventoryController.getProductsSortedByExpiryDate(1001L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getBatchId());
    }

    @Test
    void testGetProductsSortedByExpiry() {
        Product product = new Product();
        product.setProductId(1001L);
        product.setProductName("Product A");

        when(inventoryService.getProductsSortedByExpiry(1001L)).thenReturn(product);

        ResponseEntity<Product> response = inventoryController.getProductsSortedByExpiry(1001L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1001L, response.getBody().getProductId());
        assertEquals("Product A", response.getBody().getProductName());
    }

    @Test
    void testOrderUpdate() {
        OrderDTO order = new OrderDTO();
        order.setProductId(1002L);
        order.setQuantity(3);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("Product B");
        productDTO.setBatches(Arrays.asList(10L, 11L));

        when(inventoryService.updateProduct(any(OrderDTO.class))).thenReturn(productDTO);

        ResponseEntity<ProductDTO> response = inventoryController.order(order);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Product B", response.getBody().getProductName());
        assertEquals(2, response.getBody().getBatches().size());
    }
}
