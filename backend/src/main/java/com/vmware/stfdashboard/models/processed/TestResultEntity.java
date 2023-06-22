package com.vmware.stfdashboard.models.processed;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * An {@link Entity} class linked to the processed TestResult table, containing runs of
 * the {@link TestEntity} table.
 *
 * <p>TestResults have links to the {@link TestEntity} that they are a run of and the
 * {@link JobBuildEntity} that triggered them.</p>
 */
@Entity
@Table(name = "TestResult", schema = "processed")
public class TestResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "test")
    private TestEntity test;

    @ManyToOne
    @JoinColumn(name = "build")
    private JobBuildEntity build;

    @Column
    private String status;

    @Column
    private String exception;

    @Column
    private double duration;

    @Column
    private long startedAt;

    public TestResultEntity(TestEntity test, JobBuildEntity build, String status,
                            String exception, double duration, long startedAt) {
        this.test = test;
        this.build = build;
        this.status = status;
        this.exception = exception;
        this.duration = duration;
        this.startedAt = startedAt;
    }

    public TestResultEntity() {}

    public int getId() {
        return id;
    }

    public TestEntity getTest() {
        return test;
    }

    public JobBuildEntity getBuild() {
        return build;
    }

    public String getStatus() {
        return status;
    }

    public String getException() {
        return exception;
    }

    public double getDuration() {
        return duration;
    }

    public long getStartedAt() {
        return startedAt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(test, build, status, exception, duration, startedAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestResultEntity that = (TestResultEntity) o;
        return Double.compare(that.duration, duration) == 0
                && startedAt == that.startedAt && Objects.equals(test, that.test)
                && Objects.equals(status, that.status)
                && Objects.equals(exception, that.exception)
                && Objects.equals(build, that.build);
    }
}
