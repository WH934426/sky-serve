package com.wh.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import com.wh.properties.WeChatProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

/**
 * 微信支付工具类
 */
@Component
@Slf4j
public class WeChatPayUtil {

    // 微信支付下单接口地址
    public static final String JSAPI = "https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi";
    // 申请退款接口地址
    public static final String REFUNDS = "https://api.mch.weixin.qq.com/v3/refund/domestic/refunds";

    @Resource
    private WeChatProperties weChatProperties;
    private CloseableHttpClient httpClient;

    /**
     * 获取用于与微信支付接口通信的CloseableHttpClient实例。
     * 该方法通过加载商户私钥和微信支付证书来配置HttpClient，以确保通信的安全性。
     *
     * @return CloseableHttpClient 用于与微信支付接口通信的HTTP客户端。
     */
    private CloseableHttpClient getClient() {
        try {
            // 加载商户私钥，用于签名和验证。
            PrivateKey merchantPrivateKey = null;
            merchantPrivateKey = PemUtil.loadPrivateKey(new FileInputStream((new File(weChatProperties.getPrivateKeyFilePath()))));

            // 加载微信支付证书，用于验证微信支付的响应。
            X509Certificate x509Certificate = PemUtil.loadCertificate(new FileInputStream((new File(weChatProperties.getWeChatPayCertFilePath()))));

            // 创建包含微信支付证书的列表。
            List<X509Certificate> weChatPayCertificateList = Collections.singletonList(x509Certificate);

            // 使用Builder模式配置并创建WechatPayHttpClient实例。
            WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                    .withMerchant(weChatProperties.getMchid(), weChatProperties.getMchSerialNo(), merchantPrivateKey)
                    .withWechatPay(weChatPayCertificateList);

            return builder.build();
        } catch (FileNotFoundException e) {
            // 记录文件未找到的错误。
            log.error("获取微信支付客户端工具对象失败", e);
            return null;
        }
    }

    /**
     * 发送HTTP请求并获取响应。
     *
     * @param request 需要发送的HTTP请求对象，包含了请求的URL、方法类型等信息。
     * @return 返回HTTP响应的主体内容，以字符串形式表示。
     * @throws Exception 如果发送请求或处理响应过程中出现错误，则抛出异常。
     */
    private String sendRequest(HttpUriRequest request) throws Exception {
        // 检查httpClient是否为null，如果是，则抛出异常。
        if (httpClient == null) {
            throw new IllegalArgumentException("httpClient must not be null");
        }
        // 执行请求，并获取响应。
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            // 将响应内容转换为字符串，并返回。
            return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        }
    }

    /**
     * 使用HTTP POST方法向指定URL发送请求。
     *
     * @param url  请求的URL地址。
     * @param body 请求的主体内容，通常为JSON格式的数据。
     * @return 返回服务器响应的内容。
     * @throws Exception 如果请求过程中发生错误，则抛出异常。
     */
    private String post(String url, String body) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
        httpPost.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
        addCommonHeaders(httpPost); // 新增方法调用，用于添加共通的请求头
        return sendRequest(httpPost);
    }


    /**
     * 发起HTTP GET请求。
     *
     * @param url 请求的URL地址，指定资源的位置。
     * @return 返回HTTP请求的响应内容。
     * @throws Exception 如果请求过程中发生错误，抛出异常。
     */
    private String get(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        addCommonHeaders(httpGet);
        return sendRequest(httpGet);
    }

    /**
     * 添加所有请求共通的请求头
     *
     * @param request Http请求
     */
    private void addCommonHeaders(HttpRequestBase request) {
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString());
        request.setHeader("Wechatpay-Serial", weChatProperties.getMchSerialNo());
    }

    /**
     * 生成微信支付JSAPI支付的请求参数。
     * 该方法用于构造微信支付API调用的请求参数，以便发起JSAPI类型的支付请求。
     *
     * @param orderNum    商户订单号，用于唯一标识一笔交易。
     * @param total       订单总金额，单位为分。
     * @param description 订单描述，用于展示在支付页面上。
     * @param openid      用户的微信openid，用于标识支付的用户。
     * @return 返回微信支付API的请求结果，包含预支付交易会话标识等信息。
     * @throws Exception 如果构造请求参数过程中发生错误，抛出异常。
     */
    private String jsapi(String orderNum, BigDecimal total, String description, String openid) throws Exception {
        // 初始化JSON对象，用于存储支付请求的参数。
        JSONObject jsonObject = new JSONObject();
        // 设置微信支付的AppID和商户号。
        jsonObject.put("appid", weChatProperties.getAppid());
        jsonObject.put("mchid", weChatProperties.getMchid());
        // 设置订单描述。
        jsonObject.put("description", description);
        // 设置商户订单号。
        jsonObject.put("out_trade_no", orderNum);
        // 设置支付成功后回调的URL。
        jsonObject.put("notify_url", weChatProperties.getNotifyUrl());

        // 初始化amount JSON对象，用于存储订单金额信息。
        JSONObject amount = new JSONObject();
        // 订单总金额，单位为分，这里需要将BigDecimal金额转换为分，并四舍五入。
        amount.put("total", total.multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP).intValue());
        // 设置货币类型为人民币。
        amount.put("currency", "CNY");
        // 将金额信息添加到主JSON对象中。
        jsonObject.put("amount", amount);
        // 初始化payer JSON对象，用于存储支付者信息。
        JSONObject payer = new JSONObject();
        // 设置支付者的openid。
        payer.put("openid", openid);
        // 将支付者信息添加到主JSON对象中。
        jsonObject.put("payer", payer);
        // 将JSON对象转换为字符串，准备进行API请求。
        String body = jsonObject.toJSONString();
        // 调用post方法，发送支付请求，并返回处理结果。
        return post(JSAPI, body);
    }


    /**
     * 组装微信支付所需的参数并返回给微信小程序调用。
     *
     * @param orderNum    订单号。
     * @param total       订单总金额。
     * @param description 订单描述。
     * @param openid      用户的微信openid。
     * @return 返回包含支付参数的JSON对象。
     * @throws Exception 如果生成签名或解析JSON对象时发生错误，则抛出异常。
     */
    public JSONObject pay(String orderNum, BigDecimal total, String description, String openid) throws Exception {
        // 调用jsapi方法生成预支付交易单的JSON字符串
        String bodyAsString = jsapi(orderNum, total, description, openid);
        // 解析返回的JSON字符串为JSONObject对象
        JSONObject jsonObject = JSON.parseObject(bodyAsString);
        System.out.println(jsonObject);

        // 从JSONObject中获取预支付ID
        String prepayId = jsonObject.getString("prepay_id");
        if (prepayId != null) {
            // 获取当前时间戳，单位为秒
            String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
            // 生成随机的32位数字字符串作为nonceStr
            String nonceStr = RandomStringUtils.randomNumeric(32);
            // 构建签名所需的参数列表
            ArrayList<Object> list = new ArrayList<>();
            list.add(weChatProperties.getAppid());
            list.add(timeStamp);
            list.add(nonceStr);
            list.add("prepay_id=" + prepayId);
            // 将参数列表拼接成字符串，用于签名
            StringBuilder stringBuilder = new StringBuilder();
            for (Object o : list) {
                stringBuilder.append(o).append("\n");
            }
            String signMessage = stringBuilder.toString();
            byte[] message = signMessage.getBytes();

            // 使用RSA算法生成签名
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(PemUtil.loadPrivateKey(new FileInputStream(new File(weChatProperties.getPrivateKeyFilePath()))));
            signature.update(message);
            // 将签名结果转为Base64编码
            String packageSign = Base64.getEncoder().encodeToString(signature.sign());

            // 构造数据给微信小程序，用于调起微信支付
            JSONObject jo = new JSONObject();
            jo.put("timeStamp", timeStamp);
            jo.put("nonceStr", nonceStr);
            jo.put("package", "prepay_id=" + prepayId);
            jo.put("signType", "RSA");
            jo.put("paySign", packageSign);
            return jo;
        }
        return jsonObject;
    }

    /**
     * 申请退款接口调用方法。
     *
     * @param outTradeNo  商户订单号，用于标识原始交易订单。
     * @param outRefundNo 商户退款单号，用于标识退款请求。
     * @param refund      退款金额，精确到分。
     * @param total       原交易订单的总金额，精确到分。
     * @return 返回微信支付接口的响应字符串。
     * @throws Exception 如果请求过程中发生错误，则抛出异常。
     */
    public String refund(String outTradeNo, String outRefundNo, BigDecimal refund, BigDecimal total) throws Exception {
        // 构造请求参数对象
        JSONObject jsonObject = new JSONObject();
        // 商户订单号
        jsonObject.put("out_trade_no", outTradeNo);
        // 商户退款单号
        jsonObject.put("out_refund_no", outRefundNo);

        // 构造金额信息对象
        JSONObject amount = new JSONObject();
        // 退款金额，转换为分并四舍五入
        amount.put("refund", refund.multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP).intValue());
        // 总金额，转换为分并四舍五入
        amount.put("total", total.multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP).intValue());
        // 货币种类
        amount.put("currency", "CNY");
        // 将金额信息加入请求参数对象
        jsonObject.put("amount", amount);
        // 设置退款通知URL
        jsonObject.put("notify_url", weChatProperties.getRefundNotifyUrl());

        // 将请求参数转换为JSON字符串
        String body = jsonObject.toJSONString();

        // 调用接口，发送退款请求
        return post(REFUNDS, body);
    }
}
