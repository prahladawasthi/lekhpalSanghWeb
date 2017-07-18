package com.services;

import java.util.List;

import com.model.Feedback;

public interface FeedService {

	 void saveFeed(Feedback feedback);

	 Feedback deleteFeedById(String id);

	 List<Feedback> findAllFeeds();

	 Feedback findByID(String id);

}