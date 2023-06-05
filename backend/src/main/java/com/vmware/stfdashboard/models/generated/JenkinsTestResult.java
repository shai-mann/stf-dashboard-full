package com.vmware.stfdashboard.models.generated;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "test_result", schema = "public")
public class JenkinsTestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(
            name = "job_build_id",
            referencedColumnName = "id"
    )
    private JenkinsJobBuild build;

    @Column
    private String name;

    @Column
    private String status;

    @Column(name = "failed_builds")
    private int[] failedBuilds;

    @Column(name = "skipped_builds")
    private int[] skippedBuilds;

    @Column(name = "passed_builds")
    private int[] passedBuilds;

    @Column(name = "presumed_passed_builds")
    private int[] presumedPassedBuilds;

    @Column(name = "class_name")
    private String className;

    @Column
    private String parameters;

    @Column(name = "package_path")
    private String packagePath;

    @Column
    private String exception;

    @Column
    private double duration;

    @Column(name = "started_at")
    private long startedAt;

    @Column(name = "data_provider_index")
    private Integer dataProviderIndex;

    public int getId() {
        return id;
    }

    public JenkinsJobBuild getBuild() {
        return build;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public int[] getFailedBuilds() {
        return failedBuilds;
    }

    public int[] getSkippedBuilds() {
        return skippedBuilds;
    }

    public int[] getPassedBuilds() {
        return passedBuilds;
    }

    public int[] getPresumedPassedBuilds() {
        return presumedPassedBuilds;
    }

    public String getClassName() {
        return className;
    }

    public String getParameters() {
        return parameters == null ? "[]" : parameters;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public String getException() {
        return exception == null ? "" : exception;
    }

    public double getDuration() {
        return duration;
    }

    public long getStartedAt() {
        return startedAt;
    }

    public int getDataProviderIndex() {
        return dataProviderIndex == null ? 0 : dataProviderIndex;
    }
}
