package com.fptpolytechnic.duan1.utils;


import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class StorageService {


    public String storage(Part file) throws IOException {

        if(file == null || file.getSize() <= 0) {
            return null;
        }

        String path = AppConfig.getInstance().getProperty("app.static.image.path");

        String originalName = file.getSubmittedFileName();


        String extension = "";
        if (originalName != null && !originalName.isEmpty()) {
            int dotIndex = originalName.lastIndexOf(".");
            if (dotIndex >= 0) {
                extension = originalName.substring(dotIndex);
            }
        }

        String newName = UUID.randomUUID().toString() + extension;

        File uploadDir = new File(path);

        if(!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        file.write(path + File.separator + newName);

        return newName;
    }

    public void delete(String fileName){

        String path = AppConfig.getInstance().getProperty("app.static.image.path");
        File file = new File(path + fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
