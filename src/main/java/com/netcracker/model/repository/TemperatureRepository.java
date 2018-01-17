package com.netcracker.model.repository;


import com.netcracker.model.entites.TemperatureInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "temperature", path = "temperature")
public interface TemperatureRepository extends PagingAndSortingRepository<TemperatureInfo, Long> {

    List<TemperatureInfo> findAllByTemperatureId(@Param("temperature_id") Long temperatureId);
    TemperatureInfo save(TemperatureInfo temperatureInfo);

}
