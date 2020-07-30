package com.zq;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.thread.ThreadUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.zq.fastjson.FastJsonUtils;
import com.zq.ftp.MyFTPUtils;
import com.zq.httpclient.HttpClientUtil;
import com.zq.pdf.PDFUtil;
import com.zq.pdf.newPdfUtil.MyHeaderFooter;
import com.zq.pdf.newPdfUtil.PdfReport;
import com.zq.pdf.newPdfUtil.Watermark;
import com.zq.sftp.MySFTPUtils;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @Title: MyTest
 * @Description:
 * @Company: 盟固利
 * @author: 张奇
 * @date: ceate in 2020/5/22 17:40
 */
public class MyTest {
    public static void main(String[] args) throws Exception {
//        mySftpTest();
//        myFtpTest();
//        myHttpClientTest();
        myFastJsonUtilsTest();
    }

    private static void myFastJsonUtilsTest() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        System.out.println(FastJsonUtils.list2Json(arrayList));
    }

    private static void myHttpClientTest() {
        String url = "http://api.itink.com.cn/api/vehicle/getCanBusByCarId.json";
        Map<String, String> params = new HashMap();
        params.put("token", "2b37d26a9d4446d48a0a87a0f6852355");
        params.put("carId", "LVCB4L4D6KM002891");
        params.put("queryDate", "2020-06-01");
        System.out.println(HttpClientUtil.doGet(url, params));
        ;
    }

    private static void mySftpTest() {
        MySFTPUtils mySFTPUtils = new MySFTPUtils();
        mySFTPUtils.connect("192.168.0.110", "root", "1qazXSW@", "22");
        try {
            mySFTPUtils.upload(new FileInputStream(new File("D:\\server110\\beiyouCsvFile\\aaa.csv")), "/home/data/aaa.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void myFtpTest() {
        MyFTPUtils ftp = new MyFTPUtils();
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