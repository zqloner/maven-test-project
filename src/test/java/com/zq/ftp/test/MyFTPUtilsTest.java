package com.zq.ftp.test;

import com.zq.ftp.util.MyFTPUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Title: MyFTPUtilsTest
 * @Description:
 * @Company: 盟固利
 * @author: 张奇
 * @date: ceate in 2020/5/24 13:57
 * 参考地址:https://blog.csdn.net/Huozhiwu_11/article/details/90700053
 */
public class MyFTPUtilsTest {
    public static void main(String[] args) {
        MyFTPUtils ftp =new MyFTPUtils();
        //ftp.uploadFile("ftpFile/data", "123.docx", "E://123.docx");
        //ftp.downloadFile("ftpFile/data", "123.docx", "F://");
        //ftp.deleteFile("ftpFile/data", "123.docx");
        //System.out.println("ok");

//        亲测:
//                如果上传目录只有一级,会重复创建目录。
        Date date = new Date();
        String str = "yyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(str);
//        StringBuilder projectName = new StringBuilder("bjunion/");
//        StringBuilder fileName = projectName.append(sdf.format(date));
        ftp.uploadFile(sdf.format(date), "1.jpg", "D:\\图片或者视频\\李嘉欣\\1.jpg");



//        ftp.downloadFile("ftpFile/data", "aaa.txt", "D://");
//        下载自动创建目录,根据上一个方法改造
//        ftp.downloadAutpMkdir("ftpFile/data", "极速登陆器1.zip", "D://upload_download//哈哈哈//");
//        ftp.deleteFile("ftpFile/data", "aaa.txt");
    }
}
