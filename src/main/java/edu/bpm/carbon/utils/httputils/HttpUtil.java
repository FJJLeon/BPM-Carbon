package edu.bpm.carbon.utils.httputils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
public class HttpUtil {

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数为 name1=value1&name2=value2
     * @return
     *      URL所代表远程资源的响应结果，以 JSON 形式返回
     */
    public static JSONObject httpGetJSON(String url, String param) {
        log.info("HttpGetJSON: url[{}], param[{}]", url, param);
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String respString = "";
        JSONObject result = new JSONObject();
        try {
            // 通过址默认配置创建一个httpClient实例
            httpClient = HttpClients.createDefault();
            // 创建httpGet远程连接实例
            HttpGet httpGet = new HttpGet(url + "?" + param);
            // 设置配置请求参数
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 连接主机服务超时时间
                            .setConnectionRequestTimeout(35000)// 请求超时时间
                            .setSocketTimeout(60000)// 数据读取超时时间
                            .build();
            // 为httpGet实例设置配置
            httpGet.setConfig(requestConfig);
            // 执行get请求得到返回对象
            response = httpClient.execute(httpGet);
            // 通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            // 通过EntityUtils中的toString方法将结果转换为字符串
            respString = EntityUtils.toString(entity);

            // 将字符串转换为 JSONObject 返回
            result = JSONObject.parseObject(respString);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 向指定URL发送POST方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param paramMap
     *            请求体，请求参数为 key、value 对应的 Map
     * @return
     *      URL所代表远程资源的响应结果，以 JSON 形式返回
     */
    public static JSONObject httpPostJSON(String url, Map<String, Object> paramMap) {
        log.info("httpPostJSON: url[{}], paramMap[{}]", url, paramMap.toString());
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String respString = "";
        JSONObject result = new JSONObject();

        // 创建httpClient实例
        httpClient = HttpClients.createDefault();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);

        // 配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 设置连接主机服务超时时间
                .setConnectionRequestTimeout(35000)// 设置连接请求超时时间
                .setSocketTimeout(60000)// 设置读取数据连接超时时间
                .build();
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);

        // 设置请求头
        httpPost.addHeader("Content-type","application/json; charset=utf-8");
        httpPost.setHeader("Accept", "application/json");
        // 封装post请求参数
        JSONObject jsonParam = new JSONObject();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            String k = entry.getKey();
            // 不能是 String，存在需要放 数字 类型的情况
            Object v = entry.getValue();
            jsonParam.put(k, v);
        }

        log.info(jsonParam.toString());

        try {
            // 为httpPost设置封装好的请求参数
            httpPost.setEntity(new StringEntity(jsonParam.toString(), StandardCharsets.UTF_8));
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            respString = EntityUtils.toString(entity);

            // 将字符串转换为 JSONObject 返回
            result = JSONObject.parseObject(respString);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
