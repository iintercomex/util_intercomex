package util;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Cristiana
 */
public class PdfParser {
    private String enderecoRecurso;

    public PdfParser(String diretorioPdf) {
        this.enderecoRecurso = diretorioPdf;
    }
    public PdfParser() {
    }
    public void setEnderecoRecurso(String enderecoRecurso) {
        this.enderecoRecurso = enderecoRecurso;
    }
    public String getConteudo() {
        File f = new File(this.enderecoRecurso);
        FileInputStream is = null;
        try{
            is = new FileInputStream(f);
        }
        catch(IOException e){
            System.out.println("ERRO: " + e.getMessage());
            return null;
        }
        PDDocument pdfDocument = null;
        try{
            PDFParser parser = new PDFParser(is);

            parser.parse();
            pdfDocument = parser.getPDDocument();
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(pdfDocument);
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            if (pdfDocument != null){
                try{
                    pdfDocument.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }
        return null;
    }
}
