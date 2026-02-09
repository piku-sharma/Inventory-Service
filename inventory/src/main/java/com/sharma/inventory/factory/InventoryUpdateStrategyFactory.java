package com.sharma.inventory.factory;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.sharma.inventory.Strategy.FefoInventoryUpdateStrategy;
import com.sharma.inventory.Strategy.InventoryUpdateStrategy;
import com.sharma.inventory.Strategy.SimpleInventoryUpdateStrategy;

@Component
public class InventoryUpdateStrategyFactory {
	private final Map<String, InventoryUpdateStrategy> strategyMap;

    public InventoryUpdateStrategyFactory(
            FefoInventoryUpdateStrategy fefo,
            SimpleInventoryUpdateStrategy simple) {

        strategyMap = new HashMap<>();
        strategyMap.put("FEFO", fefo);
        strategyMap.put("SIMPLE", simple);
    }

    public InventoryUpdateStrategy getStrategy(String type) {
        return strategyMap.getOrDefault(type, strategyMap.get("FEFO"));
    }

	

}
