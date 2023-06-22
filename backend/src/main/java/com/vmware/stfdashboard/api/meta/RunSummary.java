package com.vmware.stfdashboard.api.meta;

import com.vmware.stfdashboard.util.Status;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Summarizes many runs of a single test, allowing the information to be formatted
 * like "10/17 Passed" or "12/12 Skipped."
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RunSummary {

    private Integer amount, total;

    private Status status;

    public RunSummary(int amount, int total, Status status) {
        this.amount = amount;
        this.total = total;
        this.status = status;
    }

    public RunSummary() {}

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
