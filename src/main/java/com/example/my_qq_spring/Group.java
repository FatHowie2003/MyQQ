package com.example.my_qq_spring;

import java.util.List;

public class Group {
    private String groupID;
    private String group_name;
    private String start_time;
    private byte[] profile;
    private String if_master;
    private String if_admin;
    private String signature;
    private int chat_id;
    private List<User> members;

    public Group(String groupID,String group_name,String start_time,byte[] profile,
                 String if_master,String if_admin,String signature,int chat_id){
        this.setGroupID(groupID);
        this.setGroup_name(group_name);
        this.setStart_time(start_time);
        this.setProfile(profile);
        this.setIf_master(if_master);
        this.setIf_admin(if_admin);
        this.setSignature(signature);
        this.setChat_id(chat_id);
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public byte[] getProfile() {
        return profile;
    }

    public void setProfile(byte[] profile) {
        this.profile = profile;
    }

    public String getIf_master() {
        return if_master;
    }

    public void setIf_master(String if_master) {
        this.if_master = if_master;
    }

    public String getIf_admin() {
        return if_admin;
    }

    public void setIf_admin(String if_admin) {
        this.if_admin = if_admin;
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

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
}
