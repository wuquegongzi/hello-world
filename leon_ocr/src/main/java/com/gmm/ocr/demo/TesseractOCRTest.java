package com.gmm.ocr.demo;

import java.io.IOException;

public class TesseractOCRTest {

    public static void main(String[] args) {
        try {
            Process  pro = Runtime.getRuntime()
                    .exec(new String[]{"D:\\Program Files (x86)\\Tesseract-OCR\\tesseract.exe",
                            "C:\\Users\\chenminglei.GM-MEDICARE\\Downloads\\000.jpg",
                            "C:\\Users\\chenminglei.GM-MEDICARE\\Downloads\\result"});
            pro.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
