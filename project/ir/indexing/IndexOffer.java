package ir.indexing;

import java.io.IOException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import ir.model.Offer;
import ir.xml.StreamReader;
import utils.Constants;

public class IndexOffer {
	private StandardAnalyzer analyzerInstance;
	private Directory indexInstance;
	private IndexWriterConfig configInstance;
	private IndexWriter indexWriter;

	public IndexOffer() throws IOException {
		createInstances();
	}

	private void createInstances() throws IOException {
		analyzerInstance = new StandardAnalyzer();
		indexInstance = new RAMDirectory();
		configInstance = new IndexWriterConfig(analyzerInstance);
		indexWriter = new IndexWriter(indexInstance, configInstance);
	}

	private IndexWriter getWriter() {
		return indexWriter;
	}

	private void addOffer(Offer offer) throws IOException {
		if (true)
			return;
		// Document doc = new Document();
		// doc.add(new TextField("Name", offer.getName(), Field.Store.YES));
		// doc.add(new TextField("Category", offer.getCategory().getName(),
		// Field.Store.YES));
		// for (String city : offer.getCity()) {
		// doc.add(new TextField("City", city, Field.Store.YES));
		// }
		// double sum = 0;
		// for (Review review : offer.getReview()) {
		// sum += review.getRating();
		// doc.add(new TextField("Review", review.getMessage(),
		// Field.Store.YES));
		// }
		// sum = sum > 0 ? sum / offer.getReview().size() : 0;
		// doc.add(new DoubleField("Rating", sum, Field.Store.YES));
		// getWriter().addDocument(doc);
	}

	public static void main(String[] args)
	        throws IOException, XMLStreamException, FactoryConfigurationError {

		IndexOffer indexOffer = new IndexOffer();
		StreamReader reader = new StreamReader(Constants.FILE_NAME_GRABO);
		Offer nextOffer;
		long startTime = System.currentTimeMillis();
		while ((nextOffer = reader.getNextOffer()) != null) {
			indexOffer.addOffer(nextOffer);
		}
		long endTime = System.currentTimeMillis();
		System.out.println(endTime - startTime);
		indexOffer.getWriter().close();
	}
}
