package com.shine.common;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * FTP工具类.
 *
 * @author shine
 * @Date 2019/5/28 16:08
 */
@Data
@Slf4j
public class FTPUtil {

    private static final String ftpIp = "47.103.124.88";
    private static final Integer port_ = 21;
    private static final String ftpUser = "shine";
    private static final String ftpPass = "Shine_2019";

    private String ip;
    private Integer port;
    private String user;
    private String password;
    private FTPClient ftpClient;

    public FTPUtil(String ip, Integer port, String user, String password) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public static boolean uploadFile(List<File> files) throws IOException {
        FTPUtil ftpUtil = new FTPUtil(ftpIp, port_, ftpUser, ftpPass);
        log.info("\n开始连接FTP服务器");
        boolean ServerResponse = ftpUtil.uploadFile("file",files);
        log.info("\n结束上传，上传结果{}",ServerResponse);
        return ServerResponse;
    }

    /**
     * 上传文件.
     *
     * @param remotePath 远程地址
     * @param files 文件
     * @return boolean 结果
     * @throws IOException IO异常
     */
    private boolean uploadFile(String remotePath, List<File> files) throws IOException {
        boolean upload = false;
        FileInputStream fis = null;
        //连接FTP服务器
        if (connectServer(ip, user, password)) {
            try {
                //切换目录
                ftpClient.changeWorkingDirectory(remotePath);
                //设置缓冲区
                ftpClient.setControlEncoding("UTF-8");
                //设置文件类型为二进制文件类型(可防止乱码)
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                //打开本地的自动模式
                ftpClient.enterLocalPassiveMode();
                for (File file : files) {
                    fis = new FileInputStream(file);
                    //上传文件
                    ftpClient.storeFile(file.getName(), fis);
                }
                upload = true;
            } catch (IOException e) {
                upload = false;
                log.error("上传文件异常", e);
            } finally {
                //释放连接
                fis.close();
                ftpClient.disconnect();
            }
            return upload;
        }else{
            throw new IllegalArgumentException("FTP用户名密码有误");
        }

    }

    /**
     * 连接FTP服务器.
     *
     * @param ip ftp地址
     * @param user ftp用户
     * @param password 密码
     * @return boolean 结果.
     */
    private boolean connectServer(String ip, String user, String password) {
        boolean isSuccess = false;
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(ip);
            isSuccess = ftpClient.login(user, password);

        } catch (IOException e) {
            log.error("连接FTP服务器异常", e);
            throw new RuntimeException("连接FTP服务器异常");
        }
        return isSuccess;
    }

}
