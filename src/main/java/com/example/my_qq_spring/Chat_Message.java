package com.example.my_qq_spring;

import java.io.Serializable;

public class Chat_Message  implements Serializable {
    private String content;
    private String sender_time;
    private String sender_id;
    private int chat_id;
    private String rec_id;
    private String group_id;
    private int type;//区分开群聊和私聊的消息
    // 无参构造函数
    public Chat_Message() {
    }

    public Chat_Message(String sender_id, String sender_time, String content){
        this.setContent(content);
        this.setSender_id(sender_id);
        this.setSender_time(sender_time);
    }
    public Chat_Message(String sender_id,String rec_id, String sender_time, int chat_id,String content,int type){
        this.setContent(content);
        this.setSender_id(sender_id);
        this.setSender_time(sender_time);
        this.setRec_id(rec_id);
        this.setChat_id(chat_id);
        this.setType(type);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender_time() {
        return sender_time;
    }

    public void setSender_time(String sender_time) {
        this.sender_time = sender_time;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getRec_id() {
        return rec_id;
    }

    public void setRec_id(String rec_id) {
        this.rec_id = rec_id;
    }

    public int getChat_id() {
        return chat_id;
    }

    public void setChat_id(int chat_id) {
        this.chat_id = chat_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }
}
