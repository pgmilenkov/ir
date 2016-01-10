import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import ir.model.Business;
import ir.model.xml.XMLBusinessReader;
import utils.Constants;

public class Test {
	public static void main(String[] args) throws FileNotFoundException, JAXBException {
		Business businessObject = XMLBusinessReader.readGraboXmlFile(Constants.FILE_NAME);
		System.out.println(businessObject.getOffers().get(0));
	}
}
