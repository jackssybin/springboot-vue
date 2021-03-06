package com.weiziplus.springboot.util;

import com.weiziplus.springboot.config.GlobalConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author wanglongwei
 * @date 2019/6/20 15:45
 */
@Slf4j
@Component
public class FileUtils {

    /**
     * /pc/   后台文件上传
     *
     * @param file
     * @param mkdir
     * @return
     */
    public static String upFilePc(MultipartFile file, String mkdir) {
        return upFile("/pc", file, mkdir);
    }

    /**
     * /pc/   后台文件上传
     *
     * @param file
     * @return
     */
    public static String upFilePc(MultipartFile file) {
        return upFile("/pc", file, null);
    }

    /**
     * /api/   web文件上传
     *
     * @param file
     * @param mkdir
     * @return
     */
    public static String upFileApi(MultipartFile file, String mkdir) {
        return upFile("/api", file, mkdir);
    }

    /**
     * /api/   web文件上传
     *
     * @param file
     * @return
     */
    public static String upFileApi(MultipartFile file) {
        return upFile("/api", file, null);
    }

    /**
     * 文件上传
     *
     * @param file
     * @param mkdir 如果分文件夹存放，传入文件夹
     * @return 成功返回路径，失败返回null
     */
    private static String upFile(String type, MultipartFile file, String mkdir) {
        if (null == file || file.isEmpty()) {
            return null;
        }
        //此处配合nginx访问/pc/反向代理java后台
        String resultPath = type + "/";
        if (ToolUtils.notBlank(mkdir)) {
            resultPath += mkdir + "/";
        }
        // 获取原始名字
        String fileName = file.getOriginalFilename();
        // 如果原始名字为null获取当前名字
        if (ToolUtils.isBlank(fileName)) {
            fileName = file.getName();
        }
        //  获取文件后缀类型
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 生成新文件名
        fileName = ToolUtils.createUUID() + suffixName;
        resultPath = resultPath + fileName;
        File dest = new File(GlobalConfig.getBaseFilePath() + resultPath);
        if (!dest.getParentFile().exists()) {
            boolean flag = dest.getParentFile().mkdirs();
            if (!flag) {
                return null;
            }
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            log.warn("文件上传失败" + e);
            return null;
        }
        return resultPath;
    }

    /**
     * 如果返回null代表文件不是图片
     *
     * @param file
     * @return
     */
    public static BufferedImage getImage(MultipartFile file) {
        if (null == file) {
            return null;
        }
        try {
            return ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            log.warn("上传的文件不是图片,详情:" + e);
            return null;
        }
    }

}
