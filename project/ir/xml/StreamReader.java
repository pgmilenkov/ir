package ir.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import ir.model.Categories;
import ir.model.Offer;
import ir.model.Review;

public class StreamReader {
	private XMLStreamReader reader;
	private String filename;

	public StreamReader(String fileName)
	        throws FileNotFoundException, XMLStreamException, FactoryConfigurationError {
		filename = fileName;
		reader = createReaderFromFilename();
	}

	private XMLStreamReader createReaderFromFilename()
	        throws FileNotFoundException, XMLStreamException, FactoryConfigurationError {
		return XMLInputFactory.newInstance().createXMLStreamReader(filename,
		        new FileInputStream(filename));
	}

	public Offer getNextOffer() throws XMLStreamException {
		Offer offer = null;
		while (reader.hasNext() && offer == null) {
			if (reader.next() == XMLStreamReader.START_ELEMENT) {
				offer = getNextOfferByStartElement(offer);
			}
		}
		return offer;
	}

	private Offer getNextOfferByStartElement(Offer offer) throws XMLStreamException {
		if (reader.getLocalName().equals("grabo"))
			offer = getNextOffer();
		else if (reader.getLocalName().equals("business"))
			offer = readBusiness();
		return offer;
	}

	private Offer readBusiness() throws XMLStreamException {
		Offer offer = new Offer();

		while (reader.hasNext()) {
			int next = reader.next();
			if (next == XMLStreamReader.START_ELEMENT) {
				String elementName = reader.getLocalName();
				if (elementName.equals("id"))
					offer.setId(readInt());
				else if (elementName.equals("name"))
					offer.setName(readCharacters());
				else if (elementName.equals("category"))
					offer.setCategory(readCategory());
				else if (elementName.equals("price"))
					offer.setPrice(readDouble());
				else if (elementName.equals("cities"))
					offer.setCity(readCities());
				else if (elementName.equals("reviews"))
					offer.setReview(readReviews());
				else System.out.println(reader.toString());
			} else if (next == XMLStreamReader.END_ELEMENT) {
				return offer;
			}
		}
		throw new XMLStreamException("Premature end of file");
	}

	public List<Offer> getAllOffers() throws XMLStreamException {
		List<Offer> offers = new ArrayList<>();
		while (reader.hasNext()) {
			if (reader.next() == XMLStreamReader.START_ELEMENT) {
				String elementName = reader.getLocalName();
				if (elementName.equals("grabo"))
					getAllOffers();
				else if (elementName.equals("business"))
					offers.add(readBusiness());
			}
		}
		return offers;
	}

	private String readCharacters() throws XMLStreamException {
		StringBuilder result = new StringBuilder();
		while (reader.hasNext()) {
			int eventType = reader.next();
			if (eventType == XMLStreamReader.CHARACTERS || eventType == XMLStreamReader.CDATA) {
				result.append(reader.getText());
			} else if (eventType == XMLStreamReader.END_ELEMENT) {
				return result.toString();
			}
		}
		throw new XMLStreamException("Premature end of file");
	}

	private int readInt() throws XMLStreamException {
		String characters = readCharacters();
		try {
			return Integer.valueOf(characters);
		} catch (NumberFormatException e) {
			throw new XMLStreamException("Invalid integer " + characters);
		}
	}

	private double readDouble() throws XMLStreamException {
		String characters = readCharacters();
		try {
			return Double.valueOf(characters);
		} catch (NumberFormatException e) {
			throw new XMLStreamException("Invalid double " + characters);
		}
	}

	private Categories readCategory() throws XMLStreamException {
		String characters = readCharacters();
		if (characters.equals(""))
			return null;
		try {
			return Categories.getEnum(characters);
		} catch (IllegalArgumentException e) {
			throw new XMLStreamException("Invalid category " + characters);
		}
	}

	private List<String> readCities() throws XMLStreamException {
		List<String> cities = new ArrayList<>();
		while (reader.hasNext()) {
			int next = reader.next();
			if (next == XMLStreamReader.START_ELEMENT) {
				String elementName = reader.getLocalName();
				if (elementName.equals("city"))
					cities.add(readCharacters());
			} else if (next == XMLStreamReader.END_ELEMENT) {
				return cities;
			}
		}
		throw new XMLStreamException("Premature end of file");
	}

	private List<Review> readReviews() throws XMLStreamException {
		List<Review> reviews = new ArrayList<>();
		while (reader.hasNext()) {
			int eventType = reader.next();
			if (eventType == XMLStreamReader.START_ELEMENT) {
				String elementName = reader.getLocalName();
				if (elementName.equals("review"))
					reviews.add(readReview());
			} else if (eventType == XMLStreamReader.END_ELEMENT) {
				return reviews;
			}
		}
		throw new XMLStreamException("Premature end of file");
	}

	private Review readReview() throws XMLStreamException {
		Review r = new Review();
		while (reader.hasNext()) {
			int eventType = reader.next();
			if (eventType == XMLStreamReader.START_ELEMENT) {
				String elementName = reader.getLocalName();
				if (elementName.equals("date"))
					r.setDate(null);
				else if (elementName.equals("user_id"))
					r.setUserId(readInt());
				else if (elementName.equals("rating"))
					r.setRating(readInt());
				else if (elementName.equals("message"))
					r.setMessage(readCharacters());
			} else if (eventType == XMLStreamReader.END_ELEMENT) {
				return r;
			}
		}
		throw new XMLStreamException("Premature end of file");
	}
}
