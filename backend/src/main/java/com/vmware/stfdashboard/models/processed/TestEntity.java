package com.vmware.stfdashboard.models.processed;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.util.Set;

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
