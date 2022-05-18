import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.*;

public class highlighter {
    public void setHighlighter(Query query) {
        Formatter formatter = new SimpleHTMLFormatter();
        QueryScorer scorer = new QueryScorer(query);
        Highlighter highlighter = new Highlighter(formatter,scorer);
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer,100);
        highlighter.setTextFragmenter(fragmenter);


    }
}
