package com.vmware.stfdashboard.models.processed;

import com.vmware.stfdashboard.util.Status;

import java.net.URL;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
    private String build;

    @Column
    private URL url;

    @Column
    private Status status;

    @Column
    private long buildTimestamp;

    @OneToMany(mappedBy = "upstreamBuild")
    private List<JobBuildEntity> triggeredJobs;

    public UpstreamJobBuildEntity(int id, UpstreamJobEntity upstreamJob, int buildNumber,
                                  String build, URL url, Status status, long buildTimestamp) {
        this.id = id;
        this.upstreamJob = upstreamJob;
        this.buildNumber = buildNumber;
        this.build = build;
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

    public String getBuild() {
        return build;
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
