package com.vmware.stfdashboard.repositories.processed;

import com.vmware.stfdashboard.models.processed.UpstreamJobEntity;
import com.vmware.stfdashboard.util.SuiteType;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpstreamJobRepository extends CrudRepository<UpstreamJobEntity, Integer> {

    Optional<UpstreamJobEntity> findFirstBySuite(SuiteType suite);

}
