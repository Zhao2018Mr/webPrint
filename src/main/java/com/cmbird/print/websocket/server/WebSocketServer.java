package com.cmbird.print.websocket.server;

import com.alibaba.fastjson.JSON;
import com.cmbird.factory.FactoryForPrintStrategy;
import com.cmbird.print.websocket.domain.RequestVo;
import com.cmbird.strategy.impl.HtmlPrintStrategy;
import com.cmbird.utils.AjaxResult;
import com.cmbird.utils.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/webSocket/{sid}")
@Component
public class WebSocketServer {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineNum = new AtomicInteger();

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    private static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    private Logger logger= LoggerFactory.getLogger(WebSocketServer.class);


    //发送消息
    public void sendMessage(Session session, String message) throws IOException {
        if(session != null){
            synchronized (session) {
//                System.out.println("发送数据：" + message);
                session.getBasicRemote().sendText(message);
            }
        }
    }
    //给指定用户发送信息
    public void sendInfo(String userName, String message){
        Session session = sessionPools.get(userName);
        try {
            sendMessage(session, message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //建立连接成功调用
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "sid") String userName){
        sessionPools.put(userName, session);
        addOnlineCount();
        System.out.println(userName + "连接打印服务！当前人数为" + onlineNum);
        try {
            sendMessage(session, "欢迎" + userName + "加入连接！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //关闭连接时调用
    @OnClose
    public void onClose(@PathParam(value = "sid") String userName){
        sessionPools.remove(userName);
        subOnlineCount();
        logger.info(userName + "断开webSocket连接！当前人数为" + onlineNum);
        System.out.println(userName + "断开webSocket连接！当前人数为" + onlineNum);
    }

    //收到客户端信息
    @OnMessage
    public void onMessage(String data) throws Exception {
        logger.info("打印----{}",data);
        RequestVo requestVo= JSON.parseObject(data,RequestVo.class);
        AjaxResult ajaxResult = SpringUtil.getBean(FactoryForPrintStrategy.class).getStrategy(requestVo.getType()).print(requestVo.getData());
        for (Session session: sessionPools.values()) {
            try {
                sendMessage(session, JSON.toJSONString(ajaxResult));
            } catch(Exception e){
                e.printStackTrace();
                continue;
            }
        }
        logger.info("打印结束----{}",JSON.toJSONString(ajaxResult));
    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable){
        System.out.println("发生错误");
        logger.info("发生错误");
        throwable.printStackTrace();
    }

    public static void addOnlineCount(){
        onlineNum.incrementAndGet();
    }

    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }

}