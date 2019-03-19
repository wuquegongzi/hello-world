package com.gmm.ocr.utils;


import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.LoadLibs;

import java.awt.image.BufferedImage;
import java.io.File;

public class OCRUtil {
    /**
     * 识别图片信息
     * @param img
     * @return
     */
    public static String getImageMessage(BufferedImage img,String language){
        String result="end";
        try{
            ITesseract instance = new Tesseract();
            File tessDataFolder = LoadLibs.extractTessResources("tessdata");
            instance.setLanguage(language);
            instance.setDatapath(tessDataFolder.getAbsolutePath());
            result = instance.doOCR(img);
            System.out.println(result);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }
}
