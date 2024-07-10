package com.example.my_qq_spring;

import java.util.List;

public class Result {
    private List<User> users;
    private List<User> chatingUsers;
    private List<Group> groups;
    private SkyZone_Message[] skyZone_messagess;
    private String responseType;
    private List<Chat_Message> chat_messages;
    private Unprocessed_Message unprocessed_message;
    private List<User> research_users;
    private boolean send_success; //记录客户端发的消息是否成功写入数据库
    private String updated_id;//在服务端向客户端推送可以更新消息时，如果客户端此时并没有选中这个id的聊天框，则不需要更新

    public boolean isSend_success() {
        return send_success;
    }

    public void setSend_success(boolean send_success) {
        this.send_success = send_success;
    }

    public List<Chat_Message> getChat_messages() {
        return chat_messages;
    }

    public void setChat_messages(List<Chat_Message> chat_messages) {
        this.chat_messages = chat_messages;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public SkyZone_Message[] getSkyZone_messagess() {
        return skyZone_messagess;
    }

    public void setSkyZone_messagess(SkyZone_Message[] skyZone_messagess) {
        this.skyZone_messagess = skyZone_messagess;
    }


    public List<User> getChatingUsers() {
        return chatingUsers;
    }

    public void setChatingUsers(List<User> chatingUsers) {
        this.chatingUsers = chatingUsers;
    }


    public Unprocessed_Message getUnprocessed_message() {
        return unprocessed_message;
    }

    public void setUnprocessed_message(Unprocessed_Message unprocessed_message) {
        this.unprocessed_message = unprocessed_message;
    }
    public void setResearch_users(List<User> research_users) {
        this.research_users = research_users;
    }

    public List<User> getResearch_users() {
        return research_users;
    }


    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public String getUpdated_id() {
        return updated_id;
    }

    public void setUpdated_id(String updated_id) {
        this.updated_id = updated_id;
    }
}
