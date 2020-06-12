package com.zq;

import com.zq.httpclient.MyHttpClientUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title: MyTest
 * @Description:
 * @Company: 盟固利
 * @author: 张奇
 * @date: ceate in 2020/5/22 17:40
 */
public class MyTest {
    public static void main(String[] args) {
        ;
        String url = "http://api.itink.com.cn/api/vehicle/getCanBusByCarId.json";
        Map<String, Object> params = new HashMap();
        params.put("token", "2b37d26a9d4446d48a0a87a0f6852355");
        params.put("carId", "LVCB4L4D6KM002891");
        params.put("queryDate", "2020-06-01");
        System.out.println(MyHttpClientUtils.doGetParam(url, params));;
    }
}