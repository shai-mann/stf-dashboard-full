package com.vmware.stfdashboard.models.generated;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.net.URL;

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
