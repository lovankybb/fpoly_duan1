package com.fptpolytechnic.duan1.utils;


import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class StorageService {


    public String storage(Part file) throws IOException {

        if (file == null || file.getSize() <= 0) {
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

        // 1. Tạo thư mục D:\du_an1\images nếu chưa có
        File uploadDir = new File(path);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // 2. Định nghĩa vị trí file đích trên ổ đĩa
        File destFile = new File(uploadDir, newName);

        // 3. THAY THẾ file.write() BẰNG ĐOẠN NÀY:
        // Đọc stream dữ liệu và copy trực tiếp ra ổ đĩa D
        try (InputStream inputStream = file.getInputStream()) {
            java.nio.file.Files.copy(
                    inputStream,
                    destFile.toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );
        }

        return newName;
    }





//    public String storage(Part file) throws IOException {
//
//        if(file == null || file.getSize() <= 0) {
//            return null;
//        }
//
//        String path = AppConfig.getInstance().getProperty("app.static.image.path");
//
//        String originalName = file.getSubmittedFileName();
//
//
//        String extension = "";
//        if (originalName != null && !originalName.isEmpty()) {
//            int dotIndex = originalName.lastIndexOf(".");
//            if (dotIndex >= 0) {
//                extension = originalName.substring(dotIndex);
//            }
//        }
//
//        String newName = UUID.randomUUID().toString() + extension;
//
//        File uploadDir = new File(path);
//
//        if(!uploadDir.exists()) {
//            uploadDir.mkdirs();
//        }
//
//        file.write(path + File.separator + newName);
//
//        return newName;
//    }

    public void delete(String fileName){

        String path = AppConfig.getInstance().getProperty("app.static.image.path");
        File file = new File(path + fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
