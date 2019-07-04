package com.lv.cloud.utils;

import org.springframework.lang.Nullable;

public class ObjectUtils {

    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
