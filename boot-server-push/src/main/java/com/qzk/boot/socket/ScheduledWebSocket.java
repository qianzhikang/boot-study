package com.qzk.boot.socket;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description TODO
 * @Date 2022-04-18-14-21
 * @Author Courage
 */
@ServerEndpoint(value = "/websocket/{id}") //设置访问的端点，相当于 controller 的
@Component
@Slf4j
@Data
public class ScheduledWebSocket {

    /**
     * 当前在线人数
     */
    private static int onlineCount = 0;

    /**
     * concurrent 包中的线程安全set，用来存放每个客户端对应的MyWebSocket对象
     */
    private static ConcurrentHashMap<Integer, ScheduledWebSocket> webSocketHash = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的链接会话，获取参数，发送数据
     */
    private Session session;

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 链接建立成功时调用
     *
     * @param session 会话
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("id") Integer id) {
        // 为session进行初始化，把当前的session赋值为连接的session
        this.session = session;
        //加入集合
        webSocketHash.put(id, this);
        //增加在线人数
        addOnlineCount();
        log.info("有新链接加入！当前人数为：" + getOnlineCount());
        this.id = id;
        // TODO: 2022/4/18 向客户端发送一条消息：连接成功
        try {
            sendMessage("id为" + id + "的用户上线了！");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


    /**
     * 连接关闭时调用
     */
    @OnClose
    public void onClose() {
        webSocketHash.remove(this.id);
        subOnlineCount();
        log.info("有链接关闭！当前人数为：" + getOnlineCount());
    }


    /**
     * 接收客户端以后调用的方法
     *
     * @param session 会话
     * @param message 消息
     * @throws IOException io异常
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        try {
            sendMessage(message);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


    /**
     * 发生错误时调用
     *
     * @param error 异常对象
     */
    @OnError
    public void onError(Throwable error) {
        log.error("发生错误", error.getMessage());
        error.printStackTrace();
    }


    /**
     * 封装基础的发送消息操作
     *
     * @param message 发送的消息
     * @throws IOException io异常
     */
    public void sendMessage(String message) throws IOException {
        //获取基础的远程链接，发送消息
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * @param id      用户id
     * @param message 消息
     */
    public void sendMessageById(Integer id, String message) {
        try {
            if ( webSocketHash.get(id) != null) {
                webSocketHash.get(id).sendMessage(message);
            } else {
                log.error("没有这个key不能发送消息！");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 封装群发消息方法
     *
     * @param message
     * @throws IOException
     */
    public static void broadcastInfo(String message) {
        for (ScheduledWebSocket item : webSocketHash.values()) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    //线程方法加锁
    public static synchronized void addOnlineCount() {
        ScheduledWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        ScheduledWebSocket.onlineCount--;
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

}

