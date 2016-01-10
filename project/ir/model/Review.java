package ir.model;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "date", "user_id", "rating", "message"})
public class Review {
	private String date;
	private String userId;
	private int rating;
	private String message;
	
	public Review() {
		this("", "", 1,"message");
	}
	
	public Review(String d, String u, int r, String m) {
		date = d;
		userId = u;
		rating = r;
		message = m;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUser_id() {
		return userId;
	}

	public void setUser_id(String userId) {
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
		return String.format("Date: %s\nUserid: %s\nRating: %d\nMessage: %s\n", date, userId, rating, message);
	}
}
