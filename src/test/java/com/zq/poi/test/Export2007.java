package com.zq.poi.test;

import com.google.gson.Gson;
import com.zq.poi.exception.ReportInternalException;
import com.zq.poi.util.ExportExcel2007;
import org.jumpmind.symmetric.csv.CsvReader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @Title: Export2007
 * @Description:
 * @Company: 盟固利
 * @author: 张奇
 * @date: ceate in 2020/5/22 9:34
 */
public class Export2007 {
    public static void main(String[] args) throws IOException, ReportInternalException {
        /**
         * 处理数据
         */
        CsvReader r = new CsvReader("E://163台车每台500条1.csv", ',', Charset.forName("GBK"));
        // 读取表头
        r.readHeaders();
        int countLength = 0;
        int flag = 0;
        List<List<Object>> ss = new ArrayList<List<Object>>();
        // 逐条读取记录，直至读完
        while (r.readRecord()) {
            flag++;
            System.out.println(flag);
            // 读取一条记录
            String message = r.get("p.originalmessage");

            String vin = null;

            List<Object> lists = new ArrayList();

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
         * 处理Excel
         */
        String sheetName = "sheet1";
        String sheetTitle = "163台车";
        List<String> columnNames = new LinkedList<String>();
        columnNames.add("vin");
        for (int i = 0; i < countLength - 1; i++) {
            columnNames.add("第" + i + "列");
        }
        ExportExcel2007 exportExcel2007 = new ExportExcel2007();
        exportExcel2007.writeExcelTitle("D:\\", "test-2007", sheetName, columnNames, sheetTitle);
        try {
            exportExcel2007.writeExcelData("D:\\", "test-2007", sheetName, ss);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ss.clear();
        exportExcel2007.dispose();
    }
}

