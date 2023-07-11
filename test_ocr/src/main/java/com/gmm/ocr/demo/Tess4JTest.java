
package com.gmm.ocr.demo;

import com.recognition.software.jdeskew.ImageDeskew;
import net.sourceforge.tess4j.ITessAPI.TessPageIteratorLevel;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.ITesseract.RenderedFormat;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Word;
import net.sourceforge.tess4j.util.ImageHelper;
import net.sourceforge.tess4j.util.LoggHelper;
import net.sourceforge.tess4j.util.Utils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.junit.Assert.assertTrue;

public class Tess4JTest {

    private static final Logger logger = LoggerFactory.getLogger(new LoggHelper().toString());
    static final double MINIMUM_DESKEW_THRESHOLD = 0.05d;
    ITesseract instance;

    private final String datapath = "src/main/resources";
    private final String testResourcesDataPath = "src/main/resources/test-data";
    private final String testResourcesLanguagePath = "src/main/resources/tessdata";

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        instance = new Tesseract();
        instance.setDatapath(new File(datapath).getPath());
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of doOCR method, of class Tesseract.
     * 根据图片文件进行识别
     * @throws Exception while processing image.
     */
    @Test
    public void testDoOCR_File() throws Exception {
        logger.info("doOCR on a jpg image");
        File imageFile = new File(this.testResourcesDataPath, "ocr.png");
        //set language
        instance.setDatapath(testResourcesLanguagePath);
        instance.setLanguage("chi_sim");
        String result = instance.doOCR(imageFile);
        logger.info(result);
    }

    /**
     * Test of doOCR method, of class Tesseract.
     * 根据图片流进行识别
     * @throws Exception while processing image.
     */
    @Test
    public void testDoOCR_BufferedImage() throws Exception {
        logger.info("doOCR on a buffered image of a PNG");
        File imageFile = new File(this.testResourcesDataPath, "ocr.png");
        BufferedImage bi = ImageIO.read(imageFile);

        //set language
        instance.setDatapath(testResourcesLanguagePath);
        instance.setLanguage("chi_sim");

        String result = instance.doOCR(bi);
        logger.info(result);
    }

    /**
     * Test of getSegmentedRegions method, of class Tesseract.
     * 得到每一个划分区域的具体坐标
     * @throws java.lang.Exception
     */
    @Test
    public void testGetSegmentedRegions() throws Exception {
        logger.info("getSegmentedRegions at given TessPageIteratorLevel");
        File imageFile = new File(testResourcesDataPath, "ocr.png");
        BufferedImage bi = ImageIO.read(imageFile);
        int level = TessPageIteratorLevel.RIL_SYMBOL;
        logger.info("PageIteratorLevel: " + Utils.getConstantName(level, TessPageIteratorLevel.class));
        List<Rectangle> result = instance.getSegmentedRegions(bi, level);
        for (int i = 0; i < result.size(); i++) {
            Rectangle rect = result.get(i);
            logger.info(String.format("Box[%d]: x=%d, y=%d, w=%d, h=%d", i, rect.x, rect.y, rect.width, rect.height));
        }

        assertTrue(result.size() > 0);
    }


    /**
     * Test of doOCR method, of class Tesseract.
     * 根据定义坐标范围进行识别
     * @throws Exception while processing image.
     */
    @Test
    public void testDoOCR_File_Rectangle() throws Exception {
        logger.info("doOCR on a BMP image with bounding rectangle");
        File imageFile = new File(this.testResourcesDataPath, "xh.jpg");
        //设置语言库
        instance.setDatapath(testResourcesLanguagePath);
        instance.setLanguage("chi_sim");
        //划定区域
        // x,y是以左上角为原点，width和height是以xy为基础
        Rectangle rect = new Rectangle(100, 250, 1000, 55);
        String result = instance.doOCR(imageFile, rect);
        logger.info(result);

        rect = new Rectangle(100, 300, 500, 45);
        result = instance.doOCR(imageFile, rect);
        logger.info(result);

        rect = new Rectangle(600, 300, 600, 45);
        result = instance.doOCR(imageFile, rect);
        logger.info(result);
    }

    /**
     * Test of createDocuments method, of class Tesseract.
     * 存储结果
     * @throws java.lang.Exception
     */
    @Test
    public void testCreateDocuments() throws Exception {
        logger.info("createDocuments for png");
        File imageFile = new File(this.testResourcesDataPath, "ocr.png");
        String outputbase = "target/classes/docrenderer-2";
        List<RenderedFormat> formats = new ArrayList<RenderedFormat>(Arrays.asList(RenderedFormat.HOCR, RenderedFormat.TEXT));

        //设置语言库
        instance.setDatapath(testResourcesLanguagePath);
        instance.setLanguage("chi_sim");

        instance.createDocuments(new String[]{imageFile.getPath()}, new String[]{outputbase}, formats);
    }

    /**
     * Test of getWords method, of class Tesseract.
     * 取词方法
     * @throws java.lang.Exception
     */
    @Test
    public void testGetWords() throws Exception {
        logger.info("getWords");
        File imageFile = new File(this.testResourcesDataPath, "ocr.png");

        //设置语言库
        instance.setDatapath(testResourcesLanguagePath);
        instance.setLanguage("chi_sim");

        //按照每个字取词
        int pageIteratorLevel = TessPageIteratorLevel.RIL_SYMBOL;
        logger.info("PageIteratorLevel: " + Utils.getConstantName(pageIteratorLevel, TessPageIteratorLevel.class));
        BufferedImage bi = ImageIO.read(imageFile);
        List<Word> result = instance.getWords(bi, pageIteratorLevel);

        //print the complete result
        for (Word word : result) {
            logger.info(word.toString());
        }
    }

    /**
     * Test of Invalid memory access.
     * 处理倾斜
     * @throws Exception while processing image.
     */
    @Test
    public void testDoOCR_SkewedImage() throws Exception {
        //设置语言库
        instance.setDatapath(testResourcesLanguagePath);
        instance.setLanguage("chi_sim");

        logger.info("doOCR on a skewed PNG image");
        File imageFile = new File(this.testResourcesDataPath, "ocr_skewed.jpg");
        BufferedImage bi = ImageIO.read(imageFile);
        ImageDeskew id = new ImageDeskew(bi);
        double imageSkewAngle = id.getSkewAngle(); // determine skew angle
        if ((imageSkewAngle > MINIMUM_DESKEW_THRESHOLD || imageSkewAngle < -(MINIMUM_DESKEW_THRESHOLD))) {
            bi = ImageHelper.rotateImage(bi, -imageSkewAngle); // deskew image
        }

        String result = instance.doOCR(bi);
        logger.info(result);
    }

}
