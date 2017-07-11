package com.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "feedBack")
public class Feedback {
	@Id
	private String id;

	@Field
	private String userEmail;

	@Field
	private String feedBackSubject;

	@Field
	private String feeds;

	@Field
	private String raisedDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getFeedBackSubject() {
		return feedBackSubject;
	}

	public void setFeedBackSubject(String feedBackSubject) {
		this.feedBackSubject = feedBackSubject;
	}

	public String getFeeds() {
		return feeds;
	}

	public void setFeeds(String feeds) {
		this.feeds = feeds;
	}

	public String getRaisedDate() {
		return raisedDate;
	}

	public void setRaisedDate(String raisedDate) {
		this.raisedDate = raisedDate;
	}
}
