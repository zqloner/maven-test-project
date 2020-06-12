package com.zq.poi.test;

import com.google.gson.Gson;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jumpmind.symmetric.csv.CsvReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: Test
 * @Description:
 * @Company: 盟固利
 * @author: 张奇
 * @date: ceate in 2020/5/21 9:15
 */
public class Export2003 {
    public static void main(String[] args) throws Exception {
        CsvReader r = new CsvReader("E://163台车每台500条1.csv", ',', Charset.forName("GBK"));
        // 读取表头
        r.readHeaders();
        int countLength = 0;
        int flag = 0;
        List<List<String>> ss = new ArrayList<List<String>>();
        // 逐条读取记录，直至读完
        List<String> arrayList;
        while (r.readRecord()) {
            flag++;
            if (flag==1000) {
                break;
            }
            System.out.println(flag);
            // 读取一条记录
            String message = r.get("p.originalmessage");
            String vin = null;
            List lists = new ArrayList();
            Gson gson = new Gson();
            Map<String, Object> map = new HashMap<String, Object>();//初始数据集合
            map = gson.fromJson(message, map.getClass());
            List<Map<String, Object>> mapListJson = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
            if (map.get("items") != null && !"".equals(map.get("items")) && map.get("vin") != null && !"".equals(map.get("vin"))) {
                vin = (String) map.get("vin");
                mapListJson = (List<Map<String, Object>>) map.get("items");
            }
            for (Map<String, Object> map1 : mapListJson) {
                if (map1.get("code").equals("1000001")) {
                    maps = (List<Map<String, Object>>) map1.get("value");
                }
            }
            if (maps != null && !"".equals(maps)) {
                for (Map<String, Object> map2 : maps) {
                    lists = (List<Object>) map2.get("1008007");
                    lists.add(0,vin);
                }
            }
            countLength = lists.size();
            if (lists.size() <= 0) {
                lists.add("再发空的，弄死主机厂。。。。。。。。。。。。。。。。。");
            }
            ss.add(lists);
        }
        /**
         * 测试2003版本
         */
//        OutputStream outputStream = new FileOutputStream("D:\\test-2003.xls");
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet("sheet1");
//        HSSFCellStyle style = workbook.createCellStyle();
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        HSSFRow row0 = sheet.createRow(0);
//        for (int i = 0; i < countLength - 1; i++) {
//            if (i == 0) {
//                row0.createCell(0).setCellValue("vin");
//            } else {
//                row0.createCell(i).setCellValue("第" + i + "列");
//            }
//        }
////        最高65535行
//        if (ss.size() <= 65000) {
//            for (int m = 1; m < ss.size(); m++) {
//                HSSFRow row = sheet.createRow(m);
//                for (int n = 0; n < ss.get(m - 1).size(); n++) {
//                    row.createCell(n).setCellValue(String.valueOf(ss.get(m - 1).get(n)));
//                }
//            }
//        } else {
//            for (int m = 1; m < 65000; m++) {
//                HSSFRow row = sheet.createRow(m);
//                for (int n = 0; n < ss.get(m - 1).size(); n++) {
//                    row.createCell(n).setCellValue(String.valueOf(ss.get(m - 1).get(n)));
//                }
//            }
//            HSSFSheet sheet2 = workbook.createSheet("sheet2");
//            HSSFRow row1 = sheet2.createRow(0);
//            for (int i = 0; i < countLength - 1; i++) {
//                if (i == 0) {
//                    row1.createCell(0).setCellValue("vin");
//                } else {
//                    row1.createCell(i).setCellValue("第" + i + "列");
//                }
//            }
//            for (int m = 1; m < ss.size() - 64999 + 1; m++) {
//                row1 = sheet2.createRow(m);
//                for (int n = 0; n < ss.get(64999 + m - 1).size(); n++) {
//                    row1.createCell(n).setCellValue(String.valueOf(ss.get(64999 + m - 1).get(n)));
//                }
//            }
//        }
//        workbook.write(outputStream);

//    测试2007导出
        OutputStream outputStream = new FileOutputStream("D:\\test-2007.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheet1");
        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        XSSFRow row0 = sheet.createRow(0);
        for (int i = 0; i < countLength - 1; i++) {
            if (i == 0) {
                row0.createCell(0).setCellValue("vin");
            } else {
                row0.createCell(i).setCellValue("第" + i + "列");
            }
        }
//        最高65535行
        if (ss.size() <= 65000) {
            for (int m = 1; m < ss.size(); m++) {
                XSSFRow row = sheet.createRow(m);
                for (int n = 0; n < ss.get(m - 1).size(); n++) {
                    row.createCell(n).setCellValue(String.valueOf(ss.get(m - 1).get(n)));
                }
            }
        } else {
            for (int m = 1; m < 65000; m++) {
                XSSFRow row = sheet.createRow(m);
                for (int n = 0; n < ss.get(m - 1).size(); n++) {
                    row.createCell(n).setCellValue(String.valueOf(ss.get(m - 1).get(n)));
                }
            }
            XSSFSheet sheet2 = workbook.createSheet("sheet2");
            XSSFRow row1 = sheet2.createRow(0);
            for (int i = 0; i < countLength - 1; i++) {
                if (i == 0) {
                    row1.createCell(0).setCellValue("vin");
                } else {
                    row1.createCell(i).setCellValue("第" + i + "列");
                }
            }
            for (int m = 1; m < ss.size() - 64999 + 1; m++) {
                row1 = sheet2.createRow(m);
                for (int n = 0; n < ss.get(64999 + m - 1).size(); n++) {
                    row1.createCell(n).setCellValue(String.valueOf(ss.get(64999 + m - 1).get(n)));
                }
            }
        }
        workbook.write(outputStream);
    }
}


