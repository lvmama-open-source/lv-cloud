package com.lv.cloud.test;

import java.io.Serializable;

/**
 * @author xiaoyulin
 * @description
 * @date 2019-10-10
 */
public class Foo2 implements Serializable {

    private String foo;

    public Foo2() {
        super();
    }

    public Foo2(String foo) {
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
        return "Foo2 [foo=" + this.foo + "]";
    }

}
