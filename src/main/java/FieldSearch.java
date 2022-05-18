import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;

public class FieldSearch {
    public static void main(String[] args) throws Exception {
        // 测试
        FieldSearch f = new FieldSearch();
        f.search("computer");
    }
    // 检索方法
    public void search(String key) throws Exception {
        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(IndexBuilder.indexDir)));
        IndexSearcher is = new IndexSearcher(reader);
        Analyzer analyzer = new SimpleAnalyzer();
        QueryParser parser = new QueryParser("contents", analyzer);
        Query query = parser.parse(key);
        TopDocs docs = is.search(query, 10);
        ScoreDoc[] sdoc = docs.scoreDocs;
        Document doc;
        // highlighter
        Formatter formatter = new SimpleHTMLFormatter();
        QueryScorer scorer = new QueryScorer(query);
        Highlighter highlighter = new Highlighter(formatter,scorer);
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer,100);
        highlighter.setTextFragmenter(fragmenter);

        for (int i = 0; i < sdoc.length; i++) {
            doc = is.doc(sdoc[i].doc);
            String name = doc.get("path");
            String title= doc.get("title");
            System.out.println(name + " socre:" + sdoc[i].score);
            System.out.println(title);
        }

    }

}
