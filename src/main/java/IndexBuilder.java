import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;

public class IndexBuilder {
    public static String indexDir="indexdir";
    public static String fileDir="src/main/resources/pdf/www2016";
    public static void main(String[] args) throws Exception {
        IndexBuilder builder = new IndexBuilder();
        //builder.filedIndex();
        builder.commonIndex();
    }
    public void filedIndex() throws Exception{
        Analyzer textAnalyzer=new SimpleAnalyzer();
        IndexWriterConfig conf = new IndexWriterConfig(textAnalyzer);
        conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        Directory dir = FSDirectory.open(Paths.get(indexDir));
        IndexWriter writer=new IndexWriter(dir,conf);
        File fdir = new File(fileDir);
        File flist[]=fdir.listFiles();

        for(File file : flist){
            Document doc=new Document();
            System.out.println(file.getName());
            String[] pdfContents = PdfReader.ReadPdf(file.getPath());
            String contents = PdfReader.ReadPdfAll(file.getPath());
            TextField all = new TextField("all",contents,Field.Store.YES);
            Field fileName = new StringField("fileName", file.getName(),Field.Store.YES);
            Field pathField = new StringField("path", file.getPath(),Field.Store.YES);
            TextField title = new TextField("title",pdfContents[0],Field.Store.YES);
            TextField author = new TextField("author",pdfContents[0],Field.Store.YES);
            TextField abstract1 = new TextField("abstract",pdfContents[1],Field.Store.YES);
            if(pdfContents.length==5){
                TextField keyWord = new TextField("keyWord",pdfContents[2],Field.Store.YES);
                TextField content = new TextField("contents",pdfContents[3],Field.Store.YES);
                TextField references = new TextField("references",pdfContents[4],Field.Store.YES);
                doc.add(keyWord);
                doc.add(content);
                doc.add(references);
            }
            if(pdfContents.length==4){
                TextField keyWord = new TextField("keyWord","",Field.Store.YES);
                TextField content = new TextField("contents",pdfContents[2],Field.Store.YES);
                TextField references = new TextField("references",pdfContents[3],Field.Store.YES);
                doc.add(keyWord);
                doc.add(content);
                doc.add(references);
            }
            doc.add(all);
            doc.add(fileName);
            doc.add(pathField);
            doc.add(title);
            doc.add(title);
            doc.add(author);
            doc.add(abstract1);
            //doc.add(new TextField("contents", new FileReader(file)));
            //writer.addDocument(doc);
			System.out.println(file.getName());
        }
        System.out.println("build index success");
        writer.close();
    }
    public void commonIndex() throws Exception{
        Analyzer textAnalyzer=new SimpleAnalyzer();
        IndexWriterConfig conf = new IndexWriterConfig(textAnalyzer);
        conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        Directory dir = FSDirectory.open(Paths.get(indexDir));
        IndexWriter writer=new IndexWriter(dir,conf);
        File fdir = new File(fileDir);
        File flist[]=fdir.listFiles();

        for(File file : flist){
            Document doc=new Document();
            System.out.println(file.getName());
            Field fileName = new StringField("fileName", file.getName(),Field.Store.YES);
            Field pathField = new StringField("path", file.getPath(),Field.Store.YES);
            String contents = PdfReader.ReadPdfAll(file.getPath());
            String[] pdfContents = PdfReader.ReadPdf(file.getPath());
            TextField title = new TextField("title",pdfContents[0],Field.Store.YES);
            TextField author = new TextField("author",pdfContents[0],Field.Store.YES);
            TextField content = new TextField("contents",contents,Field.Store.YES);
            doc.add(content);
            doc.add(title);
            doc.add(author);
            doc.add(fileName);
            doc.add(pathField);
            //doc.add(title);
            //doc.add(new TextField("contents", new FileReader(file)));
            writer.addDocument(doc);
            System.out.println(file.getName());
        }
        System.out.println("build index success");
        writer.close();
    }
}
