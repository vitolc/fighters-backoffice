package com.vitulc.fightersapi.app.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Service
public class UploadImageService {

    @Value("${upload.path}")
    private String uploadPath;

    public String uploadImage(MultipartFile image){

        String filePath = null;

        if(!image.isEmpty()){
            String fileName = image.getOriginalFilename();

            try {
                File directory = new File(uploadPath);

                if (!directory.exists()){
                    directory.mkdirs();
                }

                File serverFile = new File(directory.getAbsolutePath() + File.separator + fileName);

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));

                stream.write(image.getBytes());
                stream.close();

                filePath = serverFile.getAbsolutePath();

            }  catch (Exception e){
                e.printStackTrace();
            }
        } return filePath;
    }
}
