package com.repository;

import com.model.District;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("districtRepository")
public interface DistrictRepository extends MongoRepository<District, String> {
}
