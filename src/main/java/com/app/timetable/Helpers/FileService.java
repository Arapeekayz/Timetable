package com.app.timetable.Helpers;

import com.app.timetable.Entity.Exam;
import com.app.timetable.Entity.ExamEntity;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileService {


    public static String getPDF(File file) throws IOException {
        String x = """
                C:\\Users\\HP\\Desktop\\DRAFT 1 - MAY 2024 Timetable.pdf\
                """;
        PDDocument document = Loader.loadPDF(new File(x));

        final double res = 72; // PDF units are at 72 DPI
        PDFTableStripper stripper = new PDFTableStripper();
        stripper.setSortByPosition(true);

        // Choose a region in which to extract a table (here a 6"wide, 9" high rectangle offset 1" from top left of page)
        double width = 11.69;
        double height = 8.27;
        stripper.setRegion(new Rectangle((int) Math.round(0 * res), (int) Math.round(0 * res), (int) Math.round(width * res), (int) Math.round(height * res)));

        StringBuilder result = new StringBuilder();

        // Repeat for each page of PDF
        for (int page = 0; page < document.getNumberOfPages(); ++page) {
            PDPage pdPage = document.getPage(page);

            stripper.extractTable(pdPage);
            //stripper.setRegion(new Rectangle((int)(pdPage.getMediaBox().getLowerLeftX()*res),(int)(pdPage.getMediaBox().getUpperRightY()*res),(int)(pdPage.getMediaBox().getUpperRightX()*res),(int)(pdPage.getMediaBox().getLowerLeftY()*res)));

            for (int r = 0; r < stripper.getRows(); r++) {
                for (int c = 0; c < stripper.getColumns(); c++) {
                    result.append(stripper.getText(r, c).trim());
                }
                result.append("\n");

            }
        }


        return result.toString();

    }

    public static ArrayList<Exam> getExamListFromPDF(File file) throws IOException {
        ArrayList<Exam> exams = new ArrayList<>();

        PDDocument document = Loader.loadPDF(file);

        PDFTableStripper stripper = new PDFTableStripper();
        stripper.setSortByPosition(true);


        for (int page = 0; page < document.getNumberOfPages(); ++page) {

            PDPage pdPage = document.getPage(page);

            stripper.extractTable(pdPage);

            for (int r = 0; r < stripper.getRows(); r++) {

                Exam exam = new Exam(
                        stripper.getText(r, 0).trim(),
                        stripper.getText(r, 1).trim(),
                        stripper.getText(r, 2).trim(),
                        stripper.getText(r, 3).trim(),
                        stripper.getText(r, 4).trim(),
                        stripper.getText(r, 5).trim(),
                        stripper.getText(r, 6).trim(),
                        stripper.getText(r, 7).trim()
                );

                exams.add(exam);

            }
        }

        return exams;

    }

}
