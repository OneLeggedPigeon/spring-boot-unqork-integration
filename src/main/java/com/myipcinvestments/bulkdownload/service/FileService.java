package com.myipcinvestments.bulkdownload.service;

import com.myipcinvestments.bulkdownload.dto.AccessTokenDto;
import org.apache.pdfbox.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileService {

    private final WebClient webClient;

    @Autowired
    public FileService(WebClient webClient) {
        this.webClient = webClient;
    }

    private byte[] getFileBytes(String fileUrl, AccessTokenDto accessTokenDto) throws IOException {
        try {
            // Modify the file URL based on your file endpoint logic
            ResponseEntity<byte[]> responseEntity = webClient.get()
                    .uri(fileUrl)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenDto.getAccessToken())
                    .retrieve()
                    .toEntity(byte[].class)
                    .block();

            if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
                // Access the Content-Disposition header from the response
                List<String> contentDispositionHeader = responseEntity.getHeaders().get(HttpHeaders.CONTENT_DISPOSITION);

                if (contentDispositionHeader != null && !contentDispositionHeader.isEmpty()) {
                    // Parse the filename from the Content-Disposition header
                    String filename = extractFilenameFromContentDisposition(contentDispositionHeader.get(0));
                    // Log or use the filename as needed
                    System.out.println("Filename: " + filename);
                }

                // Return the byte array
                return responseEntity.getBody();
            } else {
                throw new IOException("Error fetching file. Non-successful response.");
            }
        } catch (Exception e) {
            // Handle exceptions or log appropriately
            throw new IOException("Error fetching file: " + e.getMessage(), e);
        }
    }

    private String extractFilenameFromContentDisposition(String contentDisposition) {
        String[] parts = contentDisposition.split(";");
        for (String part : parts) {
            part = part.trim();
            if (part.startsWith("filename=")) {
                String filename = part.substring("filename=".length()).trim();

                // Remove surrounding quotation marks if present
                if (filename.startsWith("\"") && filename.endsWith("\"")) {
                    filename = filename.substring(1, filename.length() - 1);
                }

                return filename;
            }
        }
        return null;
    }


    private String extractFilenameFromContentDispositionHeader(String fileUrl, AccessTokenDto accessTokenDto) throws IOException {
        try {
            ResponseEntity<byte[]> responseEntity = webClient.get()
                    .uri(fileUrl)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenDto.getAccessToken())
                    .retrieve()
                    .toEntity(byte[].class)
                    .block();

            if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
                // Access the Content-Disposition header from the response
                List<String> contentDispositionHeader = responseEntity.getHeaders().get(HttpHeaders.CONTENT_DISPOSITION);

                if (contentDispositionHeader != null && !contentDispositionHeader.isEmpty()) {
                    // Parse the filename from the Content-Disposition header
                    return extractFilenameFromContentDisposition(contentDispositionHeader.get(0));
                }
            }
        } catch (Exception e) {
            throw new IOException("Error fetching file: " + e.getMessage(), e);
        }

        // Default to using the last part of the URL if Content-Disposition header is not available
        return fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
    }

    public void downloadAndZipFiles(String[] urls, ZipOutputStream zipOutputStream, AccessTokenDto accessTokenDto) throws IOException {
        for (String url : urls) {
            try (InputStream inputStream = new ByteArrayInputStream(getFileBytes(url, accessTokenDto))) {
                // Extract the filename from Content-Disposition header
                String filename = extractFilenameFromContentDispositionHeader(url, accessTokenDto);

                // Create a new entry for the ZIP file
                ZipEntry zipEntry = new ZipEntry(filename);
                zipOutputStream.putNextEntry(zipEntry);

                IOUtils.copy(inputStream, zipOutputStream);

                zipOutputStream.closeEntry();
            }
        }
        zipOutputStream.close();
    }
}