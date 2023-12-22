package org.fpm.di;

import java.util.Map;

public class BinderClass implements Binder {
    private Map<Class<?>, Class<?>> classClassMap;
    private Map<Class<?>, Object> classInstanceMap;

    public BinderClass(Map<Class<?>, Class<?>> classClassMap, Map<Class<?>, Object> classInstanceMap) {
        this.classClassMap = classClassMap;
        this.classInstanceMap = classInstanceMap;
    }

    @Override
    public <T> void bind(Class<T> clazz) {
        this.bind(clazz, clazz);
    }

    @Override
    public <T> void bind(Class<T> clazz, Class<? extends T> implementation) {

        if (clazz == null || implementation == null) {
            throw new IllegalArgumentException("Can't bind null values.");
        }

        if (this.classClassMap.containsKey(clazz) || this.classInstanceMap.containsKey(clazz)) {
            throw new IllegalArgumentException("Can't bind this, " + clazz + " has already been bound.");
        }

        this.classClassMap.put(clazz, implementation);
    }

    @Override
    public <T> void bind(Class<T> clazz, T instance) {

        if (clazz == null || instance == null) {
            throw new IllegalArgumentException("Can't bind null values.");
        }

        if (this.classClassMap.containsKey(clazz) || this.classInstanceMap.containsKey(clazz)) {
            throw new IllegalArgumentException("Can't bind this, " + clazz + " has already been bound.");
        }

        this.classInstanceMap.put(clazz, instance);
    }
}
