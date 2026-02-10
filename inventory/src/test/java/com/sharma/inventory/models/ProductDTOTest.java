package com.sharma.inventory.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class ProductDTOTest {

    @Test
    void testProductDTOSettersAndGetters() {
        ProductDTO dto = new ProductDTO("Product A", Arrays.asList(1L, 2L));

        assertEquals("Product A", dto.getProductName());
        assertEquals(2, dto.getBatches().size());
    }

    @Test
    void testProductDTODefaultValues() {
        ProductDTO dto = new ProductDTO();
        assertNull(dto.getProductName());
        assertNull(dto.getBatches());
    }

    @Test
    void testProductDTOEqualsAndHashCode() {
        ProductDTO d1 = new ProductDTO("A", Arrays.asList(1L));
        ProductDTO d2 = new ProductDTO("A", Arrays.asList(1L));

        assertEquals(d1, d2);
        assertEquals(d1.hashCode(), d2.hashCode());
    }
}
