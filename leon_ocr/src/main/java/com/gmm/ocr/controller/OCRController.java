package com.gmm.ocr.controller;

import com.gmm.ocr.common.base.AjaxResult;
import com.gmm.ocr.demo.ImgCensorResult;
import com.gmm.ocr.utils.GsonUtils;
import com.gmm.ocr.utils.ImageUtils;
import com.gmm.ocr.utils.StringUtils;
import com.gmm.ocr.utils.Tess4JUtils;
import io.swagger.annotations.*;
import org.bytedeco.javacpp.opencv_core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.bytedeco.javacpp.opencv_core.CV_64F;
import static org.bytedeco.javacpp.opencv_core.meanStdDev;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import static org.bytedeco.javacpp.opencv_imgproc.*;

@Api(tags = "OCR接口" ,description="检测图片清晰度，OCR识别等")
@Controller
public class OCRController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(OCRController.class);

    @Value("${temp.path}")
    public String tempPath;


    @ApiOperation(value = "根据图片文件进行识别", notes = "根据图片文件进行识别")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "image", value = "图像数据，MultipartFile流读取，不能与imgUrl并存,若存在则忽略imgUrl", required = false, dataType = "String"),
            @ApiImplicitParam(name = "imgUrl", value = "图像Url，不能与image并存，不需要urlEncode", required = false, dataType = "String"),
            @ApiImplicitParam(name = "language", value = "语言包，默认chi_sim  可选：eng", required = false, dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "返回识别结果"),
            @ApiResponse(code = -1, message = "异常，识别失败")
    })
    @PostMapping("/doOCR_File")
    @ResponseBody
    public AjaxResult doOCR_File(@RequestParam(name="image",required = false) MultipartFile image,
                                           @RequestParam(name="imgUrl",required = false) String imgUrl,
                                 @RequestParam(name="language",required = false,defaultValue = "chi_sim") String language
    )
    {

        if((image == null || image.isEmpty()) && StringUtils.isEmpty(imgUrl)){
            return error("图片参数不可为空");
        }

        // 生成文件名
        String tempFileName = new SimpleDateFormat("yyyyMMddHHmmssSSS")
                .format(new Date())
                + (new Random().nextInt(9000) % (9000 - 1000 + 1) + 1000);

        // 生成文件路径
        String filename = tempPath + tempFileName;

        if(image == null || image.isEmpty()){
            try{
                String prefix="."+imgUrl.split("？")[0].substring(imgUrl.split("？")[0].lastIndexOf(".") + 1).toLowerCase();
                filename = filename.concat(".").concat(prefix);
                ImageUtils.downLoadImg(imgUrl,filename);
            }catch (Exception e){
                e.printStackTrace();
                return error("图片获取失败");
            }
        }else {
            // 获取文件名
            String imageOriginalFilename = image.getOriginalFilename();
            // 获取文件后缀
            String prefix = imageOriginalFilename.substring(imageOriginalFilename.lastIndexOf("."));
            filename = filename.concat(prefix);
            try {
                image.transferTo(new File(filename));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File file = new File(filename);
        if(!file.exists()){
            return error("图片获取异常");
        }
        String worlds = "";
        try {
            worlds = Tess4JUtils.doOCR_File(filename,language);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1,e.getMessage());
        }finally {
            file.delete();
        }

        return success(worlds);
    }

    @ApiOperation(value = "根据定义坐标范围进行OCR识别", notes = "根据定义坐标范围进行识别")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "image", value = "图像数据，MultipartFile流读取，不能与imgUrl并存,若存在则忽略imgUrl", required = false, dataType = "String"),
            @ApiImplicitParam(name = "imgUrl", value = "图像Url，不能与image并存，不需要urlEncode", required = false, dataType = "String"),
            @ApiImplicitParam(name = "x", value = "x the specified X coordinate", required = false, dataType = "int"),
            @ApiImplicitParam(name = "y", value = "y the specified Y coordinate", required = false, dataType = "int"),
            @ApiImplicitParam(name = "width", value = "the width of the <code>Rectangle</code>", required = false, dataType = "int"),
            @ApiImplicitParam(name = "height", value = "the height of the <code>Rectangle</code>", required = false, dataType = "int"),
            @ApiImplicitParam(name = "language", value = "语言包，默认chi_sim  可选：eng", required = false, dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "返回识别结果"),
            @ApiResponse(code = -1, message = "异常，识别失败")
    })
    @PostMapping("/doOCR_File_Rectangle")
    @ResponseBody
    public AjaxResult DoOCR_File_Rectangle(@RequestParam(name="image",required = false) MultipartFile image,
                                         @RequestParam(name="imgUrl",required = false) String imgUrl,
                                         @RequestParam(name="x",required = true,defaultValue = "0") int x,
                                         @RequestParam(name="y",required = true,defaultValue = "0") int y,
                                         @RequestParam(name="width",required = true,defaultValue = "0") int width,
                                         @RequestParam(name="height",required = true,defaultValue = "0") int height,
                                         @RequestParam(name="language",required = false,defaultValue = "chi_sim") String language
    )
    {

        if((image == null || image.isEmpty()) && StringUtils.isEmpty(imgUrl)){
            return error("图片参数不可为空");
        }

        // 生成文件名
        String tempFileName = new SimpleDateFormat("yyyyMMddHHmmssSSS")
                .format(new Date())
                + (new Random().nextInt(9000) % (9000 - 1000 + 1) + 1000);

        // 生成文件路径
        String filename = tempPath + tempFileName;

        if(image == null || image.isEmpty()){
            try{
                String prefix="."+imgUrl.split("？")[0].substring(imgUrl.split("？")[0].lastIndexOf(".") + 1).toLowerCase();
                filename = filename.concat(".").concat(prefix);
                ImageUtils.downLoadImg(imgUrl,filename);
            }catch (Exception e){
                e.printStackTrace();
                return error("图片获取失败");
            }
        }else {
            // 获取文件名
            String imageOriginalFilename = image.getOriginalFilename();
            // 获取文件后缀
            String prefix = imageOriginalFilename.substring(imageOriginalFilename.lastIndexOf("."));
            filename = filename.concat(prefix);
            try {
                image.transferTo(new File(filename));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File file = new File(filename);
        if(!file.exists()){
            return error("图片获取异常");
        }
        String worlds = "";
        try {
            worlds = Tess4JUtils.doOCR_File_Rectangle(filename,x,y,width,height,language);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1,e.getMessage());
        }finally {
            file.delete();
        }

        return success(worlds);
    }

    @ApiOperation(value = "图片检测", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "image", value = "图像数据，MultipartFile流读取，不能与imgUrl并存,若存在则忽略imgUrl", required = false, dataType = "String"),
            @ApiImplicitParam(name = "imgUrl", value = "图像Url，不能与image并存，不需要urlEncode", required = false, dataType = "String"),
            @ApiImplicitParam(name = "threshold", value = "根据矩阵的标准差。" +
                    "阈值太低会导致正常图片被误断为模糊图片，阈值太高会导致模糊图片被误判为正常图片", required = false, dataType = "double"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "返回检测结果"),
            @ApiResponse(code = -1, message = "异常，校验失败")
    })
    @PostMapping("/img_censor_quality")
    @ResponseBody
    public AjaxResult img_censor_quality(@RequestParam(name="image",required = false) MultipartFile image,
                                            @RequestParam(name="imgUrl",required = false) String imgUrl,
                                            @RequestParam(name="threshold",required = false,defaultValue = "70") Double threshold
    )
    {
        if((image == null || image.isEmpty()) && StringUtils.isEmpty(imgUrl)){
            return error("图片参数不可为空");
        }

        // 生成文件名
        String tempFileName = new SimpleDateFormat("yyyyMMddHHmmssSSS")
                .format(new Date())
                + (new Random().nextInt(9000) % (9000 - 1000 + 1) + 1000);

        // 生成文件路径
        String filename = tempPath + tempFileName;

        if(image == null || image.isEmpty()){
            try{
                String prefix="."+imgUrl.split("？")[0].substring(imgUrl.split("？")[0].lastIndexOf(".") + 1).toLowerCase();
                filename = filename.concat(".").concat(prefix);
                ImageUtils.downLoadImg(imgUrl,filename);
            }catch (Exception e){
                e.printStackTrace();
                return error("图片获取失败");
            }
        }else {
            // 获取文件名
            String imageOriginalFilename = image.getOriginalFilename();
            // 获取文件后缀
            String prefix = imageOriginalFilename.substring(imageOriginalFilename.lastIndexOf("."));
            filename = filename.concat(prefix);
            try {
                image.transferTo(new File(filename));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File file = new File(filename);
        if(!file.exists()){
            return error("图片获取异常");
        }

        opencv_core.Mat srcImage = null;
        if (!file.isDirectory())        //若非目录(即文件)，则打印
            srcImage = imread(file.getAbsolutePath());
        opencv_core.Mat dstImage = new opencv_core.Mat();
        cvtColor(srcImage, dstImage, COLOR_BGR2GRAY);        //转化为灰度图
        //在gray目录下生成灰度图片
        imwrite(tempPath+"//gray//gray-"+file.getName(), dstImage);

        opencv_core.Mat laplacianDstImage = new opencv_core.Mat();
        //阈值太低会导致正常图片被误断为模糊图片，阈值太高会导致模糊图片被误判为正常图片
        Laplacian(dstImage, laplacianDstImage, CV_64F);
        //在laplacian目录下升成经过拉普拉斯掩模做卷积运算的图片
        imwrite(tempPath+"//laplacian//laplacian-"+file.getName(), laplacianDstImage);

        //矩阵标准差
        opencv_core.Mat stddev = new opencv_core.Mat();

        //求矩阵的均值与标准差
        meanStdDev(laplacianDstImage, new opencv_core.Mat(), stddev);
        //double norm = Core.norm(laplacianDstImage); // ((全部元素的平方)的和)的平方根
        //System.out.println("\n矩阵的均值：\n" + mean.dump());
        System.out.println(file.getName() + "矩阵的标准差：\n" + stddev.createIndexer().getDouble()+"\n");
        // System.out.println(f.getName()+"平方根：\n" + norm);

        String msg = "";
        try {
            msg = GsonUtils.GsonString(new ImgCensorResult(threshold,stddev.createIndexer().getDouble(),stddev.createIndexer().getDouble() > threshold ? "图片质量校验不通过":"校验通过"));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            file.delete();
        }
        return success(msg);
    }

    @ApiIgnore
    @GetMapping("/")
    @ResponseBody
    public String index()
    {
        return "hello world 0_O~";
    }




    @ApiIgnore
    @ApiOperation(value = "图片检测", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "image", value = "图像数据，base64编码，不能与imgUrl并存,若存在则忽略imgUrl", required = false, dataType = "String"),
            @ApiImplicitParam(name = "imgUrl", value = "图像Url，不能与image并存，不需要urlEncode", required = false, dataType = "String"),
            @ApiImplicitParam(name = "threshold", value = "根据矩阵的标准差。" +
                    "阈值太低会导致正常图片被误断为模糊图片，阈值太高会导致模糊图片被误判为正常图片", required = false, dataType = "double"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "返回检测结果"),
            @ApiResponse(code = -1, message = "异常，校验失败")
    })
    @RequestMapping("/img_censor_qualityBAK")
    @ResponseBody
    public AjaxResult img_censor_qualityBAK(@RequestParam(name="image",required = false) String image,
                                 @RequestParam(name="imgUrl",required = false) String imgUrl,
                                 @RequestParam(name="threshold",required = false,defaultValue = "70") Double threshold
                             )
    {
        if(StringUtils.isEmpty(image) && StringUtils.isEmpty(imgUrl)){
            return error("图片参数不可为空");
        }

        // 生成文件名
        String files = new SimpleDateFormat("yyyyMMddHHmmssSSS")
                .format(new Date())
                + (new Random().nextInt(9000) % (9000 - 1000 + 1) + 1000)
                + ".png";
        // 生成文件路径
        String filename = tempPath + files;

        if(StringUtils.isEmpty(image)){
            try{
                ImageUtils.downLoadImg(imgUrl,filename);
            }catch (Exception e){
                e.printStackTrace();
                return error("图片获取失败");
            }
        }else{
            String dataPrix = "";
            String data = "";

            String [] d = image.split("base64,");
            if(d != null && d.length == 2){
                dataPrix = d[0];
                data = d[1];
            }else{
                return error("数据不合法");
            }

            String suffix = "";
            if("data:image/jpeg;".equalsIgnoreCase(dataPrix)){//data:image/jpeg;base64,base64编码的jpeg图片数据
                suffix = ".jpg";
//        } else if("data:image/x-icon;".equalsIgnoreCase(dataPrix)){//data:image/x-icon;base64,base64编码的icon图片数据
//            suffix = ".ico";
//        } else if("data:image/gif;".equalsIgnoreCase(dataPrix)){//data:image/gif;base64,base64编码的gif图片数据
//            suffix = ".gif";
            } else if("data:image/png;".equalsIgnoreCase(dataPrix)){//data:image/png;base64,base64编码的png图片数据
                suffix = ".png";
            }else{
                return error("上传图片格式不合法");
            }

            BASE64Decoder decoder = new BASE64Decoder();
            // Base64解码
            byte[] imageByte = null;
            try {
                imageByte = decoder.decodeBuffer(data);
                for (int i = 0; i < imageByte.length; ++i) {
                    if (imageByte[i] < 0) {// 调整异常数据
                        imageByte[i] += 256;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            OutputStream imageStream = null;
            try {
                // 生成文件
                File imageFile = new File(filename);
                imageFile.createNewFile();
                if(!imageFile.exists()){
                    imageFile.createNewFile();
                }
                imageStream = new FileOutputStream(imageFile);
                imageStream.write(imageByte);

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(imageStream != null){
                    try {
                        imageStream.flush();
                        imageStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        File file = new File(filename);
        if(!file.exists()){
            return error("图片获取异常");
        }

        opencv_core.Mat srcImage = null;
        if (!file.isDirectory())        //若非目录(即文件)，则打印
            srcImage = imread(file.getAbsolutePath());
        opencv_core.Mat dstImage = new opencv_core.Mat();
        cvtColor(srcImage, dstImage, COLOR_BGR2GRAY);        //转化为灰度图
        //在gray目录下生成灰度图片
        imwrite(tempPath+"//gray//gray-"+file.getName(), dstImage);

        opencv_core.Mat laplacianDstImage = new opencv_core.Mat();
        //阈值太低会导致正常图片被误断为模糊图片，阈值太高会导致模糊图片被误判为正常图片
        Laplacian(dstImage, laplacianDstImage, CV_64F);
        //在laplacian目录下升成经过拉普拉斯掩模做卷积运算的图片
        imwrite(tempPath+"//laplacian//laplacian-"+file.getName(), laplacianDstImage);

        //矩阵标准差
        opencv_core.Mat stddev = new opencv_core.Mat();

        //求矩阵的均值与标准差
        meanStdDev(laplacianDstImage, new opencv_core.Mat(), stddev);
        //double norm = Core.norm(laplacianDstImage); // ((全部元素的平方)的和)的平方根
        //System.out.println("\n矩阵的均值：\n" + mean.dump());
        System.out.println(file.getName() + "矩阵的标准差：\n" + stddev.createIndexer().getDouble()+"\n");
        // System.out.println(f.getName()+"平方根：\n" + norm);

        /*Map map = new HashMap();
        map.put("threshold", threshold);
        map.put("deviation", stddev.createIndexer().getDouble());
        map.put("conclusion", stddev.createIndexer().getDouble() > threshold ? "图片质量校验不通过":"校验通过");*/

        String msg = "";
        try {
            msg = GsonUtils.GsonString(new ImgCensorResult(threshold,stddev.createIndexer().getDouble(),stddev.createIndexer().getDouble() > threshold ? "图片质量校验不通过":"校验通过"));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            file.delete();
        }
        return success(msg);
    }

}
