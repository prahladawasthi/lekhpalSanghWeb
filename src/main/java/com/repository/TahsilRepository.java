package com.repository;

import com.model.Tahsil;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("tahsilRepository")
public interface TahsilRepository extends MongoRepository<Tahsil, String>{
}
