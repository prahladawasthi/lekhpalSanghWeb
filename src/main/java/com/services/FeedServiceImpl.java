package com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.model.Feedback;
import com.repository.FeedRepository;

@Service("feedService")
public class FeedServiceImpl implements FeedService {

	private MongoTemplate mongoTemplate;
	private FeedRepository feedRepository;

	@Autowired
	public FeedServiceImpl(FeedRepository feedRepository, MongoTemplate mongoTemplate) {
		this.feedRepository = feedRepository;
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public void saveFeed(Feedback feedback) {
		mongoTemplate.save(feedback);
	}

	@Override
	public Feedback deleteFeedById(String id) {
		return mongoTemplate.findAndRemove(new Query().addCriteria(Criteria.where("_id").is(id)), Feedback.class);
	}

	@Override
	public List<Feedback> findAllFeeds() {
		return (List<Feedback>) feedRepository.findAll();

	}
	
	@Override
	public Feedback findByID(String id) {
		return mongoTemplate.findById(id, Feedback.class);
	}

}
