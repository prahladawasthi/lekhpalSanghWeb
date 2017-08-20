package com.repository;

import com.model.Complain;
import com.model.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("complainRepository")
public interface ComplainRepository extends MongoRepository<Complain, String> {

}
