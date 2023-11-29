package com.myipcinvestments.bulkdownload.view;

import org.springframework.web.servlet.View;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Map;

public class ByteArrayView implements View {

    private final String contentType;
    private final String fileName;
    private final byte[] content;

    public ByteArrayView(byte[] content, String contentType, String fileName) {
        this.content = content;
        this.contentType = contentType;
        this.fileName = fileName;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(getContentType());
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        try (OutputStream out = response.getOutputStream()) {
            out.write(content);
        }
    }
}
