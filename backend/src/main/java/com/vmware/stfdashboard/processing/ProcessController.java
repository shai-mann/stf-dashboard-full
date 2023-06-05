package com.vmware.stfdashboard.processing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProcessController {

    @Autowired
    private Processor processor;

    @PostMapping(value = "/process-data", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean processData() {
        processor.emptyProcessedTables();
        processor.process();
        return true;
    }

}
