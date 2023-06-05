package com.vmware.stfdashboard.api.builders;

import com.vmware.stfdashboard.api.TestRun;
import com.vmware.stfdashboard.api.TestRunList;
import com.vmware.stfdashboard.api.TestSummary;
import com.vmware.stfdashboard.api.meta.RunInfo;
import com.vmware.stfdashboard.api.meta.RunSummary;
import com.vmware.stfdashboard.util.SddcType;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractTestBuilder<E, T> {

    protected Integer id;

    protected String name, parameters, packagePath, className;

    protected Map<SddcType, T> results = new HashMap<>();

    public static class TestRunBuilder extends AbstractTestBuilder<TestRun, RunInfo> {
        @Override
        public TestRun build() {
            TestRun out = new TestRun(
                    id, name, parameters, packagePath, className
            );

            results.forEach(out::addResult);

            return out;
        }
    }

    public static class TestRunListBuilder extends AbstractTestBuilder<TestRunList, RunInfo> {
        @Override
        public TestRunList build() {
            TestRunList out = new TestRunList(
                    id, name, parameters, packagePath, className
            );

            results.forEach(out::addResult);

            return out;
        }
    }

    public static class TestSummaryBuilder extends AbstractTestBuilder<TestSummary, RunSummary> {
        @Override
        public TestSummary build() {
            TestSummary out = new TestSummary(
                    id, name, parameters, packagePath, className
            );

            results.forEach(out::addResult);

            return out;
        }
    }

    public abstract E build();

    public AbstractTestBuilder<E, T> setId(Integer id) {
        this.id = id;
        return this;
    }

    public AbstractTestBuilder<E, T> setName(String name) {
        this.name = name;
        return this;
    }

    public AbstractTestBuilder<E, T> setParameters(String parameters) {
        this.parameters = parameters;
        return this;
    }

    public AbstractTestBuilder<E, T> setPackagePath(String packagePath) {
        this.packagePath = packagePath;
        return this;
    }

    public AbstractTestBuilder<E, T> setClassName(String className) {
        this.className = className;
        return this;
    }

    public AbstractTestBuilder<E, T> setResults(Map<SddcType, T> results) {
        this.results = results;
        return this;
    }

    public AbstractTestBuilder<E, T> addResult(SddcType sddc, T result) {
        this.results.put(sddc, result);
        return this;
    }

}
