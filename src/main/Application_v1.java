package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Application_v1 {
	IndexWriter indexWriter;
	IndexReader indexReader;
	IndexSearcher indexSearcher;
	StandardAnalyzer analyzer;
	FieldType type = new FieldType();

	public static void main(String[] args) throws IOException, ParseException {

		Application_v1 experiment = new Application_v1();
		experiment.init();

		// 2. query
		String queryString = args.length > 0 ? args[0] : "lucene";
		Query q = experiment.getQuery(queryString, "title");

		// 3. search
		ScoreDoc[] hits = experiment.search(q);

		// 4. display results
		System.out.println("Found " + hits.length + " hits.");
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			experiment.printDoc(docId);
		}

		for (int i = 0; i < hits.length; i++) {
			System.out.println(experiment.getTFIDF(hits[i].doc, "lucene", "field"));
		}

		experiment.end();

	}

	public void init() throws IOException {
//		type.setIndexed(true);
		type.setStoreTermVectors(true);
		type.setStored(true);
		type.setOmitNorms(true);
		type.setIndexOptions(IndexOptions.DOCS_AND_FREQS);
		type.freeze();

		File iFile = new File("index");
		if (iFile.exists()) {
			iFile.delete();
		}
		analyzer = new StandardAnalyzer();
		Directory index = FSDirectory.open(iFile.toPath());
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		try {
			indexWriter = new IndexWriter(index, config);
			addDocuments();
			indexReader = DirectoryReader.open(index);
			indexSearcher = new IndexSearcher(indexReader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void end() throws IOException {
		indexReader.close();
	}

	private void addDocuments() throws IOException {
		addDoc("Lucene in Action", "193398817");
		addDoc("Lucene for Dummies", "55320055Z");
		addDoc("Managing Gigabytes", "55063554A");
		addDoc("The Art of Computer Science", "9900333X");
		indexWriter.close();
	}

	private void addDoc(String title, String isbn) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("title", title, Field.Store.YES));
		doc.add(new TextField("content", "", Field.Store.YES));

		Field f = new Field("field", title, type);
		// use a string field for isbn because we don't want it tokenized
		doc.add(f);
		doc.add(new StringField("isbn", isbn, Field.Store.YES));
		indexWriter.addDocument(doc);
	}

	public Query getQuery(String string, String field) throws ParseException {
		return new QueryParser(field, analyzer).parse(string);
	}

	public ScoreDoc[] search(Query q) throws IOException {
		int hitsPerPage = 10;
		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
		indexSearcher.search(q, collector);
		return collector.topDocs().scoreDocs;
	}

	public void printDoc(int docId) throws IOException {
		Document d = indexSearcher.doc(docId);
		System.out.println(docId + " " + d.get("isbn") + "\t" + d.get("title"));
	}

	public double getTFIDF(int doc, String term, String field) throws IOException {
		return TFIDFTools.getTFIDF(term, doc, field, indexReader, indexSearcher);
	}

}
