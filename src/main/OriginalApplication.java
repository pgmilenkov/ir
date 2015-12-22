package main;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class OriginalApplication {
	private static StandardAnalyzer analyzerInstance;
	private static Directory indexInstance;
	
	public static void main(String[] args) throws IOException, ParseException {
        addDocuments();
        
        String queryString = args.length > 0 ? args[0] : "lucene";
        Query query = new QueryParser("title", getAnalyzer()).parse(queryString);

        int hitsPerPage = 10;
        IndexReader reader = DirectoryReader.open(getIndex());
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs docs = searcher.search(query, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;

        System.out.println("Found " + hits.length + " hits.");
        for(int i=0;i<hits.length;++i) {
            int docId = hits[i].doc;
            System.out.println("Docid: " + docId );
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("isbn") + "\t" + d.get("title"));
        }

        reader.close();
    }
	
	private static StandardAnalyzer getAnalyzer() {
		if ( analyzerInstance == null ) {
			analyzerInstance = new StandardAnalyzer();
		}
		return analyzerInstance;
	}	

	private static Directory getIndex() {
		if ( indexInstance == null ) {
			indexInstance = new RAMDirectory();
		}
		return indexInstance;
	}
	
	private static void addDocuments() throws IOException {
		IndexWriterConfig config = new IndexWriterConfig(getAnalyzer());
		IndexWriter writer = new IndexWriter(getIndex(), config);
        addDocument(writer, "Lucene in Action", "193398817");
        addDocument(writer, "Managing Gigabytes", "55063554A");
        addDocument(writer, "The Art of Computer Science", "9900333X");
        addDocument(writer, "Lucene for Dummies", "55320055Z");
        writer.close();
	}

	private static void addDocument(IndexWriter w, String title, String isbn) throws IOException {
        Document doc = new Document();
        doc.add(new TextField("title", title, Field.Store.YES));

        // use a string field for isbn because we don't want it tokenized
        doc.add(new StringField("isbn", isbn, Field.Store.YES));
        doc.add(new StringField("isbn", isbn, Field.Store.YES));
        
        w.addDocument(doc);
    }
}
