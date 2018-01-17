package com.netcracker.model.repository;


import com.netcracker.model.entites.GeneralInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "general", path = "general")
public interface GeneralRepository extends PagingAndSortingRepository<GeneralInfo, Long> {

    List<GeneralInfo> findAllByGeneralId(@Param("general_id") Long generalId);
    GeneralInfo save(GeneralInfo generalInfo);
}
