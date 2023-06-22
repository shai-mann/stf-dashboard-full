package com.vmware.stfdashboard.models.processed;

import com.vmware.stfdashboard.processing.Processor;
import com.vmware.stfdashboard.util.SuiteType;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * An {@link Entity} class linked to the processed Tests table, containing types of tests
 * that exist in the various {@link SuiteType}. This data is parsed in the {@link Processor}
 * to extract unique test types (without results) from the list of test results in the unprocessed
 * schema.
 *
 * <p>Tests have links to the {@link UpstreamJobEntity} that is linked to the same {@link SuiteType}
 * that the test is under. It also links to the {@link TestResultEntity} data for runs of this
 * test.</p>
 */
@Entity
@Table(name = "test", schema = "processed")
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "upstream")
    private UpstreamJobEntity upstream;

    @Column
    private String name;

    @Column
    private Integer dataProviderIndex;

    @Column
    private String className;

    @Column
    private String packagePath;

    @Column
    private String parameters;

    @OneToMany(mappedBy = "test")
    @OrderBy("startedAt desc")
    private Set<TestResultEntity> results;

    public TestEntity(UpstreamJobEntity upstream, String name, Integer dataProviderIndex,
                      String className, String packagePath, String parameters) {
        this.upstream = upstream;
        this.name = name;
        this.dataProviderIndex = dataProviderIndex;
        this.className = className;
        this.packagePath = packagePath;
        this.parameters = parameters;
    }

    public TestEntity() {}

    public int getId() {
        return id;
    }

    public UpstreamJobEntity getUpstream() {
        return upstream;
    }

    public String getName() {
        return name;
    }

    public Integer getDataProviderIndex() {
        return dataProviderIndex;
    }

    public String getClassName() {
        return className;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public String getParameters() {
        return parameters;
    }

    public Set<TestResultEntity> getResults() {
        return results;
    }

}
