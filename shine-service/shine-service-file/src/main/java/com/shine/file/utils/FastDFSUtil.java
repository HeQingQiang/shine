package com.shine.file.utils;

import com.shine.file.entity.FastDFSFile;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/***********
 *@Author: Shine
 *@Description: FastDFS文件上传工具类
 *@Data: Created in 2019/12/27 23:40
 *@Modified By:
 *****/
public class FastDFSUtil {

    /**
     * 加载全局Tracker初始化
     */
    static{
        try {
            //查找配置文件的路径
            String fileName = new ClassPathResource("fdfs_client.conf").getPath();
            //加载Tracker链接信息
            ClientGlobal.init(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件上传
     * @param file 文件
     */
    public static String[] upload(FastDFSFile file)throws Exception{

        //附加参数
        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] = new NameValuePair("author", file.getAuthor());

        //创建一个Tracker访问的客户端对象TrackerClient
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient访问TrackerServer服务，获取链接信息
        TrackerServer trackerServer = trackerClient.getConnection();
        //通过TrackerServer的链接信息获取Storage链接信息，创建StorageClient对象存储Storage的连接信息
        StorageClient storageClient = new StorageClient(trackerServer, null);
        //通过StorageClient访问Storage，实现文件上传，并获取文件上传后的存储信息
        //参数：1上传文件的字节数组2文件的扩展名3附加参数  返回值：1文件上传storage的组名字2文件存储的文件名
        String[] results = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
        return results;
    }

}
