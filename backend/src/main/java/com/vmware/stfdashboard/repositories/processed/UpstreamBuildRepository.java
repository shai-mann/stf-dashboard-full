package com.vmware.stfdashboard.repositories.processed;

import com.vmware.stfdashboard.models.processed.UpstreamJobBuildEntity;
import com.vmware.stfdashboard.models.processed.UpstreamJobEntity;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpstreamBuildRepository extends CrudRepository<UpstreamJobBuildEntity, Integer> {

    Optional<UpstreamJobBuildEntity> findFirstByUpstreamJobAndObAndBuildTimestampLessThanEqualOrderByBuildNumberDesc(
            UpstreamJobEntity upstream, long ob, long buildTimestamp
    );

}
