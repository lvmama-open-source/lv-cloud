package com.lv.cloud.test;

import java.io.Serializable;

/**
 * @author xiaoyulin
 * @description
 * @date 2019-10-10
 */
public class Foo1 implements Serializable {

    private String foo;

    public Foo1() {
        super();
    }

    public Foo1(String foo) {
        this.foo = foo;
    }

    public String getFoo() {
        return this.foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    @Override
    public String toString() {
        return "Foo1 [foo=" + this.foo + "]";
    }

}
