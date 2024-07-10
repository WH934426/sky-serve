package com.wh.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Http工具类
 */
@Slf4j
public class HttpClientUtil {

    static final int TIMEOUT_MSEC = 5 * 1000;

    /**
     * 使用GET方法发送HTTP请求。
     *
     * @param url      请求的URL地址。
     * @param paramMap 请求参数的映射表。
     * @return 返回HTTP响应的字符串内容。
     */
    public static String doGet(String url, Map<String, Object> paramMap) {
        // 创建默认的HttpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 初始化响应结果字符串
        String result = "";
        // 声明CloseableHttpResponse实例，用于处理响应
        CloseableHttpResponse response = null;

        try {
            // 构建请求的URI，包括URL和参数
            URIBuilder builder = new URIBuilder(url);
            if (paramMap != null) {
                // 遍历参数映射表，添加参数到URI构建器
                for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                    // 检查并转换参数值为String
                    String value = entry.getValue() instanceof String ? (String) entry.getValue()
                            : String.valueOf(entry.getValue());
                    builder.addParameter(entry.getKey(), value);
                }
            }
            // 根据URI构建者构建最终的URI
            URI uri = builder.build();

            // 创建HttpGet请求对象，指定URI
            // 创建GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行HTTP请求，获取响应
            // 发送请求
            response = httpClient.execute(httpGet);

            // 检查响应状态码，仅当状态码为200时处理响应体
            // 判断响应状态
            if (response.getStatusLine().getStatusCode() == 200) {
                // 将响应实体转换为字符串，指定字符编码为UTF-8
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            // 记录异常信息
            log.error("请求异常", e);
        } finally {
            // 关闭响应和HttpClient，释放资源
            try {
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                // 记录关闭资源时的异常信息
                log.error("请求异常", e);
            }
        }

        // 返回处理后的响应结果
        return result;
    }


    /**
     * 使用POST方法向指定URL发送请求，并返回服务器的响应内容。
     *
     * @param url      请求的URL地址。
     * @param paramMap 请求参数的映射表，键值对形式。
     * @return 服务器返回的响应内容，以字符串形式。
     * @throws IOException 如果发生I/O错误。
     */
    public static String doPost(String url, Map<String, String> paramMap) throws IOException {
        // 创建默认的HttpClient实例
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString;

        try {
            // 创建HttpPost请求对象
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);

            // 如果有参数，转换为NameValuePair列表，并设置到请求对象中
            // 创建参数列表
            if (paramMap != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (Map.Entry<String, String> param : paramMap.entrySet()) {
                    paramList.add(new BasicNameValuePair(param.getKey(), param.getValue()));
                }
                // 设置请求体为表单形式的参数
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }

            // 配置请求对象
            httpPost.setConfig(builderRequestConfig());

            // 执行请求，获取响应对象
            // 执行http请求
            response = httpClient.execute(httpPost);

            // 读取并返回响应内容
            resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
        } finally {
            // 关闭响应对象，释放资源
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("请求异常", e);
            }
        }

        return resultString;
    }


    /**
     * 使用POST方法向指定URL发送带JSON参数的HTTP请求，并返回响应的字符串内容。
     *
     * @param url      请求的URL地址。
     * @param paramMap 请求的参数，以键值对形式存储，将被转换为JSON格式作为请求体。
     * @return 返回HTTP响应的字符串内容。
     * @throws IOException 如果发生I/O错误。
     */
    public static String doPost4Json(String url, Map<String, String> paramMap) throws IOException {
        // 创建默认的HttpClient实例
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString;

        try {
            // 创建HttpPost请求对象
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);

            // 如果参数不为空，构建JSON对象并设置为请求体
            if (paramMap != null) {
                // 构造json格式数据
                StringEntity entity = getStringEntity(paramMap);
                httpPost.setEntity(entity);
            }

            // 配置请求参数
            httpPost.setConfig(builderRequestConfig());

            // 执行HTTP POST请求，获取响应对象
            // 执行http请求
            response = httpClient.execute(httpPost);

            // 从响应对象中获取响应内容，并以UTF-8解码
            resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
        } finally {
            // 关闭响应对象，释放资源
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("请求异常", e);
            }
        }

        // 返回响应内容字符串
        return resultString;
    }

    /**
     * 构建请求体，将参数转换为JSON格式。
     *
     * @param paramMap 参数
     * @return StringEntity 对象，包含转换后的JSON字符串及
     */
    private static StringEntity getStringEntity(Map<String, String> paramMap) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(paramMap);
        // 设置请求体为构建的JSON字符串，编码为UTF-8
        StringEntity entity = new StringEntity(jsonObject.toString(), "utf-8");
        // 明确设置请求体的编码和类型
        // 设置请求编码
        entity.setContentEncoding("utf-8");
        // 设置数据类型
        entity.setContentType("application/json");
        return entity;
    }


    /**
     * 构建请求配置对象。
     * 此方法用于配置HTTP请求的各种超时时间。这些配置决定了客户端在等待服务器响应时的耐心程度。
     * 设置合理的超时时间可以避免应用程序在等待无效请求时无限期地阻塞。
     *
     * @return RequestConfig 配置了连接、请求和套接字超时时间的请求配置对象。
     */
    private static RequestConfig builderRequestConfig() {
        // 创建一个自定义的请求配置对象
        return RequestConfig.custom()
                // 设置连接超时时间为TIMEOUT_MSEC毫秒
                .setConnectTimeout(TIMEOUT_MSEC)
                // 设置获取连接的超时时间为TIMEOUT_MSEC毫秒
                .setConnectionRequestTimeout(TIMEOUT_MSEC)
                // 设置套接字超时时间为TIMEOUT_MSEC毫秒
                .setSocketTimeout(TIMEOUT_MSEC)
                // 构建并返回配置完毕的请求配置对象
                .build();
    }
}

