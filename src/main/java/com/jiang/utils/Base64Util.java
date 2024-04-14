package com.jiang.utils;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

/**
 * @author SmilingSea
 * 2024/4/11
 */
@Component
public class Base64Util {

    public File convertBaseToJpg(String base64String ){
        // 解码base64字符串
        byte[] decodedBytes = Base64.getDecoder().decode(base64String.toString());

        // 生成随机的文件名
        String fileName = UUID.randomUUID().toString();

        // 写入文件
        File jpgFile = new File(fileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(jpgFile);
            fileOutputStream.write(decodedBytes);
            return jpgFile;
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }


    // 图片转base64
    public static String convertImageToBase64(MultipartFile multipartFile) {
        String base64String = null;
        try {
            File file = File.createTempFile(String.valueOf(IdWorker.getId()), ".png");
            multipartFile.transferTo(file);

            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputStream.read(buffer);
            inputStream.close();
            base64String = Base64.getEncoder().encodeToString(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64String;
    }
}
