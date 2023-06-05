package com.vmware.stfdashboard.api.meta;

import com.vmware.stfdashboard.util.SddcType;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.util.HashMap;
import java.util.Map;

/**
 * An API Model containing information about a test and the runs of that test for a given
 * Upstream job.
 *
 * <p>The data type for the results is abstracted to easily allow different types of information
 * about the individual results to be displayed.</p>
 * @param <T> the model type for the results of this test.
 * @see RunSummary
 * @see RunInfo
 * @see AbstractUpstream
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public abstract class AbstractTest<T> {

    private Integer id;

    private String name, parameters, packagePath, className;

    private Map<SddcType, T> results = new HashMap<>();

    public AbstractTest(int id, String name, String parameters, String packagePath, String className) {
        this.id = id;
        this.name = name;
        this.parameters = parameters;
        this.packagePath = packagePath;
        this.className = className;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void addResult(SddcType sddc, T result) {
        this.results.put(sddc, result);
    }

    public T getResult(SddcType sddc) {
        return this.results.get(sddc);
    }
}
