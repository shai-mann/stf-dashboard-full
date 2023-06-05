package com.vmware.stfdashboard.models.processed;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;

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
