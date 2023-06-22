package com.vmware.stfdashboard.processing;

import com.vmware.WorkflowRunner;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * A {@link Configuration} class responsible for scheduling the {@link Processor} to run
 * on a consistent schedule. Makes use of {@link Dotenv} in order to abstract the data
 * inputting for scraping and processing the Jenkins data.
 */
@Configuration
@EnableScheduling
public class ProcessScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessScheduler.class);

    private static final String WORKFLOW_CONFIG_PATH = "WORKFLOW_CONFIG_PATH";
    private static final String WORKFLOW_DESTINATION_FILE = "WORKFLOW_DESTINATION_FILE";

    @Autowired
    private Processor processor;

    private final Dotenv dotenv = Dotenv.configure().directory("./backend").load();

    @Scheduled(cron = "0 0 */12 * * *")
    public void processData() {
        LOGGER.info("[CRON] Fetching new CToT test data.");
        WorkflowRunner.main(new String[] {
                "FindRealTestFailures",
                "-c=" + dotenv.get(WORKFLOW_CONFIG_PATH),
                "--destination-file=" + dotenv.get(WORKFLOW_DESTINATION_FILE),
                "--disable-login"
        });

        processor.emptyProcessedTables();
        processor.process();
    }

}
