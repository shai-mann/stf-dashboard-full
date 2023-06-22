package com.vmware.stfdashboard.api.builders;

import com.vmware.stfdashboard.api.Test;

/**
 * A builder for the {@link Test} class.
 */
public class TestBuilder {

    private int id;
    private String name, parameters, suite;

    public Test build() {
        return new Test(id, name, parameters, suite);
    }

    public TestBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public TestBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public TestBuilder setParameters(String parameters) {
        this.parameters = parameters;
        return this;
    }

    public TestBuilder setSuite(String suite) {
        this.suite = suite;
        return this;
    }
}
