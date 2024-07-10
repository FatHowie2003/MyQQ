package com.example.my_qq_spring;

import java.util.List;

public class Unprocessed_Message {

    private List<Chating_item> chating_items;

    private List<Request_User> request_users;

    public Unprocessed_Message(List<Chating_item> users, List<Request_User> requestusers){
        this.setRequest_users(requestusers);
        this.chating_items = users;
    }
    public Unprocessed_Message(){}
    public List<Chating_item> getChating_users() {
        return chating_items;
    }

    public void setChating_users(List<Chating_item> chating_items) {
        this.chating_items = chating_items;
    }

    public List<Request_User> getRequest_users() {
        return request_users;
    }

    public void setRequest_users(List<Request_User> request_users) {
        this.request_users = request_users;
    }
}
