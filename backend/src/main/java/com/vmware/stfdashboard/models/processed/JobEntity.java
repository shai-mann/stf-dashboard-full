package com.vmware.stfdashboard.models.processed;

import com.vmware.stfdashboard.util.SddcType;
import com.vmware.stfdashboard.util.SuiteType;

import java.net.URL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * An {@link Entity} class linked to the processed Job table, containing categories of Jobs.
 * These jobs are distinct from {@link UpstreamJobEntity},
 * since these Jobs are a single {@link SuiteType} against a single {@link SddcType}.
 *
 * <p>Jobs have links to the {@link UpstreamJobEntity} that is linked to the {@link SuiteType}
 * that this Job uses.</p>
 */
@Entity
@Table(name = "Job", schema = "processed")
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "upstream")
    private UpstreamJobEntity upstream;

    @Column
    private String name;

    @Column
    private SddcType sddc;

    @Column
    private URL url;

    public JobEntity(int id, UpstreamJobEntity upstream, String name, SddcType sddc, URL url) {
        this.id = id;
        this.upstream = upstream;
        this.name = name;
        this.sddc = sddc;
        this.url = url;
    }

    public JobEntity() {}

    public int getId() {
        return id;
    }

    public UpstreamJobEntity getUpstream() {
        return upstream;
    }

    public String getName() {
        return name;
    }

    public SddcType getSddc() {
        return sddc;
    }

    public URL getUrl() {
        return url;
    }
}
