package com.vmware.stfdashboard.api;

import com.vmware.stfdashboard.api.meta.AbstractTest;
import com.vmware.stfdashboard.api.meta.RunSummary;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Essentially a marker class for this type of {@link AbstractTest}.
 * @see RunSummary
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TestSummary extends AbstractTest<RunSummary> {

    public TestSummary(int id, String name, String parameters, String packagePath, String className) {
        super(id, name, parameters, packagePath, className);
    }

}
