package com.app.timetable.Helpers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PDFGenerator {
    public static void generatePDFFromHTML(InputStream inputStream, OutputStream outputStream) throws DocumentException, IOException {

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.setPageSize(PageSize.A4.rotate());
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer,document,inputStream);
        document.close();

    }
}
