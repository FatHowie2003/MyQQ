package com.example.my_qq_spring;

import java.io.Serializable;

public class Chating_item implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userID;
    private String nickname;
    private byte[] profile;
    private int chat_id;
    private String newest_message;
    private String birth_time;
    private int unhandled_count = 0;
    private int type;
    private String sender_nickname;
    public Chating_item(String userID, String nickname,String start_time,
                        int chat_id, byte[] profile,int type,String newest_message,int unhandled_count,
                        String sender_nickname) {
        this.setNickname(nickname);
        this.setUserID(userID);
        this.setBirth_time(start_time);
        this.setProfile(profile);
        this.setType(type);
        this.setNewest_message(newest_message);
        this.setChat_id(chat_id);
        this.setUnhandled_count(unhandled_count);
        this.setSender_nickname(sender_nickname);
    }
//    public Chating_User(String userID,String nick_name,String sex,String start_time,int chat_id,String newest_message) {
//        super(userID,nick_name,sex,start_time);
//        this.setChat_id(chat_id);
//        this.setNewest_message(newest_message);
//    }
//    public Chating_item(String userID, String nick_name int chat_id,
//                        String newest_message, String birth_time, int unhandled_count, String signature, byte[] profile) {
//        super(userID,nick_name,sex,start_time,profile,signature);
//        this.setChat_id(chat_id);
//        this.setNewest_message(newest_message);
//        this.setBirth_time(birth_time);
//        this.setUnhandled_count(unhandled_count);
//    }

    public int getChat_id() {
        return chat_id;
    }

    public void setChat_id(int chat_id) {
        this.chat_id = chat_id;
    }

    public String getNewest_message() {
        return newest_message;
    }

    public void setNewest_message(String newest_message) {
        this.newest_message = newest_message;
    }

    public String getBirth_time() {
        return birth_time;
    }

    public void setBirth_time(String birth_time) {
        this.birth_time = birth_time;
    }

    public int getUnhandled_count() {
        return unhandled_count;
    }

    public void setUnhandled_count(int unhandled_count) {
        this.unhandled_count = unhandled_count;
    }

    public byte[] getProfile() {
        return profile;
    }

    public void setProfile(byte[] profile) {
        this.profile = profile;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSender_nickname() {
        return sender_nickname;
    }

    public void setSender_nickname(String sender_nickname) {
        this.sender_nickname = sender_nickname;
    }
}
