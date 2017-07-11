package com.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.model.Feedback;

@Repository("feedRepository")
public interface FeedRepository extends MongoRepository<Feedback, String> {

}
