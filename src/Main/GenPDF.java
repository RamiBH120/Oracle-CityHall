package Main;
import java.io.FileOutputStream;

import Model.Depense;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPTable;

import com.itextpdf.text.Font;

import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;

import java.io.*;
import java.awt.Desktop;

public class GenPDF {

    public static void genPdf(String titre, String sujet, String etat, String joindate, String type) throws DocumentException, IOException {
        // TODO code application logic here
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("Doleance.pdf"));
        document.open();

        Paragraph nom = new Paragraph(joindate);
        nom.setAlignment(Element.ALIGN_LEFT);
        document.add(nom);

        Image logo = Image.getInstance("C:\\Users\\Rami\\Documents\\Rami\\IdeaProjs\\FXScLog\\src\\icon\\icon.PNG");
        logo.setAlignment(Element.ALIGN_CENTER);
        document.add(logo);

        Paragraph dt = new Paragraph("Municipalité : Oued lil");
        dt.setAlignment(Element.ALIGN_CENTER);
        document.add(dt);

        document.add(new Paragraph("\n"));
        Image img1 = Image.getInstance("C:\\Users\\Rami\\Documents\\Rami\\IdeaProjs\\FXScLog\\src\\img\\logo.png");
        img1.setAlignment(Element.ALIGN_CENTER);
        document.add(img1);

        Paragraph sep = new Paragraph("________________________________________________");
        sep.setAlignment(Element.ALIGN_CENTER);
        document.add(sep);

        Font font = new Font();
        font.setSize(18);
        Paragraph title = new Paragraph(type+" : "+titre,font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Image img = Image.getInstance("C:\\Users\\Rami\\Documents\\Rami\\IdeaProjs\\FXScLog\\src\\icon\\copy.png");
        img.setAlignment(Element.ALIGN_CENTER);
        document.add(img);

        document.add(new Paragraph("\n"));

        document.add(sep);
        document.add(new Paragraph("* Sujet à traiter: "+sujet));

        document.add(sep);

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("* Etat actuel de ce document: "+etat));

        document.add(sep);

        document.close();


        try{
            File file = new File("C:\\Users\\Rami\\Documents\\Rami\\IdeaProjs\\FXScLog\\Doleance.pdf");
            if(!Desktop.isDesktopSupported()){
                System.out.println("not supported");
                return;
            }
            Desktop desktop = Desktop.getDesktop();
            if(file.exists()) desktop.open(file);
        }catch(Exception e){
            e.printStackTrace();
        }


    }

    public static void genRptPdf(ObservableList<Depense> depdt) throws DocumentException, IOException {
        // TODO code application logic here
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("RapportFinancier.pdf"));
        document.open();

        Image logo = Image.getInstance("C:\\Users\\Rami\\Documents\\Rami\\IdeaProjs\\FXScLog\\src\\icon\\icon.PNG");
        logo.setAlignment(Element.ALIGN_CENTER);
        document.add(logo);

        Paragraph nom = new Paragraph("Municipalité : Oued lil");
        nom.setAlignment(Element.ALIGN_CENTER);
        document.add(nom);

        document.add(new Paragraph("\n"));
        Image img1 = Image.getInstance("C:\\Users\\Rami\\Documents\\Rami\\IdeaProjs\\FXScLog\\src\\img\\logo.png");
        img1.setAlignment(Element.ALIGN_CENTER);
        document.add(img1);

        document.add(new Paragraph("______________________________"));

        Font font = new Font();
        font.setSize(18);
        Paragraph title = new Paragraph("Rapport Financier : ",font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Image img = Image.getInstance("C:\\Users\\Rami\\Documents\\Rami\\IdeaProjs\\FXScLog\\src\\icon\\copy.png");
        img.setAlignment(Element.ALIGN_CENTER);
        document.add(img);

        document.add(new Paragraph("\n"));

        PdfPTable table = new PdfPTable(4);

        table.addCell("ID");
        table.addCell("Raison");
        table.addCell("Montant en DT");
        table.addCell("Type de finance");
        for (Depense d : depdt){
            table.addCell(String.valueOf(d.getIddep()));
            table.addCell(String.valueOf(d.getForres()));
            table.addCell(String.valueOf(d.getMontant()));
            table.addCell(String.valueOf(d.getType()));

        }
        document.add(table);
        document.close();


        try{
            File file = new File("C:\\Users\\Rami\\Documents\\Rami\\IdeaProjs\\FXScLog\\RapportFinancier.pdf");
            if(!Desktop.isDesktopSupported()){
                System.out.println("not supported");
                return;
            }
            Desktop desktop = Desktop.getDesktop();
            if(file.exists()) desktop.open(file);
        }catch(Exception e){
            e.printStackTrace();
        }


    }

}
