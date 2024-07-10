package com.example.my_qq_spring;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userID;
    private String password;
    private String nick_name;
    private String sex;
    private String start_time;
    private byte[] profile;
    private String signature;
    private int chat_id;
    public User(String userID,String password,String nick_name,String sex,String start_time) {
        setUserID(userID);
        setPassword(password);
        setnick_name(nick_name);
        setsex(sex);
        setstart_time(start_time);
    }
    public User(String userID,String nick_name,String sex,String start_time) {
        setUserID(userID);
        setnick_name(nick_name);
        setsex(sex);
        setstart_time(start_time);
    }
    public User(String userID,String nick_name,String sex,String start_time,byte[] profile,String signature) {
        setUserID(userID);
        setnick_name(nick_name);
        setsex(sex);
        setstart_time(start_time);
        setProfile(profile);
        setSignature(signature);
    }
    public User(){
    }


    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public String getnickname() {
        return nick_name;
    }

    public void setUserID(String a) {
        userID = a;
    }

    public void setPassword(String a) {
        password = a;
    }

    public void setnick_name(String a) {
        nick_name = a;
    }
    public void setsex(String a){
        sex = a;
    }
    public void setstart_time(String a){
        start_time = a;
    }

    public byte[] getProfile() {
        return profile;
    }

    public void setProfile(byte[] profile) {
        this.profile = profile;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getChat_id() {
        return chat_id;
    }

    public void setChat_id(int chat_id) {
        this.chat_id = chat_id;
    }

//     @Override
//    public String toString(){
//        return "{username:" + this.getUsername() + ",password:" +this.getPassword() + "}";
//    }
//damn!!!!
}
