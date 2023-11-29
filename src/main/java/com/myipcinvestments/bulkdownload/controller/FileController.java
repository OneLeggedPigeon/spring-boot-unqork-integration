package com.myipcinvestments.bulkdownload.controller;

import com.myipcinvestments.bulkdownload.service.ApiService;
import com.myipcinvestments.bulkdownload.service.FileService;
import com.myipcinvestments.bulkdownload.view.ByteArrayView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayOutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

@Controller
@RequestMapping("/")
public class FileController {

    private final FileService fileService;
    private final ApiService apiService;

    @Autowired
    public FileController(FileService fileService, ApiService apiService) {
        this.fileService = fileService;
        this.apiService = apiService;
    }

    @PostMapping("/zip-pdfs")
    public ModelAndView zipPdfs(@RequestParam String[] pdfUrls) throws IOException {

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
             ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream)) {

            fileService.downloadAndZipFiles(pdfUrls, zipOutputStream);

            // Set response headers
            ModelAndView modelAndView = new ModelAndView(new ByteArrayView(
                    byteArrayOutputStream.toByteArray(),
                    "application/zip",
                    "output.zip"
            ));
            return modelAndView;
        }
    }
}
