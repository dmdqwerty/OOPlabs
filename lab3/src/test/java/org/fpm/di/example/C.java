package org.fpm.di.example;

import javax.inject.Inject;

public class C extends B {
    private A a;
    private B b;

    @Inject
    public C(A a, B b) {
        this.a = a;
        this.b = b;
    }
}
