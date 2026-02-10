package com.sharma.inventory.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

class BatchTest {

    @Test
    void testBatchSettersAndGetters() {
        Batch batch = new Batch();
        batch.setBatchId(1L);
        batch.setQuantity(10);
        Date expiry = new Date(1700000000000L);
        batch.setExpiryDate(expiry);

        Product product = new Product();
        product.setProductId(1001L);
        batch.setProduct(product);

        assertEquals(1L, batch.getBatchId());
        assertEquals(10, batch.getQuantity());
        assertEquals(expiry, batch.getExpiryDate());
        assertEquals(1001L, batch.getProduct().getProductId());
    }

    @Test
    void testBatchDefaultValues() {
        Batch batch = new Batch();
        assertNull(batch.getBatchId());
        assertEquals(0, batch.getQuantity());
        assertNull(batch.getExpiryDate());
        assertNull(batch.getProduct());
    }

    @Test
    void testBatchEqualsAndHashCode() {
        Batch b1 = new Batch();
        b1.setBatchId(1L);
        Batch b2 = new Batch();
        b2.setBatchId(1L);

        assertEquals(b1, b2);
        assertEquals(b1.hashCode(), b2.hashCode());
    }
}
