package com.app.timetable.Helpers;

import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PDFService {
    public byte[] generatePDFFromHTML(String html) throws DocumentException, IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PDFGenerator.generatePDFFromHTML(new ByteArrayInputStream(html.getBytes()), outputStream);
        return outputStream.toByteArray();
    }
}
