package com.example.my_qq_spring.socketAPI;

import com.example.my_qq_spring.*;
import com.example.my_qq_spring.services.Server_Services;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@ServerEndpoint("/ws")
@Component
public class SocketAPI {

    // 定义一个 ReentrantLock 对象，用于控制对共享资源（WebSocket 连接）的访问
    private final ReentrantLock lock = new ReentrantLock();

    public static ConcurrentHashMap<String, SocketAPI> LoginMap = new ConcurrentHashMap<>();

    private Session session;

    private String userID;

    private Server_Services server_service = new Server_Services();

    private ObjectMapper objectMapper = new ObjectMapper();



    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("连接已建立");
    }

    @OnMessage
    public void onMessage(String message) {
        try {
            // 解析消息
            UserRequest userRequest = this.objectMapper.readValue(message, UserRequest.class);
            System.out.println("请求类型：" + userRequest.getType());
            if ("login_request".equals(userRequest.getType())) {
                handleLogin(userRequest);
            }
            else if("request_friends".equals(userRequest.getType())){
                handleRequestfriends(userRequest);
            }
            else if("request_unhandledChat".equals(userRequest.getType())){
                handleUnhandledchat(userRequest);
            }
            else if("request_messages".equals(userRequest.getType())){
                handle_SingleFriend_Messages(userRequest);
            }
            else if("request_group_messages".equals(userRequest.getType())){
                handle_Group_Messages(userRequest);
            }
            else if("updateInfo".equals(userRequest.getType())){
                updateInfo(userRequest);
            }
            else if("send_a_message".equals(userRequest.getType())){
                handleReceive_a_message(userRequest);
            }
            else if("request_addFriend".equals(userRequest.getType())){
                handleRequest_add_friend(userRequest);
            }
            else if("agree_add_friend".equals(userRequest.getType())){
                handleAgree_add_friend(userRequest);
            }
            else if("request_groups".equals(userRequest.getType())){
                handleRequestgroups(userRequest);
            }
            else if("request_group_members".equals(userRequest.getType())){
                handleRequestgroupMembers(userRequest);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.print("连接关闭:" + userID);
        System.out.println(" Code: " + reason.getCloseCode() + ", Reason: " + reason.getReasonPhrase());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    private void handleLogin(UserRequest userRequest){
        Result res = new Result();
        String userID = userRequest.getUserID();
        String password = userRequest.getPassword();
        User temp = server_service.check(userID,password);
        if(temp != null){
            List<User> request_user = new ArrayList<>();
            request_user.add(temp);

            this.userID = userID;

            if(LoginMap.containsKey(userID)){
                LoginMap.remove(userID);
                LoginMap.put(userID,this);
            }else{
                LoginMap.put(userID,this);
                System.out.println(LoginMap.get("2487240017") + "已登录");
            }
            System.out.println("用户连接:" + userID + ",当前在线人数为:" + LoginMap.size());

            res.setUsers(request_user);
            res.setResponseType("LOGIN_SUCCESS");

        }
        else res.setResponseType("LOGIN_FAIL");

        this.sendMessage(res);

    }
    private void handleRequestfriends(UserRequest userRequest){
        Result res = new Result();
        System.out.println("已收到好友请求！！！");
        res.setChatingUsers(server_service.get_friends_list(userRequest.getUserID()));
        res.setResponseType("friendlist_response");
        this.sendMessage(res);
    }
    private void handleUnhandledchat(UserRequest userRequest){
        Result res = new Result();
        System.out.println("已收到消息请求！！！");
        res.setUnprocessed_message(new Unprocessed_Message(
                server_service.get_UnprocessedMessages(userRequest.getUserID(),userRequest.getIf_firstTime_request_messages()),
                server_service.get_Requests(userRequest.getUserID())));
        res.setResponseType("unhandledchat_response");
        this.sendMessage(res);
    }

    private void handle_SingleFriend_Messages(UserRequest userRequest){
        Result res = new Result();
        String temp = userRequest.getRequest_messages_begin();
        System.out.println("起始时间：" + temp);
        if(userRequest.getRequest_messages_begin().equals("0")){
            System.out.println("从头开始请求消息！");
            temp = "2003-8-2 8:10:23";
        }
        System.out.println("请求聊天内容的好友：" + userRequest.getChatID());
        res.setChat_messages(server_service.get_messages(userRequest.getChatID(),temp));
        //System.out.println("第一个消息的内容：" + res.getChat_messages()[0].getContent());
        res.setResponseType("messages_response");
        this.sendMessage(res);
    }
    private void handle_Group_Messages(UserRequest userRequest){
        Result res = new Result();
        String temp = userRequest.getRequest_messages_begin();
        if(userRequest.getRequest_messages_begin().equals("0")){
            temp = "2003-8-2 8:10:23";
        }
        System.out.println("请求聊天内容的好友：" + userRequest.getChatID());
        res.setChat_messages(server_service.get_group_messages(userRequest.getChatID(),temp));
        //System.out.println("第一个消息的内容：" + res.getChat_messages()[0].getContent());
        res.setResponseType("group_messages_response");
        this.sendMessage(res);
    }
    private void updateInfo(UserRequest userRequest){
        Result res = new Result();
        List<User> user = new ArrayList<>();
        byte[] imageBytes = null;

        if(userRequest.getFile() != null){
            imageBytes = userRequest.getFile();
        }
        user.add(server_service.updata_profile(userRequest.getUserID(),
                imageBytes,userRequest.getSignature(),userRequest.getNew_nickname()));
        //System.out.println("修改前的信息：" + nickname + " 修改后的信息：" + user.get(0).getNick_name());
        res.setUsers(user);
        res.setResponseType("updateInfo_success_response");
        this.sendMessage(res);

        Result res1 = new Result();
        res.setResponseType("updateInfo_push");
        List<String> push_users = server_service.get_friends_id(userRequest.getUserID());
        for (String userID :push_users) {
            if(LoginMap.containsKey(userID)){
                SocketAPI temp = LoginMap.get(userID);
                //server_service.get_a_user(userRequest.getUserID())
                temp.sendMessage(res);
            }
        }

    }
    private void handleReceive_a_message(UserRequest userRequest){
        Result res = new Result();
        boolean insert_success = false;

        //类型为1，则说明是一个私聊消息，需要在发送方和接收方都加入未处理消息项，只不过发送方不需要加小红点
        if(userRequest.getChat_message().getType() == 1){
            String receiver = userRequest.getChat_message().getRec_id();
            System.out.println("接收方：" + receiver);

            insert_success = server_service.recv_message(userRequest.getChat_message());
            server_service.updata_unprocessed_messages(userRequest.getChat_message(),1);
            server_service.updata_unprocessed_messages(userRequest.getChat_message(),0);
            res.setResponseType("send_success_1");
            List<Chat_Message> templist = new ArrayList<>();
            templist.add(userRequest.getChat_message());
            if(LoginMap.containsKey(receiver)){
                Result res1 = new Result();
                SocketAPI temp = LoginMap.get(receiver);
                res1.setResponseType("new_message_push");
                //在服务端向客户端推送可以更新消息时，如果客户端此时并没有选中这个id的聊天框，则不需要更新
                res1.setUpdated_id(userRequest.getChat_message().getSender_id());
                res1.setChat_messages(templist);

                System.out.println("收到消息，向：" + receiver + "发送推送");
                temp.sendMessage(res1);
            }
            //再把这个消息原封不动发回发送者
            res.setChat_messages(templist);
        }
        else if(userRequest.getChat_message().getType() == 2){
            insert_success = server_service.recv_group_message(userRequest.getChat_message());
            server_service.updata_unprocessed_group_messages(userRequest.getChat_message());
            res.setResponseType("send_success_2");

            //向在线的同群好友推送消息，更新消息列表
            List<String> group_members = server_service.get_group_members_ID(userRequest.getChat_message().getGroup_id());
            for (String i:group_members) {
                if(LoginMap.containsKey(i)){
                    Result res1 = new Result();
                    SocketAPI temp = LoginMap.get(i);
                    res1.setResponseType("group_new_message_push");
                    res1.setUpdated_id(userRequest.getChat_message().getGroup_id());
                    List<Chat_Message> temp2 = new ArrayList<>();
                    temp2.add(userRequest.getChat_message());
                    res1.setChat_messages(temp2);
                    System.out.println("收到消息，向：" + i + "发送推送");
                    temp.sendMessage(res1);
                }
            }
        }

        //res.setResponseType("new_message_push");  //发送方也推送消息，让发送方获取最新消息
//        List<Chat_Message> temp = new ArrayList<>();
//        temp.add(userRequest.getChat_message());
//
//        res.setChat_messages(temp);
        res.setSend_success(insert_success);
        this.sendMessage(res);
    }

    private void handleRequest_add_friend(UserRequest userRequest){
        Result res = new Result();
        boolean flag = server_service.request_a_friend(userRequest.getUserID(),
                userRequest.getRequest_id(),userRequest.getRequest_time(),userRequest.getHello_words());
        if(LoginMap.containsKey(userRequest.getRequest_id())){
            SocketAPI temp = LoginMap.get(userRequest.getRequest_id());
            Result res1 = new Result();
            res1.setResponseType("add_friend_push");
            temp.sendMessage(res1);
        }
        res.setResponseType("add_copy_response");
        res.setSend_success(flag);
        this.sendMessage(res);
    }

    private void handleAgree_add_friend(UserRequest userRequest){
        Result res = new Result();
        res.setSend_success(false);
        if(userRequest.getRequest_res().equals("agree")){
            server_service.add_friend_success(userRequest.getRequest_from(),
                    userRequest.getRequest_to(),userRequest.getResponse_time());
            res.setSend_success(true);
            if(LoginMap.containsKey(userRequest.getRequest_from())){
                SocketAPI temp = LoginMap.get(userRequest.getRequest_from());
                Result res1 = new Result();
                res1.setResponseType("agree_add_push");
                temp.sendMessage(res1);
            }
        }
        res.setResponseType("add_friend_res_response");
        this.sendMessage(res);
    }
    private void handleRequestgroups(UserRequest userRequest){
        Result res = new Result();
        System.out.println("已收到群聊列表请求！！！");
        res.setGroups(server_service.get_groups_list(userRequest.getUserID()));
        res.setResponseType("grouplist_response");
        this.sendMessage(res);
    }
    private void handleRequestgroupMembers(UserRequest userRequest){
        Result res = new Result();
        System.out.println("已收到群聊成员请求！！！");
        res.setUsers(server_service.get_group_members(userRequest.getUserID()));
        res.setResponseType("groupmembers_response");
        //System.out.println(res.getUsers().get(0).getUserID());

        this.sendMessage(res);
    }

    private void sendMessage(Object object){
        lock.lock();
        try {
            String jsonResponse = objectMapper.writeValueAsString(object);
            this.session.getBasicRemote().sendText(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        lock.unlock();
    }
}
