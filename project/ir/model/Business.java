package ir.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

//TODO: change namespace name
@XmlRootElement(namespace = "jaxb.model")
public class Business {
	private List<Offer> offers;

	public Business() {
		this(new ArrayList<>());
	}
	
	public Business(List<Offer> offers) {
		this.offers = offers;
	}
	
	@XmlElementWrapper(name = "grabo")
	@XmlElement(name = "business")
	public List<Offer> getOffers() {
		return offers;
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}
}
