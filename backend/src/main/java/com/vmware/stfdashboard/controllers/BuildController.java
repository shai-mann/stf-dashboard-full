package com.vmware.stfdashboard.controllers;

import com.vmware.stfdashboard.api.BuildSummary;
import com.vmware.stfdashboard.api.UpstreamInfo;
import com.vmware.stfdashboard.api.UpstreamSummary;
import com.vmware.stfdashboard.services.BuildService;
import com.vmware.stfdashboard.util.SddcType;
import com.vmware.stfdashboard.util.SuiteType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/builds")
public class BuildController {

    @Autowired
    private BuildService buildService;

    @GetMapping(value = "/upstream", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UpstreamSummary> getUpstreamJobs(@RequestParam String suite,
                                                 @RequestParam Optional<String[]> filters) {
        return buildService.getUpstreamBuilds(SuiteType.findByValue(suite), filters);
    }

    @GetMapping(value = "/upstream/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UpstreamInfo getUpstreamJob(@PathVariable int id) {
        return buildService.getUpstreamJob(id);
    }

    @GetMapping(value = "/summary/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<SddcType, BuildSummary> getBuildSummary(@PathVariable int id) {
        // id is an upstream build id
        return buildService.getBuildSummary(id);
    }

}
