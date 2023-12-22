package org.fpm.di;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class ContainerClass implements Container {
    private Map<Class<?>, Class<?>> classClassMap;
    private Map<Class<?>, Object> classInstanceMap;

    public ContainerClass(Map<Class<?>, Class<?>> classClassMap, Map<Class<?>, Object> classInstanceMap) {
        this.classClassMap = classClassMap;
        this.classInstanceMap = classInstanceMap;
    }

    @Override
    public <T> T getComponent(Class<T> clazz) {
        if (classInstanceMap.containsKey(clazz)) {
            return (T) classInstanceMap.get(clazz);
        }

        if (classClassMap.containsKey(clazz)) {
            Class<T> requiredClass = (Class<T>) classClassMap.get(clazz);
            if (!clazz.equals(requiredClass) &&
                    (classClassMap.containsKey(requiredClass) || classInstanceMap.containsKey(requiredClass))) {
                return getComponent(requiredClass);
            }
            return getInstance(requiredClass);
        }
        return null;
    }

    private <T> T getInstance(Class<T> clazz) {
        T instance;
        try {
            instance = getInstanceWithDependentConstructor(clazz);
            if (instance == null) {
                Constructor<T> constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                instance = constructor.newInstance();
                if (clazz.getAnnotation(Singleton.class) != null) {
                    classInstanceMap.put(clazz, instance);
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
        return instance;
    }

    private <T> T getInstanceWithDependentConstructor(Class<T> clazz) throws
            InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.getAnnotation(Inject.class) != null) {
                Class<?>[] params = constructor.getParameterTypes();
                int len = params.length;
                Object[] args = new Object[len];
                for (int i = 0; i < len; i++) {
                    args[i] = getComponent(params[i]);
                }
                constructor.setAccessible(true);
                T instance = (T) constructor.newInstance(args);
                if (clazz.getAnnotation(Singleton.class) != null) {
                    classInstanceMap.put(clazz, instance);
                }
                return instance;
            }
        }
        return null;
    }
}
