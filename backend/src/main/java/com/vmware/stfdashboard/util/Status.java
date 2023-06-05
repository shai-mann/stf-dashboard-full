package com.vmware.stfdashboard.util;

public enum Status {
    /* Test Result Status Options */
    FAILED("FAIL"),
    PASSED("PASS"),
    SKIPPED("SKIP"),
    MISSING("MISSING"),

    /* Job Build Status Options */
    ABORTED("ABORTED"),
    FAILURE("FAILURE"),
    SUCCESS("SUCCESS"),
    UNSTABLE("UNSTABLE");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static Status findByValue(String value) {
        for (Status s : values()) {
            if (s.value().equals(value)) return s;
        }

        throw new IllegalArgumentException("Failed to find Status for value " + value);
    }
}
