package com.wh.controller.notify;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import com.wh.properties.WeChatProperties;
import com.wh.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

/**
 * 支付回调相关接口
 */
@RestController
@RequestMapping("/notify")
@Slf4j
public class PayNotifyController {
    private final OrderService orderService;
    private final WeChatProperties weChatProperties;

    public PayNotifyController(OrderService orderService, WeChatProperties weChatProperties) {
        this.orderService = orderService;
        this.weChatProperties = weChatProperties;
    }

    /**
     * 从HttpServletRequest中读取请求体中的数据。
     * 这个方法主要用于处理POST请求，其中包含的数据通常是以键值对或JSON格式发送的。
     *
     * @param request HttpServletRequest对象，通过这个对象可以获取HTTP请求的相关信息。
     * @return 返回一个字符串，包含了请求体中的所有数据。
     * @throws IOException 如果在读取请求数据时发生错误，将抛出此异常。
     */
    private static String readData(HttpServletRequest request) throws IOException {
        // 读取数据
        BufferedReader reader = request.getReader();
        StringBuilder result = new StringBuilder(); // 用于存储读取到的数据
        String line = null;
        // 循环读取每一行数据，直到读取完毕
        while ((line = reader.readLine()) != null) {
            result.append(line); // 将读取到的每一行数据追加到StringBuilder中
        }
        return result.toString(); // 返回读取到的所有数据
    }

    /**
     * 向微信发送响应。
     * <p>
     * 该方法用于构建并发送一个表示成功响应的JSON对象给微信服务器。
     * JSON对象包含两个字段：code和message，它们的值都是SUCCESS。
     *
     * @param response 用于向微信服务器发送响应的HttpServletResponse对象。
     * @param mapper   用于序列化JSON对象的ObjectMapper实例。
     * @throws IOException 如果在序列化JSON对象或发送响应时发生I/O错误。
     */
    private static void responseToWeChat(HttpServletResponse response, ObjectMapper mapper) throws IOException {
        // 创建一个JSON对象节点，用于构建响应体
        ObjectNode node = mapper.createObjectNode();
        // 设置响应的状态码和消息为SUCCESS
        node.put("code", "SUCCESS");
        node.put("message", "SUCCESS");
        // 将JSON对象序列化为字符串
        String jsonResponse = mapper.writeValueAsString(node);

        // 设置响应状态为200，表示请求处理成功
        response.setStatus(200);
        // 设置响应头，指明响应内容的类型为JSON
        response.setHeader("Content-type", ContentType.APPLICATION_JSON.toString());
        // 将序列化后的JSON字符串写入响应输出流
        response.getOutputStream().write(jsonResponse.getBytes(StandardCharsets.UTF_8));
        // 刷新缓冲区，确保响应立即发送
        response.flushBuffer();
    }

    /**
     * 处理支付成功通知的请求。
     * 当用户完成支付后，支付平台会向本接口发送通知，应用需要处理通知中的数据来更新订单状态等信息。
     *
     * @param request  包含支付通知数据的HttpServletRequest对象。
     * @param response 用于向支付平台发送响应的HttpServletResponse对象。
     * @throws Exception 如果处理通知或响应支付平台时发生错误。
     */
    @RequestMapping("/paySuccess")
    public void paySuccessNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 读取数据
        String body = readData(request);
        log.info("支付成功通知:{}", body);
        // 数据解密
        String plainText = decryptData(body);
        log.info("解密后的文本：{}", plainText);

        // 使用Jackson解析字符串为JSONObject
        ObjectMapper mapper = new ObjectMapper();
        JSONObject jsonObject = mapper.readValue(plainText, JSONObject.class);
        String outTradeNo = jsonObject.getString("out_trade_no");
        String transactionId = jsonObject.getString("transaction_id");

        log.info("商户平台订单号：{}", outTradeNo);
        log.info("微信支付交易号：{}", transactionId);

        // 业务处理，修改订单状态、来单提醒
        orderService.paySuccess(outTradeNo);
        // 响应微信
        responseToWeChat(response, mapper);
    }

    /**
     * 解密数据。
     * 使用AES-GCM-256算法解密传入的加密数据，该数据通常是由微信支付V3接口返回的加密内容。
     * 解密过程需要使用API密钥（apiV3Key）作为解密密钥，以及加密数据中的nonce和associatedData作为解密的额外数据。
     *
     * @param body 加密的JSON字符串，包含需要解密的资源（resource）。
     * @return 解密后的明文字符串。
     * @throws GeneralSecurityException 如果解密过程中出现安全问题（如密钥无效或数据被篡改）。则抛出GeneralSecurityException异常。
     */
    private String decryptData(String body) throws GeneralSecurityException {
        // 解析加密的JSON字符串，获取解密所需的信息。
        JSONObject resultObject = JSON.parseObject(body);
        JSONObject resource = resultObject.getJSONObject("resource");
        String ciphertext = resource.getString("ciphertext");
        String nonce = resource.getString("nonce");
        String associatedData = resource.getString("associated_data");

        // 使用API密钥初始化AES工具类。
        AesUtil aesUtil = new AesUtil(weChatProperties.getApiV3Key().getBytes(StandardCharsets.UTF_8));
        // 使用AES-GCM-256算法解密密文，并返回解密后的明文。
        String plainText;
        plainText = aesUtil.decryptToString(associatedData.getBytes(StandardCharsets.UTF_8),
                nonce.getBytes(StandardCharsets.UTF_8),
                ciphertext);
        return plainText;
    }

}
