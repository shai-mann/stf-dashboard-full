package com.vmware.stfdashboard.processing;

import com.vmware.WorkflowRunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ProcessScheduler {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProcessScheduler.class);

    @Autowired
    private Processor processor;

    @Scheduled(cron = "0 * */12 ? * *")
    public void processData() {
        LOGGER.info("[CRON] Fetching new CToT test data.");
        WorkflowRunner.main(("FindRealTestFailures\n" +
                "-c=./workflow-config-file.json\n" +
                "--destination-file=/Users/melisha/Documents/GitHub/h2-temp\n" +
                "--disable-login").split("\n"));

        processor.emptyProcessedTables();
        processor.process();
    }

}
