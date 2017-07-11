package com.services;

import java.util.List;

import com.model.Feedback;

public interface FeedService {

	public void saveFeed(Feedback feedback);

	public Feedback deleteFeedById(String id);

	public List<Feedback> findAllFeeds();

	public Feedback findByID(String id);

}