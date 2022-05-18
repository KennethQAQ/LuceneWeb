import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfReader {

    public static void main(String[] args) throws IOException {
        String path = "F:\\Projects\\JavaProjects\\LuceneWeb\\src\\main\\resources\\test\\test.pdf";
        String[] doc = ReadPdf(path);
        System.out.println(doc[0]);
    }

    public static String[] ReadPdf(String Path) throws IOException {
        File pdfFile = new File(Path);
        PDDocument document = null;
        document = PDDocument.load(pdfFile);

        // 获取页码
        int pages = document.getNumberOfPages();

        // 读文本内容
        PDFTextStripper stripper = new PDFTextStripper();
        // 设置按顺序输出
        stripper.setSortByPosition(true);
        stripper.setStartPage(1);
        stripper.setEndPage(pages);
        String content = stripper.getText(document);
        // System.out.println(content);
        String regex = "ABSTRACT|Keywords|KEYWORDS|INTRODUCTION|REFERENCES";
        String[] split = content.split(regex);
        return split;
    }
    public static String ReadPdfAll(String Path) throws IOException {
        File pdfFile = new File(Path);
        PDDocument document = null;
        document = PDDocument.load(pdfFile);

        // 获取页码
        int pages = document.getNumberOfPages();

        // 读文本内容
        PDFTextStripper stripper = new PDFTextStripper();
        // 设置按顺序输出
        stripper.setSortByPosition(true);
        stripper.setStartPage(1);
        stripper.setEndPage(pages);
        String content = stripper.getText(document);
        // System.out.println(content);
        return content;
    }
}

