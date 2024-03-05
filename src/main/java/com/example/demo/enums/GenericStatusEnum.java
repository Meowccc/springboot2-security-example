package com.example.demo.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author meow
 */
@Getter
@RequiredArgsConstructor
public enum GenericStatusEnum {

    ACTIVE("active"),
    INACTIVE("inactive"),
    ;

    private final String value;

    private static final Map<String, GenericStatusEnum> ENUMS = Arrays.stream(GenericStatusEnum.values()).collect(Collectors.toUnmodifiableMap(GenericStatusEnum::getValue, Function.identity()));

    public static Optional<GenericStatusEnum> findByValue(final String value) {
        return Optional.ofNullable(ENUMS.get(value));
    }
}
