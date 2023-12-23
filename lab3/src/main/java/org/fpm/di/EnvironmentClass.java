package org.fpm.di;

import java.util.HashMap;
import java.util.Map;

public class EnvironmentClass implements Environment{
    private Map<Class<?>, Class<?>> classClassMap = new HashMap<>();
    private Map<Class<?>, Object> classInstanceMap = new HashMap<>();
    private BinderClass binder;


    @Override
    public Container configure(Configuration configuration) {
        this.binder = new BinderClass(classClassMap, classInstanceMap);
        configuration.configure(binder);
        return new ContainerClass(classClassMap, classInstanceMap);
    }
}
