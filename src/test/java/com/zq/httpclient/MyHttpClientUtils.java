package com.zq.httpclient;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Title: MyHttpClientUtils
 * @Description:
 * @Company: 盟固利
 * @author: 张奇
 * @date: ceate in 2020/6/2 15:56
 */
public class MyHttpClientUtils {
    /**
     * 请求参数乱码
     * 设置请求的编码格式：
     *          obj.addHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
     *
     *
     * 响应HTTP/1.1 403 Forbidden
     *
     *      原因：网站设置了反爬虫机制，禁止非法访问。
     *
     * 解决方案：伪装浏览器。
     *          obj.addHeader("User-Agent"," Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537. 36 (KHTML, like Gecko) Chrome/31.0.1650.63")
     *
     */

    /**
     * 执行GET请求
     */
    public static String  doGet(String url) {

        // 创建Httpclient对象

        CloseableHttpClient httpclient = HttpClients.createDefault();


        // 创建http GET请求

        HttpGet httpGet = new HttpGet(url);


        CloseableHttpResponse response = null;

        try {

            // 执行请求

            response = httpclient.execute(httpGet);

            System.out.println(response.getStatusLine());

            // 判断返回状态是否为200

            if (response.getStatusLine().getStatusCode() == 200) {

                String content = EntityUtils.toString(response.getEntity(), "UTF-8");

//                System.out.println("内容长度：" + content.length());
                return content;

            }

        } catch (Exception e) {

            e.printStackTrace();


        } finally {

            if (response != null) {

                try {

                    response.close();

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }
            try {
                httpclient.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }
        return null;
    }


    /**
     * 执行GET带参数
     */
    public static String doGetParam(String url, Map<String,Object> params) {

        // 创建Httpclient对象

        CloseableHttpClient httpclient = HttpClients.createDefault();

        CloseableHttpResponse response = null;
        String newUrl = "";
        try {
            StringBuilder builder = new StringBuilder(url);
            if (params != null && params.size() > 0) {
                builder.append("?");
            }
            params.forEach((a, b) -> {
                builder.append(a.toString());
                builder.append("=");
                builder.append(b.toString());
                builder.append("&");
            });
            String substring = builder.substring(0, builder.lastIndexOf("&"));
            HttpGet httpGet = new HttpGet(substring);
            // 执行请求
            response = httpclient.execute(httpGet);

            // 判断返回状态是否为200

            if (response.getStatusLine().getStatusCode() == 200) {

                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                return content;
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {

            if (response != null) {

                try {

                    response.close();

                } catch (IOException e) {

                    // TODO Auto-generated catch block

                    e.printStackTrace();
                }

            }

            try {

                httpclient.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }

        }
        return null;

    }

    /**
     * 执行post请求
     */
    public static void doPost() {

        // 创建Httpclient对象

        CloseableHttpClient httpclient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();


        // 创建http POST请求

        HttpPost httpPost = new HttpPost("http://www.oschina.net/");


        CloseableHttpResponse response = null;

        try {

            // 执行请求

            response = httpclient.execute(httpPost);

            System.out.println(response.getStatusLine());

            // 判断返回状态是否为200

            if (response.getStatusLine().getStatusCode() == 200) {

                String content = EntityUtils.toString(response.getEntity(), "UTF-8");

                System.out.println(content);

            }

        } catch (Exception e) {

            e.printStackTrace();


        } finally {

            if (response != null) {

                try {

                    response.close();

                } catch (IOException e) {

                    // TODO Auto-generated catch block

                    e.printStackTrace();

                }

            }

            try {

                httpclient.close();

            } catch (IOException e) {

                // TODO Auto-generated catch block

                e.printStackTrace();

            }

        }

    }


    /**
     * 执行post带参数
     *
     * @throws Exception
     */
    public static void doPostParam() throws Exception {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        // 创建http POST请求

        HttpPost httpPost = new HttpPost("http://www.oschina.net/search");

        // 设置2个post参数，一个是scope、一个是q

        List<NameValuePair> parameters = new ArrayList<NameValuePair>();

        parameters.add(new BasicNameValuePair("scope", "project"));

        parameters.add(new BasicNameValuePair("q", "java"));

        // 构造一个form表单式的实体

        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);

        // 将请求实体设置到httpPost对象中

        httpPost.setEntity(formEntity);
        CloseableHttpResponse response = null;

        try {
            // 执行请求
            response = httpclient.execute(httpPost);

            System.out.println(response.getStatusLine());

            // 判断返回状态是否为200

            if (response.getStatusLine().getStatusCode() == 200) {

                String content = EntityUtils.toString(response.getEntity(), "UTF-8");

                System.out.println(content);
            }

        } finally {

            if (response != null) {

                response.close();
            }

            httpclient.close();
        }
    }
}
