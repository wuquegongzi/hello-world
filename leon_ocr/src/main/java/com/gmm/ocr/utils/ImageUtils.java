package com.gmm.ocr.utils;

import sun.misc.BASE64Encoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class ImageUtils {

    private static File file = null;

    /**
     * 读取图像的二进制流
     *
     * @param infile
     * @return
     */
    public static FileInputStream getByteImage(String infile) {
        FileInputStream inputImage = null;
        file = new File(infile);
        try {
            inputImage = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return inputImage;
    }

    /**
     * 输出图片
     *
     * @param inputStream
     * @param path
     */
    public static void readBlob(FileInputStream inputStream, String path) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(path);
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buf)) != -1) {
                fileOutputStream.write(buf, 0, len);// 写
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void readImg(InputStream inputStream, String path) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buf)) != -1) {
                fileOutputStream.write(buf, 0, len);// 写
            }
            inputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void downLoadImg(String imgPath,String savePath){
        BufferedReader reader = null;
        try {
            URL url = new URL(imgPath);
            URLConnection conn = url.openConnection();
            ImageUtils.readImg(conn.getInputStream(), savePath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e2) {
                }
            }
        }
    }

    /**
     * @Description: 根据图片地址转换为base64编码字符串
     * @Author:
     * @CreateTime:
     * @return
     */
    public static String getImageStr(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    public static void main(String[] args) throws IOException {

        String filePath = "D:\\360MoveData\\Users\\chenminglei.GM-MEDICARE\\Desktop\\pic\\2.jpg";
        byte[] imgData = FileUtil.readFileByBytes(filePath);
        BASE64Encoder encoder = new BASE64Encoder();
        String imgStr = encoder.encode(imgData);

        Map<String, Object> input = new HashMap<String, Object>();
        input.put("image", imgStr);//与image二者选一
        input.put("threshold", 50);

        String result = HttpUtils.doPostByJson("http://192.168.150.123:8083/img_censor_quality",GsonUtils.GsonString(input));

        System.out.println(result);
    }

}
