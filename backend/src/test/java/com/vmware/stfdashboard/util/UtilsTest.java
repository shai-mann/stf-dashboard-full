package com.vmware.stfdashboard.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UtilsTest {

    @Test
    void extractSddcType() {
        assertEquals(SddcType.AVS, Utils.extractSddcType("CToT-CDS-AVS-Multitenancy #54 ob-21893"));

        assertEquals(SddcType.GCVE, Utils.extractSddcType("CToT-CDS-GCVE-Networking #873 ob-21893"));

        assertEquals(SddcType.AVS, Utils.extractSddcType("CToT-CDS-AVS-VdcService-D #52 ob-21621086"));
    }

    @Test
    void extractSuiteType() {
        assertEquals(SuiteType.MULTITENANCY, Utils.extractSuiteType("CToT-CDS-AVS-Multitenancy #54 ob-21893"));

        assertEquals(SuiteType.NETWORKING, Utils.extractSuiteType("CToT-CDS-GCVE-Networking #873 ob-21893"));

        // build name
        assertEquals(SuiteType.VDC_SERVICE_D, Utils.extractSuiteType("CToT-CDS-AVS-VdcService-D #52 ob-21621086"));

        // job name
        assertEquals(SuiteType.VDC_SERVICE_C, Utils.extractSuiteType("CToT-CDS-AVS-VdcService-C"));
    }

    @Test
    void extractBuildNumber() {
        assertEquals(9283193, Utils.extractBuildNumber("CToT-CDS-GCVE-Networking #912 ob-9283193"));

        assertEquals(21621086, Utils.extractBuildNumber("CToT-CDS-AVS-VdcService-D #52 ob-21621086"));
    }
}