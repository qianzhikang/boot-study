//package com.qzk.boot.socket;
//
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.websocket.*;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import java.util.concurrent.CopyOnWriteArraySet;
//
///**
// * @Description TODO
// * @Date 2022-04-18-10-27
// * @Author Courage
// */
//@ServerEndpoint(value = "/websocket") //设置访问的端点，相当于 controller 的
//@Component
//@Slf4j
//@Data
//public class MyWebSocket {
//
//    /**
//     * 当前在线人数
//     */
//    private static int onlineCount = 0;
//
//    /**
//     * concurrent 包中的线程安全set，用来存放每个客户端对应的MyWebSocket对象
//     */
//    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<>();
//
//    /**
//     * 与某个客户端的链接会话，获取参数，发送数据
//     */
//    private Session session;
//
//
//    /**
//     * 链接建立成功时调用
//     * @param session 会话
//     */
//    @OnOpen
//    public void onOpen(Session session){
//        // 为session进行初始化，把当前的session赋值为连接的session
//        this.session = session;
//        //加入集合
//        webSocketSet.add(this);
//        //增加在线人数
//        addOnlineCount();
//        log.info("有新链接加入！当前人数为：" + getOnlineCount());
//        String queryString = session.getQueryString();
//        String nickname = null;
//        try {
//            nickname = URLDecoder.decode(queryString.split("=")[1],"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            log.error("转码异常");
//        }
//        log.info(nickname);
//        // TODO: 2022/4/18 向客户端发送一条消息：连接成功
//        try{
//            sendMessage(nickname + "上线了！");
//        }catch (IOException e){
//            log.error(e.getMessage());
//        }
//    }
//
//
//    /**
//     * 连接关闭时调用
//     */
//    @OnClose
//    public void onClose(){
//        webSocketSet.remove(this);
//        subOnlineCount();
//        log.info("有链接关闭！当前人数为：" + getOnlineCount());
//    }
//
//
//    /**
//     * 接收客户端以后调用的方法
//     * @param session 会话
//     * @param message 消息
//     * @throws IOException io异常
//     */
//    @OnMessage
//    public void onMessage(Session session,String message)throws IOException{
//        String queryString = session.getQueryString();
//        log.info(queryString);
//        String nickname = null;
//        try {
//            nickname = URLDecoder.decode(queryString.split("=")[1],"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            log.error("转码异常");
//        }
//        log.info(message);
//        // TODO: 2022/4/18  进行群发消息
//        broadcastInfo(nickname+ " 说：" + message);
//    }
//
//
//
//    /**
//     * 封装基础的发送消息操作
//     * @param message 发送的消息
//     * @throws IOException io异常
//     */
//    public void sendMessage(String message) throws IOException{
//        //获取基础的远程链接，发送消息
//        this.session.getBasicRemote().sendText(message);
//    }
//
//
//    /**
//     * 封装群发消息方法
//     * @param message
//     * @throws IOException
//     */
//    public static void broadcastInfo(String message){
//        for (MyWebSocket item : webSocketSet) {
//            try {
//                item.sendMessage(message);
//            }catch (IOException e){
//                log.error(e.getMessage());
//            }
//        }
//    }
//
//
//    /**
//     * 发生错误时调用
//     * @param error 异常对象
//     */
//    @OnError
//    public void onError(Throwable error){
//        log.error("发生错误", error.getMessage());
//        error.printStackTrace();
//    }
//
//
//    //线程方法加锁
//    public static synchronized void addOnlineCount(){
//        MyWebSocket.onlineCount++;
//    }
//
//    public static synchronized void subOnlineCount(){
//        MyWebSocket.onlineCount--;
//    }
//
//    public static synchronized int getOnlineCount(){
//        return onlineCount;
//    }
//
//
//}
