package com.example.my_qq_spring;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Logined_Users {
    private static HashMap<String, Status> loginHashMap = new HashMap<>(10000);

    // 添加用户登录状态
    public static void add(String id,String login_host) {
        Status status = new Status(login_host,LocalDateTime.now());
        loginHashMap.put(id, status);
    }

    // 删除用户登录状态
    public static void delete(String id) {
        loginHashMap.remove(id);
    }

    // 检查用户是否登录，如果登录则返回用户状态，否则返回 null
    public static boolean check(String id) {
        Status temp1 = loginHashMap.get(id);
        if(temp1 != null && temp1.getRemainingTime() >= 0){
            return true;
        }
        return false;
    }
}

class Status {
    private String loginHost;
    private LocalDateTime loginTime;

    public Status(String loginHost,LocalDateTime loginTime) {
        this.loginHost = loginHost;
        this.loginTime = loginTime;
    }

    public String getLoginHost() {
        return loginHost;
    }

    public void setLoginHost(String loginHost) {
        this.loginHost = loginHost;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }


    // 计算登录状态的剩余时间，假设登录时间为1小时
    public long getRemainingTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime = loginTime.plusHours(12); // 假设登录状态有效期为12小时
        if (now.isAfter(expirationTime)) {
            // 超时，返回0
            return 0;
        } else {
            // 计算剩余时间
            long remainingMinutes = java.time.Duration.between(now, expirationTime).toMinutes();
            return Math.max(remainingMinutes, 0); // 如果剩余时间小于0，返回0
        }
    }

}
