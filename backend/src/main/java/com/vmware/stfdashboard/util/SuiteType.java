package com.vmware.stfdashboard.util;

/**
 * An enum containing the test suites supported by this project.
 */
public enum SuiteType {
    SMOKE("Smoke"),
    VDC_SERVICE_A("VdcService-A"),
    VDC_SERVICE_B("VdcService-B"),
    VDC_SERVICE_C("VdcService-C"),
    VDC_SERVICE_D("VdcService-D"),
    NETWORKING("Networking"),
    MULTITENANCY("Multitenancy");

    private final String value;

    SuiteType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static SuiteType findByValue(String value) {
        for (SuiteType t : values()) {
            if (t.value.equalsIgnoreCase(value)) {
                return t;
            }
        }

        throw new IllegalArgumentException("No SuiteType found for " + value);
    }
}
