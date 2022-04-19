package com.qzk.boot.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qzk.boot.entity.Barrage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Description TODO
 * @Date 2022-04-18-23-46
 * @Author Courage
 */
@ServerEndpoint("/server")
@Component
@Slf4j
@Data
public class WebSocketServer {

    /**
     * 静态变量，用来记录当前在线链接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每一个客户端对应的MyWebSocket对象。
     */
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    /**
     * concurrent包的线程安全Set，用来存放每一个客户端对应的MyWebSocket对象。
     */
    private Session session;

    /**
     * json转换化工具
     */
    ObjectMapper mapper = new ObjectMapper();
    /**
     * 随机数工具
     */
    Random random = new Random();

    /**
     * 链接创建成功调用的方法
     * @param session 会话
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.err.println(this.session);
        //加入set中
        webSocketSet.add(this);
        //在线数加1
        addOnlineCount();
        log.info("当前在线人数为" + getOnlineCount());
        try {
            sendMessage("链接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        //从set中删除
        webSocketSet.remove(this);
        //在线数减1
        subOnlineCount();
        log.info("有一链接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来信息:" + message);
        Barrage build = Barrage.builder().height(random.nextInt(1000) * 2 / 4 + "px").message(message).color("red").build();
        List<Barrage> dataList = new ArrayList<>();
        dataList.add(build);

        //群发消息
        for (WebSocketServer item : webSocketSet) {
            try {
                System.out.println(mapper.writeValueAsString(dataList));
                item.sendMessage(mapper.writeValueAsString(dataList));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送错误时调用
     * @param session 连接session
     * @param error 异常
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     * @param message 推送的消息
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 定时推送弹幕
     * @throws IOException
     */
    //@Scheduled(cron = "0/3 * * * * ?")
    public void autoSendMessage() throws IOException {
        for (WebSocketServer webSocketServer : webSocketSet) {
            List<Barrage> barrages = initData();
            for (WebSocketServer item : webSocketSet) {
                try {
                    item.sendMessage(mapper.writeValueAsString(barrages));
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
            }
            //webSocketServer.session.getBasicRemote().sendText(mapper.writeValueAsString(barrages));
        }

    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }


    public List<Barrage> initData() {
        List<Barrage> dataList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Barrage build = Barrage.builder().message("测试弹幕" + i).height(random.nextInt(1000) * 2 / 4 + "px").color("white").build();
            dataList.add(build);
        }
        return dataList;
    }
}
