package com.vmware.stfdashboard.util;

public enum SddcType {
    VMC("VMC"),
    AVS("AVS"),
    OCVS("OCVS"),
    GCVE("GCVE");

    private final String value;

    SddcType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static SddcType findByValue(String value) {
        for (SddcType t : values()) {
            if (t.value.equalsIgnoreCase(value)) {
                return t;
            }
        }

        throw new RuntimeException("No SddcType found for " + value);
    }
}
