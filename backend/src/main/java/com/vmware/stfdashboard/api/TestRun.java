package com.vmware.stfdashboard.api;

import com.vmware.stfdashboard.api.meta.AbstractTest;
import com.vmware.stfdashboard.api.meta.RunInfo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Essentially a marker class for this type of {@link AbstractTest}.
 * @see RunInfo
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TestRun extends AbstractTest<RunInfo> {

    public TestRun(int id, String name, String parameters, String packagePath, String className) {
        super(id, name, parameters, packagePath, className);
    }

    public TestRun(int id, String name, String parameters) {
        this(id, name, parameters, null, null);
    }

}
