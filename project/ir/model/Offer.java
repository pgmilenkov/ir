package ir.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "business")
@XmlType(propOrder = { "id", "name", "category", "city", "review" })
public class Offer {
	private long id;
	private String name;
	private Categories category;
	private double price;
	private List<String> citiesList;
	private List<Review> reviewList;

	public Offer(long _id, String _name, Categories c, List<String> cit, List<Review> r) {
		id = _id;
		name = _name;
		category = c;
		citiesList = new ArrayList<>(cit);
		reviewList = new ArrayList<>(r);
	}

	public Offer() {
		this(0, "a", null, new ArrayList<String>(), new ArrayList<Review>());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Categories getCategory() {
		return category;
	}

	public void setCategory(Categories category) {
		this.category = category;
	}

	public long getId() {
		return id;
	}

	public void setId(long _id) {
		id = _id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double p) {
		price = p > 0 ? p : 0;
	}

	@XmlElementWrapper(name = "cities")
	@XmlElement(name = "city")
	public List<String> getCity() {
		return citiesList;
	}

	public void setCity(List<String> cities) {
		this.citiesList = new ArrayList<>(cities);
	}

	@XmlElementWrapper(name = "reviews")
	@XmlElement(name = "review")
	public List<Review> getReview() {
		return reviewList;
	}

	public void setReview(List<Review> reviews) {
		this.reviewList = new ArrayList<>(reviews);
	}

	@Override
	public String toString() {
		return String.format("%d %s %s %.2f %s %s", id, name, category, price, citiesList,
		        reviewList);
	}
}
