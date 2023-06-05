package com.vmware.stfdashboard.api;

import com.vmware.stfdashboard.api.meta.AbstractTest;
import com.vmware.stfdashboard.api.meta.RunInfo;
import com.vmware.stfdashboard.util.SddcType;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Essentially a marker class for this type of {@link AbstractTest}.
 * @see RunInfo
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TestRunList extends AbstractTest<List<RunInfo>> {

    public TestRunList(int id, String name, String parameters,
                       String packagePath, String className) {
        super(id, name, parameters, packagePath, className);
    }

    public void addResult(SddcType sddc, RunInfo result) {
        if (this.getResult(sddc) == null) {
            this.addResult(sddc, new ArrayList<>(Collections.singletonList(result)));
        } else {
            this.getResult(sddc).add(result);
        }
    }

}
