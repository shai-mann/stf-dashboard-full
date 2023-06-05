package com.vmware.stfdashboard.models.processed;

import com.vmware.stfdashboard.util.SddcType;

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
