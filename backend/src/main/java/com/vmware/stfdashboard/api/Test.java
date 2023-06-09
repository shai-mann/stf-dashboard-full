package com.vmware.stfdashboard.api;

import com.vmware.stfdashboard.models.processed.TestEntity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Contains relevant information about a specific {@link TestEntity}
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Test {
    private int id;
    private String name, parameters, suite;

    public Test(int id, String name, String parameters, String suite) {
        this.id = id;
        this.name = name;
        this.parameters = parameters;
        this.suite = suite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

}
