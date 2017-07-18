package com.repository;

import com.model.Mandal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("mandalRepository")
public interface MandalRepository extends MongoRepository<Mandal, String> {
}
