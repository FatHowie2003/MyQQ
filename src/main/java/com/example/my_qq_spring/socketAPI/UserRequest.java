package com.example.my_qq_spring.socketAPI;

import com.example.my_qq_spring.Chat_Message;
import org.springframework.web.multipart.MultipartFile;

public class UserRequest {
    private String type;//表示客户端请求的类型
    private String userID;
    private String password;
    private int if_firstTime_request_messages;
    private int chatID;
    private String friendID;
    private Chat_Message chat_message;//单条聊天消息
    private String request_messages_begin;//只请求这个时间以后的消息
    private String signature;
    private String new_nickname;
    private byte[] file;

    //请求好友时使用数据结构
    private String request_id;
    private String hello_words;
    private String request_time;
   //同意请求时数据结构
    private String response_time;
    private String request_from;
    private String request_to;
    private String request_res;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIf_firstTime_request_messages() {
        return if_firstTime_request_messages;
    }

    public void setIf_firstTime_request_messages(int if_firstTime_request_messages) {
        this.if_firstTime_request_messages = if_firstTime_request_messages;
    }

    public int getChatID() {
        return chatID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }

    public String getFriendID() {
        return friendID;
    }

    public void setFriendID(String friendID) {
        this.friendID = friendID;
    }

    public Chat_Message getChat_message() {
        return chat_message;
    }

    public void setChat_message(Chat_Message chat_message) {
        this.chat_message = chat_message;
    }

    public String getRequest_messages_begin() {
        return request_messages_begin;
    }

    public void setRequest_messages_begin(String request_messages_begin) {
        this.request_messages_begin = request_messages_begin;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getNew_nickname() {
        return new_nickname;
    }

    public void setNew_nickname(String new_nickname) {
        this.new_nickname = new_nickname;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getRequest_time() {
        return request_time;
    }

    public void setRequest_time(String request_time) {
        this.request_time = request_time;
    }

    public String getHello_words() {
        return hello_words;
    }

    public void setHello_words(String hello_words) {
        this.hello_words = hello_words;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getResponse_time() {
        return response_time;
    }

    public void setResponse_time(String response_time) {
        this.response_time = response_time;
    }

    public String getRequest_from() {
        return request_from;
    }

    public void setRequest_from(String request_from) {
        this.request_from = request_from;
    }

    public String getRequest_to() {
        return request_to;
    }

    public void setRequest_to(String request_to) {
        this.request_to = request_to;
    }

    public String getRequest_res() {
        return request_res;
    }

    public void setRequest_res(String request_res) {
        this.request_res = request_res;
    }

}
