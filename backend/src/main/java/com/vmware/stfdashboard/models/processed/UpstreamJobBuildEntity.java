package com.vmware.stfdashboard.models.processed;

import com.vmware.stfdashboard.util.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.net.URL;
import java.util.List;

@Entity
@Table(name = "UpstreamBuild", schema = "processed")
public class UpstreamJobBuildEntity {

    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "upstreamJob")
    private UpstreamJobEntity upstreamJob;

    @Column
    private int buildNumber;

    @Column
    private long ob;

    @Column
    private URL url;

    @Column
    private Status status;

    @Column
    private long buildTimestamp;

    @OneToMany(mappedBy = "upstreamBuild")
    private List<JobBuildEntity> triggeredJobs;

    public UpstreamJobBuildEntity(int id, UpstreamJobEntity upstreamJob, int buildNumber,
                                  long ob, URL url, Status status, long buildTimestamp) {
        this.id = id;
        this.upstreamJob = upstreamJob;
        this.buildNumber = buildNumber;
        this.ob = ob;
        this.url = url;
        this.status = status;
        this.buildTimestamp = buildTimestamp;
    }

    public UpstreamJobBuildEntity() {}

    public int getId() {
        return id;
    }

    public UpstreamJobEntity getUpstreamJob() {
        return upstreamJob;
    }

    public int getBuildNumber() {
        return buildNumber;
    }

    public long getOb() {
        return ob;
    }

    public URL getUrl() {
        return url;
    }

    public Status getStatus() {
        return status;
    }

    public long getBuildTimestamp() {
        return buildTimestamp;
    }

    public List<JobBuildEntity> getTriggeredJobs() {
        return triggeredJobs;
    }

}
