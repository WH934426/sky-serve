package com.wh.websocket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * WebSocket 服务
 */
@Slf4j
@Component
@ServerEndpoint("/ws/{sid}")
public class WebSocketServer {

    // 存放会话对象
    private static final Map<String, Session> sessionMap = new HashMap<>();

    /**
     * 当WebSocket连接打开时调用此方法。
     *
     * @param session WebSocket会话，用于与客户端进行通信。
     * @param sid     会话ID，作为参数路径的一部分，用于唯一标识客户端会话。
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        System.out.println("客户端：" + sid + "建立连接");
        sessionMap.put(sid, session);
    }

    /**
     * 当客户端发送消息时，主要功能是接收客户端发送的消息
     *
     * @param message 客户端发送的消息内容。
     * @param sid     会话ID，用于标识发送消息的客户端会话。
     */
    @OnMessage
    public void onMessage(String message, @PathParam("sid") String sid) {
        System.out.println("收到来自客户端：" + sid + "的信息:" + message);
    }


    /**
     * 当WebSocket连接关闭,用于处理连接关闭后的清理工作
     *
     * @param sid 会话ID，用于唯一标识一个WebSocket连接。通过@PathParam注解将其绑定到方法参数。
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        System.out.println("连接断开:" + sid);
        // 从会话映射中移除对应的会话ID，释放资源，避免内存泄漏。
        sessionMap.remove(sid);
    }


    /**
     * 群发消息给所有客户端。
     *
     * @param message 要发送给所有客户端的消息内容。
     */
    public void sendToAllClient(String message) {
        // 获取当前所有客户端会话的集合
        Collection<Session> sessions = sessionMap.values();

        // 遍历所有会话，尝试发送消息
        for (Session session : sessions) {
            try {
                // 向当前会话发送消息
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                log.error("发送消息异常", e);
            }
        }
    }

}
