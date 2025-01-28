package com.app.timetable.Helpers;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MultiPartFileToFileConverter {

    public static File convert(MultipartFile file) throws IOException {
        File mFile=new File(System.getProperty("user.dir") + "/Uploads" + File.separator + "New file.pdf");
        if (mFile.createNewFile()) {
            System.out.println("File created: " + mFile.getName());
        } else {
            System.out.println("File already exists.");
        }

        Path path = Paths.get(System.getProperty("user.dir") + "/Uploads" + File.separator + "New file.pdf");

        try (OutputStream os = Files.newOutputStream(path)) {
            os.write(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new File(path.toString());
    }
}
