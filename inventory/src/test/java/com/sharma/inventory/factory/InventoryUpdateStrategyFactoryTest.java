package com.sharma.inventory.factory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.sharma.inventory.Strategy.InventoryUpdateStrategy;
import com.sharma.inventory.Strategy.SimpleInventoryUpdateStrategy;
import com.sharma.inventory.Strategy.FefoInventoryUpdateStrategy;

class InventoryUpdateStrategyFactoryTest {

    @Test
    void testGetStrategyReturnsFefoByDefault() {
        InventoryUpdateStrategy fefo = mock(FefoInventoryUpdateStrategy.class);
        InventoryUpdateStrategy simple = mock(SimpleInventoryUpdateStrategy.class);

        InventoryUpdateStrategyFactory factory = new InventoryUpdateStrategyFactory(
                (FefoInventoryUpdateStrategy) fefo,
                (SimpleInventoryUpdateStrategy) simple
        );

        InventoryUpdateStrategy result = factory.getStrategy("UNKNOWN");

        assertNotNull(result);
        assertEquals(fefo, result);
    }

    @Test
    void testGetStrategyReturnsSimple() {
        InventoryUpdateStrategy fefo = mock(FefoInventoryUpdateStrategy.class);
        InventoryUpdateStrategy simple = mock(SimpleInventoryUpdateStrategy.class);

        InventoryUpdateStrategyFactory factory = new InventoryUpdateStrategyFactory(
                (FefoInventoryUpdateStrategy) fefo,
                (SimpleInventoryUpdateStrategy) simple
        );

        InventoryUpdateStrategy result = factory.getStrategy("SIMPLE");

        assertNotNull(result);
        assertEquals(simple, result);
    }

    @Test
    void testGetStrategyReturnsFefo() {
        InventoryUpdateStrategy fefo = mock(FefoInventoryUpdateStrategy.class);
        InventoryUpdateStrategy simple = mock(SimpleInventoryUpdateStrategy.class);

        InventoryUpdateStrategyFactory factory = new InventoryUpdateStrategyFactory(
                (FefoInventoryUpdateStrategy) fefo,
                (SimpleInventoryUpdateStrategy) simple
        );

        InventoryUpdateStrategy result = factory.getStrategy("FEFO");

        assertNotNull(result);
        assertEquals(fefo, result);
    }
}
