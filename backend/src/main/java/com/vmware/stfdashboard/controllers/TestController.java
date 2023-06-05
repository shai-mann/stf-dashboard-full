package com.vmware.stfdashboard.controllers;

import com.vmware.stfdashboard.api.Test;
import com.vmware.stfdashboard.api.TestRun;
import com.vmware.stfdashboard.api.TestRunList;
import com.vmware.stfdashboard.api.TestSummary;
import com.vmware.stfdashboard.api.UpstreamRun;
import com.vmware.stfdashboard.models.processed.UpstreamJobBuildEntity;
import com.vmware.stfdashboard.services.BuildService;
import com.vmware.stfdashboard.services.TestService;
import com.vmware.stfdashboard.util.SuiteType;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/tests")
public class TestController {

    @Autowired
    private TestService service;

    @Autowired
    private BuildService buildService;

    @GetMapping(value = "/results", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<TestRun> getTestResults(@RequestParam int buildId,
                                        @RequestParam(required = false, defaultValue = "1") int page,
                                        @RequestParam(required = false, defaultValue = "25") int itemsPerPage,
                                        @RequestParam Optional<String[]> filter,
                                        @RequestParam Optional<String> orderingColumn,
                                        @RequestParam Optional<Boolean> reversed) {
        // build ID is the UPSTREAM build ID
        UpstreamJobBuildEntity upstreamBuild = buildService.getUpstreamBuild(buildId);
        return service.compileResults(
                upstreamBuild, page, itemsPerPage,
                filter, orderingColumn, reversed
        );
    }

    @GetMapping(value = "/summaries", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<TestSummary> getTestSummaries(@RequestParam String suite,
                                              @RequestParam(required = false, defaultValue = "1") int page,
                                              @RequestParam(required = false, defaultValue = "25") int itemsPerPage,
                                              @RequestParam Optional<String> filter) {
        SuiteType suiteType = SuiteType.findByValue(suite);
        return service.compileResults(buildService.getUpstreamJob(suiteType),
                page, itemsPerPage, filter);
    }

    @GetMapping(value = "/runs", produces = MediaType.APPLICATION_JSON_VALUE)
    public TestRunList getTestRuns(@RequestParam int id,
                                   @RequestParam Optional<Integer> build) {
        // build is Upstream Build ID
        return service.getTestRunResults(service.getTestById(id), build);
    }

    @GetMapping(value = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UpstreamRun> getTestHistory(int id) {
        return service.getTestHistory(service.getTestById(id));
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Test getTest(int id) {
        return service.getTest(id);
    }

}
