package com.vmware.stfdashboard.controllers;

import com.vmware.stfdashboard.util.SddcType;
import com.vmware.stfdashboard.util.SuiteType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class ApplicationController {

    @GetMapping(value = "/sddc-types", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getSddcTypes() {
        return Arrays.stream(SddcType.values()).map(SddcType::value).collect(Collectors.toList());
    }

    @GetMapping(value = "/suite-types", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getSuiteTypes() {
        return Arrays.stream(SuiteType.values()).map(SuiteType::value).collect(Collectors.toList());
    }

}
