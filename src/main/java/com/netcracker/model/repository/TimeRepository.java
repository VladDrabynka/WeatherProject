package com.netcracker.model.repository;

import com.netcracker.model.entites.TimeInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "time", path = "time")
public interface TimeRepository extends PagingAndSortingRepository<TimeInfo, Long> {

    List<TimeInfo> findAllByTimeId(@Param("time_id") Long timeId);
    TimeInfo save(TimeInfo info);
}
