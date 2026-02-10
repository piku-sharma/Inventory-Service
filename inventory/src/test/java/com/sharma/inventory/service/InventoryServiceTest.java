package com.sharma.inventory.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.sharma.inventory.Strategy.InventoryUpdateStrategy;
import com.sharma.inventory.factory.InventoryUpdateStrategyFactory;
import com.sharma.inventory.models.Batch;
import com.sharma.inventory.models.OrderDTO;
import com.sharma.inventory.models.Product;
import com.sharma.inventory.models.ProductDTO;
import com.sharma.inventory.repository.ProductRepository;
import com.sharma.inventory.services.InventoryService;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private InventoryUpdateStrategyFactory factory;

    @Mock
    private InventoryUpdateStrategy strategy;

    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        inventoryService = new InventoryService(factory);
        ReflectionTestUtils.setField(inventoryService, "productRepository", productRepository);
    }

    @Test
    void testGetProductsSortedByExpiryDate() {
        Batch b1 = new Batch();
        b1.setBatchId(1L);
        b1.setExpiryDate(new Date(1800000000000L));

        Batch b2 = new Batch();
        b2.setBatchId(2L);
        b2.setExpiryDate(new Date(1700000000000L));

        Product product = new Product();
        product.setProductId(1001L);
        product.setBatches(Arrays.asList(b1, b2));

        when(productRepository.findById(1001L)).thenReturn(Optional.of(product));

        List<Batch> result = inventoryService.getProductsSortedByExpiryDate(1001L);

        assertEquals(2, result.size());
        assertEquals(2L, result.get(0).getBatchId());
        assertEquals(1L, result.get(1).getBatchId());
    }

    @Test
    void testGetProductsSortedByExpiryDateProductNotFound() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> inventoryService.getProductsSortedByExpiryDate(999L));

        assertEquals("Product not found", ex.getMessage());
    }

    @Test
    void testUpdateProductUsesFefoStrategy() {
        OrderDTO order = new OrderDTO();
        order.setProductId(1002L);
        order.setQuantity(5);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("Product B");

        when(factory.getStrategy("FEFO")).thenReturn(strategy);
        when(strategy.update(any(OrderDTO.class))).thenReturn(productDTO);

        ProductDTO result = inventoryService.updateProduct(order);

        assertNotNull(result);
        assertEquals("Product B", result.getProductName());
        verify(factory, times(1)).getStrategy("FEFO");
        verify(strategy, times(1)).update(order);
    }

    @Test
    void testGetProductsSortedByExpiry() {
        Product product = new Product();
        product.setProductId(1003L);
        product.setProductName("Product C");

        when(productRepository.findById(1003L)).thenReturn(Optional.of(product));

        Product result = inventoryService.getProductsSortedByExpiry(1003L);

        assertEquals(1003L, result.getProductId());
        assertEquals("Product C", result.getProductName());
    }
}
