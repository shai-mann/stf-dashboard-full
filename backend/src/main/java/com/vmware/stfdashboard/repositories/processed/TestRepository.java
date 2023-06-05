package com.vmware.stfdashboard.repositories.processed;

import com.vmware.stfdashboard.models.processed.TestEntity;
import com.vmware.stfdashboard.models.processed.UpstreamJobEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends CrudRepository<TestEntity, Integer> {

    Page<TestEntity> findAllByUpstreamAndNameContainingIgnoreCase(UpstreamJobEntity upstream, String filter, Pageable pageable);

    @Query("select t from TestEntity t left join TestResultEntity tr on tr.test = t where t.name ilike concat('%', ?3, '%') and t.upstream.id = ?1 and tr.build.id = ?2 and ?4 ilike concat('%', tr.status, '%') order by tr.startedAt asc")
    Page<TestEntity> findOrderedTestsByUpstreamBuild(int upstreamBuild, int jobBuild, String filter, String status, Pageable pageable);

    @Query("select t from TestEntity t left join TestResultEntity tr on tr.test = t where t.name ilike concat('%', ?3, '%') and t.upstream.id = ?1 and tr.build.id = ?2 and ?4 ilike concat('%', tr.status, '%') order by tr.startedAt desc")
    Page<TestEntity> findOrderedTestsByUpstreamBuildDesc(int upstreamBuild, int jobBuild, String filter, String status, Pageable pageable);

}
