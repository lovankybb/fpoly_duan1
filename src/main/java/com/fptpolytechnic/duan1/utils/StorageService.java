package com.fptpolytechnic.duan1.utils;


import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class StorageService {


    public String storage(Part file) throws IOException {
        String path = AppConfig.getInstance().getProperty("app.static.image.path");

        String originalName = file.getSubmittedFileName();

        System.out.println("Info: Storing " + originalName);

        String extension = originalName.substring(originalName.lastIndexOf("."));

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
