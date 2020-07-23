package com.zq.csv;


import org.jumpmind.symmetric.csv.CsvReader;
import org.jumpmind.symmetric.csv.CsvWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: zhangqi
 * @CreateTime: 2020/7/317:37
 * @Company: MGL
 */
public class ReadCSVUtil {
    public static char separator = ',';

    public static void main(String[] args) throws Exception {
        List<String[]> strings = readCSV("D://csv//LVCB3L4D0GM001990-20200706.csv");
        System.out.println(strings);
    }

    /**
     * 读取CSV文件
     * @param filePath:全路径名
     */
    public static List<String[]> readCSV(String filePath) throws Exception {
        CsvReader reader = null;
        List<String[]> dataList = new ArrayList<String[]>();
        try {
            //如果生产文件乱码，windows下用gbk，linux用UTF-8
            reader = new CsvReader(filePath, separator, Charset.forName("UTF-8"));

            // 读取表头
            reader.readHeaders();
            String[] headArray = reader.getHeaders();//获取标题
            System.out.println(headArray[0] + headArray[1] + headArray[2]);

            // 逐条读取记录，直至读完
            while (reader.readRecord()) {
                // 读一整行
                System.out.println(reader.getRawRecord());
                // 读这行的第一列
                System.out.println(reader.get(0));
                // 读这行的第二列
                System.out.println(reader.get(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                reader.close();
            }
        }

        return dataList;
    }

    /**
     * 生成CSV文件
     * @param dataList:数据集
     * @param filePath:全路径名
     */
    public static boolean createCSV(List<String[]> dataList, String filePath) throws Exception {
        boolean isSuccess = false;
        CsvWriter writer = null;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath, true);
            //如果生产文件乱码，windows下用gbk，linux用UTF-8
            writer = new CsvWriter(out, separator, Charset.forName("GBK"));
            for (String[] strs : dataList) {
                writer.writeRecord(strs);
            }
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return isSuccess;
    }
}
