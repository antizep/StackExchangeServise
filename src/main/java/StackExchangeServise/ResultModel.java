package StackExchangeServise;

import java.util.Date;

public class ResultModel {

	private String dateQuestion;
	private String title;
	private String whoPosted;
	private String link;
	private boolean answered;
	
	public boolean isAnswered() {
		return answered;
	}

	public void setAnswered(boolean answered) {
		this.answered = answered;
	}

	public String getDateQuestion() {
		return dateQuestion;
	}
	
	public void setDateQuestion(String dateQuestion) {
		this.dateQuestion = dateQuestion;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWhoPosted() {
		return whoPosted;
	}
	public void setWhoPosted(String whoPosted) {
		this.whoPosted = whoPosted;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	
}
