package ir.xml;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import ir.model.Business;

public class JAXBReader {
	public static Business readGraboXmlFile(String filename) throws JAXBException, FileNotFoundException {
		JAXBContext context = JAXBContext.newInstance(Business.class);
	    Unmarshaller um = context.createUnmarshaller();
	    Business graboFile = (Business) um.unmarshal(new FileReader(filename));
	    return graboFile;
	}
}
