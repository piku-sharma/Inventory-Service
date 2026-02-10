package com.sharma.inventory.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OrderDTOTest {

    @Test
    void testOrderDTOSettersAndGetters() {
        OrderDTO dto = new OrderDTO(1001L, 5);

        assertEquals(1001L, dto.getProductId());
        assertEquals(5, dto.getQuantity());
    }

    @Test
    void testOrderDTODefaultValues() {
        OrderDTO dto = new OrderDTO();
        assertNull(dto.getProductId());
        assertNull(dto.getQuantity());
    }

    @Test
    void testOrderDTOEqualsAndHashCode() {
        OrderDTO d1 = new OrderDTO(1L, 2);
        OrderDTO d2 = new OrderDTO(1L, 2);

        assertEquals(d1, d2);
        assertEquals(d1.hashCode(), d2.hashCode());
    }
}
