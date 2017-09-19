package com.science.middleware.datasource;

/**
 * @author kongtong.ouyang on 2017/9/18.
 */
public final class DataSourceContextHolder {

    private static final ThreadLocal<String> DATA_SOURCES = new ThreadLocal<String>();

    public static String get() {
        return DATA_SOURCES.get();
    }

    public static void set(String key) {
        DATA_SOURCES.set(key);
    }

    public static void remove() {
        DATA_SOURCES.remove();
    }

}
