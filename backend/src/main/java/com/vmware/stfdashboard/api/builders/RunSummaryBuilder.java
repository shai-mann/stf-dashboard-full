package com.vmware.stfdashboard.api.builders;

import com.vmware.stfdashboard.api.meta.RunSummary;
import com.vmware.stfdashboard.util.Status;

/**
 * A builder for the {@link RunSummary} class.
 */
public class RunSummaryBuilder {

    private Integer amount = 0, total = 0;

    private Status status = Status.PASSED;

    public RunSummary build() {
        return new RunSummary(amount, total, status);
    }

    public RunSummaryBuilder setAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public RunSummaryBuilder setTotal(Integer total) {
        this.total = total;
        return this;
    }

    public RunSummaryBuilder setStatus(Status status) {
        this.status = status;
        return this;
    }
}
