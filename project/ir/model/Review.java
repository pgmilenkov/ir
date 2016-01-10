package ir.model;

import java.util.Date;

public class Review {
	private Date date;
	private long userId;
	private int rating;
	private String message;
	
	public Review() {
		this(new Date(), 0, 1,"message");
	}
	
	public Review(Date d, long u, int r, String m) {
		date = d;
		userId = u;
		rating = r;
		message = m;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String toString() {
		return String.format("Date: %s\nUserid: %d\nRating: %d\nMessage: %s\n",date.toString(), userId, rating, message);
	}
}
