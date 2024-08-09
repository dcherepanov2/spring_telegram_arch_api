package com.example.spring.telegram.wrapper.strategy;

import lombok.Getter;

import java.util.Arrays;
import java.util.function.Predicate;
import com.example.spring.telegram.wrapper.exceptions.ApplierNotFoundException;


@Getter
public enum BotStrategyType {

    WITH_PARAMS(1, isSuitableWithParam()),
    DEFAULT(2, isSuitableDefault());

    private final int priority;
    private final Predicate<String> isSuitable;

    BotStrategyType(int priority, Predicate<String> isSuitable) {
        this.priority = priority;
        this.isSuitable = isSuitable;
    }

    private static Predicate<String> isSuitableDefault(){
        return url -> true;
    }

    private static Predicate<String> isSuitableWithParam(){
        return url -> url.contains("?") && url.contains("=");
    }

    public static BotStrategyType defineStrategyType(String url) {
        return Arrays.stream(values())
                .filter(strategy -> strategy.isSuitable.test(url))
                .findFirst()
                .orElseThrow(ApplierNotFoundException::new);
    }
}
