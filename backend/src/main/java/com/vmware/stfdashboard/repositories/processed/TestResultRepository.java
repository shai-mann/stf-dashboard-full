package com.vmware.stfdashboard.repositories.processed;

import com.vmware.stfdashboard.models.processed.JobBuildEntity;
import com.vmware.stfdashboard.models.processed.TestEntity;
import com.vmware.stfdashboard.models.processed.TestResultEntity;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestResultRepository extends CrudRepository<TestResultEntity, Integer> {

    int countByTest_IdAndBuildInAndStatus(int testId, List<JobBuildEntity> builds, String status);

    int countByTest_IdAndBuildIn(int testId, List<JobBuildEntity> builds);

    int countAllByBuildAndStatus(JobBuildEntity build, String status);

    Optional<TestResultEntity> findByBuildAndTest(JobBuildEntity build, TestEntity test);

}
