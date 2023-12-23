package org.fpm.di.example;

import org.fpm.di.Container;
import org.fpm.di.Environment;
import org.fpm.di.EnvironmentClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertSame;

public class MyOwnTests {
    private Container container;

    @Before
    public void setUp() {
        Environment env = new EnvironmentClass();
        container = env.configure(new MyOwnConfig());
    }

    @Test
    public void shouldBuildInjectionGraph() {

        final C cAsSingleton = container.getComponent(C.class);
        assertSame(container.getComponent(A.class), cAsSingleton);
        assertSame(container.getComponent(B.class), cAsSingleton);
        assertSame(container.getComponent(C.class), cAsSingleton);
    }

    @Test
    public void stringUsageTest() {
        assertSame(container.getComponent(String.class), "Hello, world");
        System.out.println(container.getComponent(String.class));
    }
}
