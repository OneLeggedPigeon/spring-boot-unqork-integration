package com.myipcinvestments.bulkdownload.service;

import org.apache.pdfbox.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileService {

    public void downloadAndZipFiles(String[] pdfUrls, ZipOutputStream zipOutputStream) throws IOException {
        for (String pdfUrl : pdfUrls) {
            try (InputStream inputStream = new URL(pdfUrl).openStream();) {

                // Create a new entry for the ZIP file
                ZipEntry zipEntry = new ZipEntry(pdfUrl.substring(pdfUrl.lastIndexOf('/') + 1));
                zipOutputStream.putNextEntry(zipEntry);

                IOUtils.copy(inputStream, zipOutputStream);

                zipOutputStream.closeEntry();
            }
        }
        zipOutputStream.close();
    }
}