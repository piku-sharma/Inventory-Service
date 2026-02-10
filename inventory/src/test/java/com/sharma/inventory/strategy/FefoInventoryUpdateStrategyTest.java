package com.sharma.inventory.strategy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sharma.inventory.Strategy.FefoInventoryUpdateStrategy;
import com.sharma.inventory.models.Batch;
import com.sharma.inventory.models.OrderDTO;
import com.sharma.inventory.models.Product;
import com.sharma.inventory.models.ProductDTO;
import com.sharma.inventory.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class FefoInventoryUpdateStrategyTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private FefoInventoryUpdateStrategy strategy;

    @Test
    void testUpdateSuccessSingleBatch() {
        Batch b1 = new Batch();
        b1.setBatchId(1L);
        b1.setQuantity(10);
        b1.setExpiryDate(new Date(1700000000000L));

        Product product = new Product();
        product.setProductId(1001L);
        product.setProductName("Product A");
        product.setBatches(Arrays.asList(b1));

        OrderDTO order = new OrderDTO();
        order.setProductId(1001L);
        order.setQuantity(3);

        when(productRepository.findById(1001L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO result = strategy.update(order);

        assertNotNull(result);
        assertEquals("Product A", result.getProductName());
        assertEquals(1, result.getBatches().size());
        assertEquals(7, product.getBatches().get(0).getQuantity());
    }

    @Test
    void testUpdateSuccessMultipleBatches() {
        Batch b1 = new Batch();
        b1.setBatchId(1L);
        b1.setQuantity(2);
        b1.setExpiryDate(new Date(1700000000000L));

        Batch b2 = new Batch();
        b2.setBatchId(2L);
        b2.setQuantity(5);
        b2.setExpiryDate(new Date(1800000000000L));

        Product product = new Product();
        product.setProductId(1002L);
        product.setProductName("Product B");
        product.setBatches(Arrays.asList(b1, b2));

        OrderDTO order = new OrderDTO();
        order.setProductId(1002L);
        order.setQuantity(5);

        when(productRepository.findById(1002L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO result = strategy.update(order);

        assertNotNull(result);
        assertEquals(2, result.getBatches().size());
        assertEquals(0, b1.getQuantity());
        assertEquals(2, b2.getQuantity());
    }

    @Test
    void testUpdateOutOfStock() {
        Batch b1 = new Batch();
        b1.setBatchId(1L);
        b1.setQuantity(0);
        b1.setExpiryDate(new Date(1700000000000L));

        Product product = new Product();
        product.setProductId(1003L);
        product.setProductName("Product C");
        product.setBatches(Arrays.asList(b1));

        OrderDTO order = new OrderDTO();
        order.setProductId(1003L);
        order.setQuantity(1);

        when(productRepository.findById(1003L)).thenReturn(Optional.of(product));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> strategy.update(order));
        assertEquals("Item Out of Stock", ex.getMessage());
    }

    @Test
    void testUpdateQuantityExceeded() {
        Batch b1 = new Batch();
        b1.setBatchId(1L);
        b1.setQuantity(2);
        b1.setExpiryDate(new Date(1700000000000L));

        Product product = new Product();
        product.setProductId(1004L);
        product.setProductName("Product D");
        product.setBatches(Arrays.asList(b1));

        OrderDTO order = new OrderDTO();
        order.setProductId(1004L);
        order.setQuantity(5);

        when(productRepository.findById(1004L)).thenReturn(Optional.of(product));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> strategy.update(order));
        assertTrue(ex.getMessage().contains("Quantity exceeded"));
    }

    @Test
    void testUpdateProductNotFound() {
        OrderDTO order = new OrderDTO();
        order.setProductId(999L);
        order.setQuantity(1);

        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> strategy.update(order));
        assertEquals("Product not found", ex.getMessage());
    }
}
