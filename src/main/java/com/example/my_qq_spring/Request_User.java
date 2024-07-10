package com.example.my_qq_spring;

public class Request_User extends User{
    private String hello_words;
    private String request_time;
    private String type;
    private String request_id;
    private String if_handled;
    private String request_to_id;

    public Request_User(String userID,String nick_name,String sex,String start_time,
                        byte[] profile,String signature,String hello_words,String request_time,
                        String type,String request_id,String if_handled,String request_to){
        super(userID,nick_name,sex,start_time,profile,signature);
        this.setHello_words(hello_words);
        this.setRequest_time(request_time);
        this.setType(type);
        this.setRequest_id(request_id);
        this.setIf_handled(if_handled);
        this.setRequest_to_id(request_to);
    }

    public String getHello_words() {
        return hello_words;
    }

    public void setHello_words(String hello_words) {
        this.hello_words = hello_words;
    }

    public String getRequest_time() {
        return request_time;
    }

    public void setRequest_time(String request_time) {
        this.request_time = request_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getIf_handled() {
        return if_handled;
    }

    public void setIf_handled(String if_handled) {
        this.if_handled = if_handled;
    }

    public String getRequest_to_id() {
        return request_to_id;
    }

    public void setRequest_to_id(String request_to_id) {
        this.request_to_id = request_to_id;
    }
}
