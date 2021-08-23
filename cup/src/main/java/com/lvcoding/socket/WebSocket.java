/*
 *
 *
 *        Copyright (c) 2018-2021, wuyanshen All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions are met:
 *
 *    Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *    Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *    Neither the name of the lvcoding.com developer nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *    Author: wuyanshen
 *
 *
 */

package com.lvcoding.socket;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wuyanshen
 * @date 2020-06-03 11:16 上午
 * @description 描述
 */
@Data
@Component
@ServerEndpoint(value = "/websocket/{name}")
public class WebSocket {
    private static int onlineCount = 0;
    private Session session;
    private String name;
    private static Map<String, WebSocket> allClients = new HashMap<>();

    /**
     * 当建立连接时会调用此方法
     *
     * @param name
     * @param session
     * @return void
     * @date 2020/6/3 11:19 上午
     * @author wuyanshen
     */
    @OnOpen
    public void onOpen(@PathParam("name") String name, Session session) {
        this.session = session;
        this.name = this.name;
        allClients.put(name, this);
        System.out.println(name + "建立连接了");
        addOnlineCount(name);
        System.out.println("有新连接加入！当前在线人数为"+getOnlineCount());
    }

    /**
     * 接收客户端发来的消息
     * 当服务器收到消息时会调用此方法
     *
     * @param message
     * @param session
     * @return void
     * @date 2020/6/3 11:20 上午
     * @author wuyanshen
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        JSONObject jsonObject = JSONUtil.parseObj(message);
        //获取发送给谁
        String to = jsonObject.getStr("toUser");
        //获取发送内容
        String toMessage = jsonObject.getStr("toMessage");

        //指定发消息
        //根据目标接受者，找到它的session连接
        WebSocket mySocket = allClients.get(to);//获取到目标接收者的websocket
        if (mySocket != null) {
            //获取到服务器和目标接收者的连接
            Session toSession = mySocket.getSession();
            if (toSession.isOpen()) {//如果连接时打开状态
                this.sendMessage(toSession,toMessage);
            }
        } else {
            this.sendMessage(session,"对方不在线");
        }


        //群发消息
//        for (WebSocket item:allClients.values()){
//            this.sendMessage(item.getSession(),toMessage);
//        }

    }

    /**
     * 当出现异常时触发
     *
     * @param session
     * @param e
     * @return void
     * @date 2020/6/3 11:27 上午
     * @author wuyanshen
     */
    @OnError
    public void onError(Session session, Throwable e) {
        System.out.println("出错啦");
        this.sendMessage(session,"出错啦");
    }

    /**
     * 当连接关闭时触发
     *
     * @param session
     * @return void
     * @date 2020/6/3 11:28 上午
     * @author wuyanshen
     */
    @OnClose
    public void onClose(Session session) {
        subOnlineCount();
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
        System.out.println("websocket连接关闭");
        this.sendMessage(session,"websocket连接关闭");
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount(String name) {
        if(WebSocket.onlineCount<0){
            WebSocket.onlineCount = 0;
        }
        WebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }

    public synchronized void sendMessage(Session session,String message){
        session.getAsyncRemote().sendText(message);
    }


//    private static int onlineCount=0;
//    private static CopyOnWriteArrayList<MySocket> webSocketSet=new CopyOnWriteArrayList<MySocket>();
//    private Session session;
//
//    @OnOpen
//    public void onOpen(Session session){
//        this.session=session;
//        webSocketSet.add(this);//加入set中
//        addOnlineCount();
//        System.out.println("有新连接加入！当前在线人数为"+getOnlineCount());
//    }
//
//    @OnClose
//    public void onClose(){
//        webSocketSet.remove(this);
//        subOnlineCount();
//        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
//    }
//
//    @OnMessage
//    public void onMessage(String message,Session session){
//        System.out.println("来自客户端的消息："+message);
////        群发消息
//        for (MySocket item:webSocketSet){
//            try {
//                item.sendMessage(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//                continue;
//            }
//        }
//    }
//
//    @OnError
//    public void onError(Session session,Throwable throwable){
//        System.out.println("发生错误！");
//        throwable.printStackTrace();
//    }
//    //   下面是自定义的一些方法
//    public void sendMessage(String message) throws IOException {
//        this.session.getAsyncRemote().sendText(message);
//    }
//
//    public static synchronized int getOnlineCount(){
//        return onlineCount;
//    }
//    public static synchronized void addOnlineCount(){
//        MySocket.onlineCount++;
//    }
//    public static synchronized void subOnlineCount(){
//        MySocket.onlineCount--;
//    }

}
