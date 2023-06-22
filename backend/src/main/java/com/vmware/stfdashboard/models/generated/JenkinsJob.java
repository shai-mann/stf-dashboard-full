package com.vmware.stfdashboard.models.generated;

import java.net.URL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * An {@link Entity} class connected to the Jenkins Job table. A job represents
 * a type of Jenkins Job that can be built and run. These are not distinguished from
 * Upstream Jobs in this (unprocessed) schema.
 */
@Entity
@Table(name = "job", schema = "public")
public class JenkinsJob {

    @Id
    private int id;

    @Column
    private String name;

    @Column
    private URL url;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public URL getUrl() {
        return url;
    }

}
