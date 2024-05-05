package com.wh.controller.admin;

import com.wh.constant.MessageConstant;
import com.wh.result.Result;
import com.wh.utils.AliOssUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 通用接口
 */
@RestController
@RequestMapping("/admin/common")
@Slf4j
public class CommonController {

    @Resource
    private AliOssUtil aliOssUtil;

    /**
     * 上传文件
     * @param file 文件
     * @return 文件上传成功的路径 / 文件上传失败的提示信息
     */
    @PostMapping("/upload")
    public Result<String> uploadFile(MultipartFile file) {
        log.info("上传文件: {}", file);

        try {
            // 获取原始文件名
            String originalFilename = file.getOriginalFilename();
            // 获取文件后缀名
            String extension = null;
            if (originalFilename != null) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            // 生成一个随机UUID作为文件名，并添加后缀名
            String objectName = UUID.randomUUID().toString() + extension;
            // 上传文件到OSS
            String filePath = aliOssUtil.uploadFile(file.getBytes(), objectName);
            // 返回文件路径
            return Result.success(filePath);
        } catch (IOException e) {
            log.error("上传文件失败", e);
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
