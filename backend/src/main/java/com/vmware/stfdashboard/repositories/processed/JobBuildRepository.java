package com.vmware.stfdashboard.repositories.processed;

import com.vmware.stfdashboard.models.processed.JobBuildEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobBuildRepository extends CrudRepository<JobBuildEntity, Integer> {}
