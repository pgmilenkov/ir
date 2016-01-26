package ir.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import utils.Constants;

public class StreamModifier {
	private XMLEventReader eventReader;

	public StreamModifier(String filename) throws FileNotFoundException, XMLStreamException {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		eventReader = factory.createXMLEventReader(new FileReader(filename));
	}

	public XMLEventReader getReader() {
		return eventReader;
	}

	public static void main(String[] args) throws XMLStreamException, IOException, JDOMException {
		StreamModifier xmlStreamModifier = new StreamModifier(Constants.FILE_NAME_GRABO);
		// Document document = xmlStreamModifier.modify(filename);
		xmlStreamModifier.getReader().close();

		XMLOutputter xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getPrettyFormat());
		// xmlOutput.output(document, new FileOutputStream(new File(filename)));
	}

	public Document modify(String filename) throws JDOMException, IOException, XMLStreamException {
		SAXBuilder saxBuilder = new SAXBuilder();
		File file = new File(filename);
		int startIndex = 0;
		Document document = saxBuilder.build(file);
		Element rootElement = document.getRootElement().getChildren("grabo").get(0);
		List<Element> businessElements = rootElement.getChildren("business");
		Random r = new Random();
		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();
			if (event.getEventType() == XMLStreamConstants.START_ELEMENT) {
				String tagName = event.asStartElement().getName().getLocalPart();
				if (tagName.equalsIgnoreCase("business")) {
					String businessId = this.getTagNameValue("id");
					for (int i = startIndex; i < startIndex + 1; i++) {
						Element studentElement = businessElements.get(i);
						if (studentElement.getChild("id").getValue().equals(businessId)) {
							studentElement.addContent(new Element("price")
							        .setText(String.format("%.2f", r.nextDouble() * 100 + 1)));
						}
					}
					startIndex++;
				}
			}
		}
		return document;
	}

	private String getTagNameValue(String tagName) throws XMLStreamException {
		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();
			if (event.getEventType() == XMLStreamConstants.START_ELEMENT) {
				String tagFoundName = event.asStartElement().getName().getLocalPart();
				if (tagFoundName.equalsIgnoreCase(tagName)) {
					return eventReader.getElementText();
				}
			}
		}
		return "";
	}
}
