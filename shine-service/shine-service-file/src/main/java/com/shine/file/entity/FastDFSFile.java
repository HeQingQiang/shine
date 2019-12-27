package com.shine.file.entity;

import lombok.Data;

/***********
 *@Author: Shine
 *@Description: 封装文件上传信息
 *@Data: Created in 2019/12/27 23:32
 *@Modified By:
 *****/
@Data
public class FastDFSFile {

    //文件名字
    private String name;
    //文件内容
    private byte[] content;
    //文件扩展名
    private String ext;
    //文件MD5摘要
    private String md5;
    //文件作者
    private String author;

    public FastDFSFile(String name, byte[] content, String ext, String md5, String author) {
        this.name = name;
        this.content = content;
        this.ext = ext;
        this.md5 = md5;
        this.author = author;
    }

    public FastDFSFile(String name, byte[] content, String ext) {
        this.name = name;
        this.content = content;
        this.ext = ext;
    }
}
