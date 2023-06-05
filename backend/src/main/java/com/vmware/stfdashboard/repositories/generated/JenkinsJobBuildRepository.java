package com.vmware.stfdashboard.repositories.generated;

import com.vmware.stfdashboard.models.generated.JenkinsJob;
import com.vmware.stfdashboard.models.generated.JenkinsJobBuild;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JenkinsJobBuildRepository extends CrudRepository<JenkinsJobBuild, Integer> {

    List<JenkinsJobBuild> findByJobEntity_IdAndBuildNumberIn(int jobId, List<Integer> buildNumbers);

    List<JenkinsJobBuild> findAllByNameContains(String value);

    List<JenkinsJobBuild> findAllByNameNotContainsAndNameNotContains(String value, String v2);

    List<JenkinsJobBuild> findAllByNameNotContainsAndNameNotContainsAndJobEntityNotIn(
            String v1, String v2, List<JenkinsJob> jobs);
}
