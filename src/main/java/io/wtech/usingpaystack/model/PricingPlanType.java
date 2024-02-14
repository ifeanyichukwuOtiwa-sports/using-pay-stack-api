package io.wtech.usingpaystack.model;

import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum PricingPlanType {

    BASIC("BASIC"),
    STANDARD("STANDARD"),
    PREMIUM("PREMIUM");

    private final String value;

    private static final Map<String, PricingPlanType> map;
    PricingPlanType(String value) {
        this.value = value;
    }

    public static PricingPlanType fromValue(String value) {
        return map.getOrDefault(value, PricingPlanType.BASIC);
    }

    static {
        map = Stream.of(PricingPlanType.values())
                .map(p -> Map.entry(p.getValue().toUpperCase(), p))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
