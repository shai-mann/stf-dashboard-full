package com.vmware.stfdashboard.models.generated;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.net.URL;

@Entity
@Table(name = "job_build", schema = "public")
public class JenkinsJobBuild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private JenkinsJob jobEntity;

    @Column
    private String name;

    @Column
    private URL url;

    @Column(name = "build_number")
    private int buildNumber;

    @Column(name = "commit_id")
    private String commitId;

    @Column
    private String status;

    @Column(name = "failed_count")
    private int failedCount;

    @Column(name = "skipped_count")
    private int skippedCount;

    @Column(name = "build_timestamp")
    private long buildTimestamp;

    public int getId() {
        return id;
    }

    public JenkinsJob getJobEntity() {
        return jobEntity;
    }

    public String getName() {
        return name;
    }

    public URL getUrl() {
        return url;
    }

    public int getBuildNumber() {
        return buildNumber;
    }

    public String getCommitId() {
        return commitId;
    }

    public String getStatus() {
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

}
