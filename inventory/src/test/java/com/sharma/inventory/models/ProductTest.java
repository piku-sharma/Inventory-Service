package com.sharma.inventory.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void testProductSettersAndGetters() {
        Product product = new Product();
        product.setProductId(1001L);
        product.setProductName("Product A");

        Batch b1 = new Batch();
        b1.setBatchId(1L);
        Batch b2 = new Batch();
        b2.setBatchId(2L);

        List<Batch> batches = Arrays.asList(b1, b2);
        product.setBatches(batches);

        assertEquals(1001L, product.getProductId());
        assertEquals("Product A", product.getProductName());
        assertEquals(2, product.getBatches().size());
    }

    @Test
    void testProductDefaultValues() {
        Product product = new Product();
        assertNull(product.getProductId());
        assertNull(product.getProductName());
        assertNotNull(product.getBatches());
        assertTrue(product.getBatches().isEmpty());
    }

    @Test
    void testProductEqualsAndHashCode() {
        Product p1 = new Product();
        p1.setProductId(1L);
        p1.setProductName("A");

        Product p2 = new Product();
        p2.setProductId(1L);
        p2.setProductName("A");

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }
}
