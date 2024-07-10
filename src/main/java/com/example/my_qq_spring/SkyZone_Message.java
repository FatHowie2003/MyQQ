package com.example.my_qq_spring;

import org.apache.logging.log4j.util.Chars;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class SkyZone_Message implements Serializable {
    private String content;
    private BufferedImage[] imgs;
    private User sender;
    private String[] comments;
    private String[] supporters;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BufferedImage[] getImgs() {
        return imgs;
    }

    public void setImgs(BufferedImage[] imgs) {
        this.imgs = imgs;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String[] getComments() {
        return comments;
    }

    public void setComments(String[] comments) {
        this.comments = comments;
    }

    public String[] getSupporters() {
        return supporters;
    }

    public void setSupporters(String[] supporters) {
        this.supporters = supporters;
    }

}
