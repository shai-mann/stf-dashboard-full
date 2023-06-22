package com.vmware.stfdashboard.models.processed;

import com.vmware.stfdashboard.util.SddcType;
import com.vmware.stfdashboard.util.Status;
import com.vmware.stfdashboard.util.SuiteType;

import java.net.URL;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * An {@link Entity} class linked to the processed JobBuild table, containing builds of
 * the {@link JobEntity} jobs. These job builds are distinct from {@link UpstreamJobBuildEntity},
 * since these JobBuilds are only a run of a single {@link SuiteType} against a single
 * {@link SddcType}.
 *
 * <p>JobBuilds have links to the {@link JobEntity} that they are a build of, the
 * {@link UpstreamJobBuildEntity} that triggered them, and the {@link TestResultEntity} objects
 * containing the results of tests in this build.</p>
 */
@Entity
@Table(name = "JobBuild", schema = "processed")
public class JobBuildEntity {

    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "job")
    private JobEntity job;

    @ManyToOne
    @JoinColumn(name = "upstreamBuild")
    private UpstreamJobBuildEntity upstreamBuild;

    @Column
    private int buildNumber;

    @Column
    private URL url;

    @Column
    private String commitId;

    @Column
    private Status status;

    @Column
    private int failedCount;

    @Column
    private int skippedCount;

    @Column
    private long buildTimestamp;

    @OneToMany(mappedBy = "build")
    private List<TestResultEntity> testResults;

    public JobBuildEntity(int id, JobEntity job, UpstreamJobBuildEntity upstreamBuild,
                          int buildNumber, URL url, String commitId, String status,
                          int failedCount, int skippedCount, long buildTimestamp) {
        this.id = id;
        this.job = job;
        this.upstreamBuild = upstreamBuild;
        this.buildNumber = buildNumber;
        this.url = url;
        this.commitId = commitId;
        this.status = Status.findByValue(status);
        this.failedCount = failedCount;
        this.skippedCount = skippedCount;
        this.buildTimestamp = buildTimestamp;
    }

    public JobBuildEntity() {}

    public int getId() {
        return id;
    }

    public JobEntity getJob() {
        return job;
    }

    public UpstreamJobBuildEntity getUpstreamBuild() {
        return upstreamBuild;
    }

    public int getBuildNumber() {
        return buildNumber;
    }

    public URL getUrl() {
        return url;
    }

    public String getCommitId() {
        return commitId;
    }

    public Status getStatus() {
        return status;
    }

    public int getFailedCount() {
        return failedCount;
    }

    public int getSkippedCount() {
        return skippedCount;
    }

    public long getBuildTimestamp() {
        return buildTimestamp;
    }

    public List<TestResultEntity> getTestResults() {
        return testResults;
    }

}
