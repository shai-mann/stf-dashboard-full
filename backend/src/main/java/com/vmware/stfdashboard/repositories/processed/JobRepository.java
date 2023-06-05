package com.vmware.stfdashboard.repositories.processed;

import com.vmware.stfdashboard.models.processed.JobEntity;
import com.vmware.stfdashboard.models.processed.UpstreamJobEntity;
import com.vmware.stfdashboard.util.SddcType;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends CrudRepository<JobEntity, Integer> {

    Optional<JobEntity> findBySddcAndUpstream(SddcType sddc, UpstreamJobEntity upstream);

}
