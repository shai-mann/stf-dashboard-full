package com.vmware.stfdashboard.repositories.generated;

import com.vmware.stfdashboard.models.generated.JenkinsJob;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JenkinsJobRepository extends CrudRepository<JenkinsJob, Integer> {
    List<JenkinsJob> findAllByNameContains(String value);

    List<JenkinsJob> findAllByNameNotContainsAndNameNotContains(String value, String value2);

}
