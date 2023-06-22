package com.vmware.stfdashboard.models.generated;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.util.StringUtils;

/**
 * An {@link Entity} class linked to the Jenkins TestResult table. TestResults contain
 * information about a specific test run in a specific Job Build. However,
 * this table is aggregated strangely (by nature of how the Workflow tools project
 * scrapes Jenkins data). Aggregation appears to be done based on test type (which is uniquely
 * identified by a combination of {@code name}, {@code packagePath}, {@code className},
 * and {@code dataProviderIndex}.
 *
 * <p>The {@code dataProviderIndex} is a way of uniquely separating the different types of tests
 * with the same name - since sometimes a test is run multiple times with different
 * {@code parameters}. The workflow tools notates the data provider index as either null (a stand
 * in for 0) or an incrementing number unique to that test. </p>
 */
@Entity
@Table(name = "test_result", schema = "public")
@TypeDefs({
        @TypeDef(
                name = "string-array",
                typeClass = StringArrayType.class
        ),
        @TypeDef(
                name = "int-array",
                typeClass = IntArrayType.class
        )
})
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

    @Type(type = "int-array")
    @Column(name = "failed_builds", columnDefinition = "int array")
    private int[] failedBuilds;

    @Type(type = "int-array")
    @Column(name = "skipped_builds", columnDefinition = "int array")
    private int[] skippedBuilds;

    @Type(type = "int-array")
    @Column(name = "passed_builds", columnDefinition = "int array")
    private int[] passedBuilds;

    @Type(type = "int-array")
    @Column(name = "presumed_passed_builds", columnDefinition = "int array")
    private int[] presumedPassedBuilds;

    @Column(name = "class_name")
    private String className;

    @Type(type = "string-array")
    @Column(name = "parameters", columnDefinition = "varchar array")
    private Object parameters;

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
        return parameters == null ? "[]"
                : "[" + Arrays.stream(((Object[]) parameters)).map(
                        o -> (String) o).collect(Collectors.joining(","))
                + "]";
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
