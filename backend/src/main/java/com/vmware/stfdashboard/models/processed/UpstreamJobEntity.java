package com.vmware.stfdashboard.models.processed;

import com.vmware.stfdashboard.util.SuiteType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.net.URL;
import java.util.List;

@Entity
@Table(name = "UpstreamJob", schema = "processed")
public class UpstreamJobEntity {

    @Id
    private int id;

    @Column
    private String name;

    @Column
    private SuiteType suite;

    @Column
    private URL url;

    @OneToMany(mappedBy = "upstreamJob")
    private List<UpstreamJobBuildEntity> builds;

    @OneToMany(mappedBy = "upstream")
    private List<TestEntity> tests;

    public UpstreamJobEntity(int id, String name, SuiteType suite, URL url) {
        this.id = id;
        this.name = name;
        this.suite = suite;
        this.url = url;
    }

    public UpstreamJobEntity() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public SuiteType getSuite() {
        return suite;
    }

    public URL getUrl() {
        return url;
    }

    public List<UpstreamJobBuildEntity> getBuilds() {
        return builds;
    }

    public List<TestEntity> getTests() {
        return tests;
    }

}