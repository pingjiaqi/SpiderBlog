package com.pjq.service;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @program:
 * @description: 发送http请求
 * @author:
 * @create:
 **/
@Service
public class HttpClientResponse {

    public String httpClientResponse(String url) throws IOException {
        String html = null;
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                //请求体内容
                html = EntityUtils.toString(response.getEntity(), "UTF-8");
            } else {
                html = "error";
            }
        } catch (ConnectTimeoutException e) {
            System.out.println(e.toString());
            html = "time out";
        } catch (HttpHostConnectException e) {
            System.out.println(e.toString());
            html = "time out";
        } finally {
            if (response != null) {
                response.close();
            }
            //相当于关闭浏览器
            httpclient.close();
        }
        return html;
    }
}
