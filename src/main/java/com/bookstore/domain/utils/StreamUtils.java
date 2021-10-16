package com.bookstore.domain.utils;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


import static java.util.Collections.emptyList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StreamUtils {

    public static <T, R> List<R> mapToList(List<T> list, Function<T, R> mapper) {
        return Optional.ofNullable(list).orElse(emptyList()).stream().map(mapper).collect(Collectors.toList());
    }
}
