package com.shine.file.controller;

import com.shine.common.ServerResponse;
import com.shine.file.entity.FastDFSFile;
import com.shine.file.utils.FastDFSUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/***********
 *@Author: Shine
 *@Description: 文件上传
 *@Data: Created in 2019/12/28 0:13
 *@Modified By:
 *****/
@RestController
@RequestMapping(value = "/file")
@CrossOrigin
public class FileController {

    @Value("${FastDFS.urlPri}")
    public String urlPri;

    @PostMapping("/upload")
    public ServerResponse upload(@RequestParam(value = "file")MultipartFile file) throws Exception {

        FastDFSFile fastDFSFile = new FastDFSFile(
                file.getOriginalFilename(),
                file.getBytes(),
                StringUtils.getFilenameExtension(file.getOriginalFilename())
        );
        //调用FastDFSUtil工具类将文件传入FastDFS中
        String[] result = FastDFSUtil.upload(fastDFSFile);
        //拼接访问地址
        //查找配置文件的路径
        String url = urlPri + result[0] + "/" + result[1];

        return ServerResponse.success("文件上传成功",url);
    }

}
