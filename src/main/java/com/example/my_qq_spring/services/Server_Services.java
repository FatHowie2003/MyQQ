package com.example.my_qq_spring.services;

import com.example.my_qq_spring.*;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class Server_Services {
    private User user = new User();
    Connection con;
    //URL指向要访问的数据库名mydata
    String url = "jdbc:mysql://localhost:3306/qq_cilents?autoReconnect=true&useSSL=false";
    //
    //MySQL配置时的用户名
    String sql_user = "root";
    //MySQL配置时的密码
    String sql_password = "200382fjh";

    Statement statement;

    public Server_Services(){
        try {
            con = DriverManager.getConnection(url,sql_user,sql_password);
            if(!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            statement = con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized User check(String name,String password0) {
        boolean flag = false;
        User user = new User();
        ResultSet res = null;
        try {

            System.out.println("密码:" + password0);
            String sql = "SELECT * FROM login_inf WHERE id=? AND password=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, password0);
            res = pstmt.executeQuery();

            while (res.next()) {
                String i = res.getString("id");
                String p = res.getString("password");
                System.out.println("id:" + i + " password:" + p);
                // 如果存在数据，表示验证成功，返回true
                if (i.equals(name) && p.equals(password0)) {
                    sql = "SELECT * FROM user_inf WHERE id= ? ;";
                    pstmt = con.prepareStatement(sql);
                    pstmt.setString(1, name);
                    ResultSet res1 = pstmt.executeQuery();

                    while (res1.next()) {
                        user.setnick_name(res1.getString("name"));
                        user.setsex(res1.getString("sex"));
                        user.setUserID(res1.getString("id"));
                        user.setPassword(password0);
                        user.setstart_time(res1.getString("registration_time"));
                        user.setSignature(res1.getString("signature"));
                        user.setProfile(res1.getBytes("profile_photo"));
                    }
                    res1.close();
                    return user;
                } else return null;
            }

        } catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                res.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public synchronized List<User> get_friends_list(String id) {
        //User[] users = null;
        List<User> users = new ArrayList<>();
        ResultSet res = null;
        try {

            String sql1 = "SELECT relationships.*,user_inf.* " +
                    "FROM relationships " +
                    "INNER JOIN user_inf ON user_inf.id = relationships.id2 " +
                    "WHERE id1 = ?";
            PreparedStatement pstmt = con.prepareStatement(sql1);
            pstmt.setString(1, id);
            res = pstmt.executeQuery();

            while (res.next()) {
                String id2 = res.getString("id2");
                String start_time = res.getString("start_time");
                int chat_id = res.getInt("chat_id");
                String nickname = res.getString("name");
                String sex = res.getString("sex");
                String signature = res.getString("signature");
                byte[] profile = res.getBytes("profile_photo");
                User temp = new User(id2,nickname,sex,start_time,profile,signature);
                temp.setChat_id(chat_id);
                users.add(temp);
            }
        } catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                res.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return users;
    }

    public synchronized List<Chat_Message> get_messages(int chat_id,String begin_time) {

        List<Chat_Message> chatMessages = new ArrayList<>();
        ResultSet res = null;
        try {

            String sql1 = "SELECT send_time, sender_id, content " +
                    "FROM messages " +
                    "WHERE chat_id = ? and send_time > ?;";
            PreparedStatement pstmt = con.prepareStatement(sql1);
            pstmt.setInt(1, chat_id);
            pstmt.setString(2,begin_time);
            res = pstmt.executeQuery();

            while (res.next()) {
                String send_time = res.getString("send_time");
                String sender_id = res.getString("sender_id");
                String content = res.getString("content");
                Chat_Message temp = new Chat_Message(sender_id, send_time, content);
                chatMessages.add(temp);
            }

        } catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                res.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return chatMessages;
    }

    public synchronized List<Chat_Message> get_group_messages(int chat_id,String begin_time) {

        List<Chat_Message> chatMessages = new ArrayList<>();
        ResultSet res = null;
        try {

            String sql1 = "SELECT send_time, sender_id, content " +
                    "FROM group_messages " +
                    "WHERE chat_id = ? and send_time >= ?;";
            PreparedStatement pstmt = con.prepareStatement(sql1);
            pstmt.setInt(1, chat_id);
            pstmt.setString(2,begin_time);
            res = pstmt.executeQuery();

            while (res.next()) {
                String send_time = res.getString("send_time");
                String sender_id = res.getString("sender_id");
                String content = res.getString("content");
                //String remind_id = res.getString("remind_id");
                Chat_Message temp = new Chat_Message(sender_id, send_time, content);
                chatMessages.add(temp);
            }

        } catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                res.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return chatMessages;
    }

    public synchronized boolean recv_message(Chat_Message message){
        try {

            String sql1 = "INSERT INTO messages(send_time,sender_id,receiver_id,chat_id,content)" +
                    "VALUES(?,?,?,?,?);";
            PreparedStatement pstmt = con.prepareStatement(sql1);
            pstmt.setString(1,message.getSender_time());
            pstmt.setString(2,message.getSender_id());
            pstmt.setString(3,message.getRec_id());
            pstmt.setInt(4,message.getChat_id());
            pstmt.setString(5,message.getContent());
            pstmt.executeUpdate();

        }catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
            return false;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public synchronized boolean recv_group_message(Chat_Message message){

        try {

            String sql1 = "INSERT INTO group_messages(send_time,sender_id,group_id,chat_id,content)" +
                    "VALUES(?,?,?,?,?);";
            PreparedStatement pstmt = con.prepareStatement(sql1);
            pstmt.setString(1,message.getSender_time());
            pstmt.setString(2,message.getSender_id());
            pstmt.setString(3,message.getGroup_id());
            pstmt.setInt(4,message.getChat_id());
            pstmt.setString(5,message.getContent());
            pstmt.executeUpdate();

        }catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
            return false;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public synchronized List<Chating_item> get_UnprocessedMessages(String id, int if_first_time){
        List<Chating_item> chatingUsers = new ArrayList<Chating_item>();
        try {

            String sql = "CALL GetMessages1(?,?);";
//                    "select user_inf.*,chat_id,content_char2,unhandled_count,birth_time from qq_cilents.unprocessed_messages " +
//                    "inner join user_inf on unprocessed_messages.content_char1 = user_inf.id " +
//                    "inner join relationships ON id1 = unprocessed_messages.content_char1 AND id2 = unprocessed_messages.id " +
//                    "where unprocessed_messages.id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,id);
            pstmt.setInt(2,if_first_time);
            ResultSet res = pstmt.executeQuery();
            while(res.next()){
                String id1 = res.getString("id");
                String name = res.getString("name");
                String newest_message = res.getString("content_char2");
                byte[] profile = res.getBytes("profile_photo");
                int chat_id = res.getInt("chat_id");
                int unhandled_count = res.getInt("unhandled_count");
                String birth_time = res.getString("birth_time");
                int type = res.getInt("unprocessed_type");
                chatingUsers.add(new Chating_item(id1,name,birth_time,chat_id,profile,type,newest_message,
                                                  unhandled_count,null));
                System.out.println(id1 + "," + name);
            }

            sql = "CALL GetMessages2(?,?);";
//                    "select user_inf.*,chat_id,content_char2,unhandled_count,birth_time from qq_cilents.unprocessed_messages " +
//                    "inner join user_inf on unprocessed_messages.content_char1 = user_inf.id " +
//                    "inner join relationships ON id1 = unprocessed_messages.content_char1 AND id2 = unprocessed_messages.id " +
//                    "where unprocessed_messages.id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,id);
            pstmt.setInt(2,if_first_time);
            ResultSet res2 = pstmt.executeQuery();
            while(res2.next()){
                String id1 = res2.getString("group_id");
                String name = res2.getString("group_name");
                String newest_message = res2.getString("content_char2");
                String sender_nickname = res2.getString("name");
                byte[] profile = res2.getBytes("profile");
                int chat_id = res2.getInt("chat_id");
                int unhandled_count = res2.getInt("unhandled_count");
                String birth_time = res2.getString("birth_time");
                int type = res2.getInt("unprocessed_type");
                chatingUsers.add(new Chating_item(id1,name,birth_time,chat_id,profile,type,newest_message,
                        unhandled_count,sender_nickname));
                System.out.println(id1 + "," + name);
            }

            //请求一次过后把updated置零

            sql = "UPDATE relationships" +
                    " SET updated = '0'" +
                    " WHERE relationships.id1 = ?;";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,id);
            pstmt.executeUpdate();

            sql = "UPDATE groups_members" +
                    " SET updated = '0'" +
                    " WHERE groups_members.member_id = ?;";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,id);
            pstmt.executeUpdate();

            res.close();
            res2.close();

            if(chatingUsers.isEmpty()) return null;

        } catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }

        // 根据 birth_time 进行降序排序
        chatingUsers.sort(Comparator.comparing(Chating_item::getBirth_time).reversed());
        return chatingUsers;
    }
    public synchronized List<Request_User> get_Requests(String id){
        List<Request_User> requestUsers = new ArrayList<Request_User>();
        try {

            String sql = "SELECT * FROM requests INNER JOIN user_inf ON user_inf.`id` = requests.request_from" +
                         " WHERE requests.id = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,id);
            ResultSet res = pstmt.executeQuery();
            while(res.next()){
                String request_id = res.getString("request_from");
                String request_to_id = res.getString("request_to");
                String  nickname= res.getString("name");
                String time = res.getString("registration_time");
                String sex = res.getString("sex");
                String signature = res.getString("signature");
                byte[] profile = res.getBytes("profile_photo");
                String hello_words = res.getString("hello_words");
                String request_time = res.getString("request_time");
                String if_handled = res.getString("if_handled");
                String type = res.getString("type");
                requestUsers.add(new Request_User(request_id,nickname,sex,time,profile,signature,
                        hello_words,request_time,type,request_id,if_handled,request_to_id));
            }

            res.close();

            if(requestUsers.isEmpty()) return null;

        } catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return requestUsers;
    }


    public boolean add_TounprocessedMessages(String id, Chating_item chating_item, String send_date){

//        System.out.println(message.getContent());

        
//添加逻辑：若已经有相同记录则不再添加
        try {

            String sql1 = "INSERT INTO qq_cilents.unprocessed_messages(unprocessed_type,content_char1,birth_time,id)" +
                    "VALUES(?,?,?,?);";
            PreparedStatement pstmt = con.prepareStatement(sql1);
            pstmt.setInt(1,1);
            pstmt.setString(2, chating_item.getUserID());
            pstmt.setString(3,send_date);
            pstmt.setString(4,id);
            pstmt.executeUpdate();

        }catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
            return false;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public synchronized List<User> fuzzy_search_users(String nickname_part){
        List<User> users = new ArrayList<>();
        try {

            String sql = "SELECT * FROM user_inf WHERE name LIKE ?;";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "%" + nickname_part + "%");
            ResultSet res = pstmt.executeQuery();
            while(res.next()){
                String id1 = res.getString("id");
                String name = res.getString("name");
                String time = res.getString("registration_time");
                String sex = res.getString("sex");
                byte[] profile = res.getBytes("profile_photo");
                String signature1 = res.getString("signature");

                users.add(new User(id1,name,time,sex,profile,signature1));
            }

            res.close();

            if(users.isEmpty()) return null;

        } catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return users;
    }
    public synchronized List<User> accurate_search_users(String id){
        List<User> users = new ArrayList<>();
        try {

            String sql = "SELECT * FROM user_inf WHERE id = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet res = pstmt.executeQuery();
            while(res.next()){
                String id1 = res.getString("id");
                String name = res.getString("name");
                String time = res.getString("registration_time");
                String sex = res.getString("sex");
                byte[] profile = res.getBytes("profile_photo");
                String signature1 = res.getString("signature");

                users.add(new User(id1,name,time,sex,profile,signature1));
            }

            res.close();

            if(users.isEmpty()) return null;

        } catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return users;
    }
    public synchronized List<User> fuzzy_search_groups(String id){
        List<User> users = new ArrayList<>();
        try {

            String sql = "SELECT * FROM groups_inf WHERE group_name LIKE ?;";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "%" + id + "%");
            ResultSet res1 = pstmt.executeQuery();
            while(res1.next()){
                User temp = new User();
                temp.setUserID(res1.getString("group_id"));
                temp.setNick_name(res1.getString("group_name"));
                temp.setProfile(res1.getBytes("profile"));
                temp.setSignature(res1.getString("lead_words"));
                users.add(temp);
            }

            res1.close();

            if(users.isEmpty()) return null;

        } catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public synchronized List<User> accurate_search_groups(String id){
        List<User> users = new ArrayList<>();
        try {

            String sql = "SELECT * FROM groups_inf WHERE group_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet res = pstmt.executeQuery();
            while(res.next()){
                User temp = new User();
                temp.setUserID(res.getString("group_id"));
                temp.setNick_name(res.getString("group_name"));
                temp.setProfile(res.getBytes("profile"));
                temp.setSignature(res.getString("lead_words"));
                users.add(temp);
            }

            res.close();

            if(users.isEmpty()) return null;

        } catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return users;
    }
    public synchronized void  updata_unprocessed_messages(Chat_Message chat_message,int flag){
        String chating_friend = chat_message.getSender_id();
        String newest_message = chat_message.getContent();

        System.out.println("最新消息：  " + newest_message);

        String send_time = chat_message.getSender_time();
        String id = chat_message.getRec_id();

        if(flag == 0){
            String temp = id;
            id = chating_friend;
            chating_friend = temp;
        }
        try {

            String sql = "CALL Update_Unprocessed_Message(?,?,?,?,?);";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, chating_friend);
            pstmt.setString(2, newest_message);
            pstmt.setString(3, send_time);
            pstmt.setString(4, id);
            pstmt.setInt(5, flag);//flag表示更新的消息是否需要更新小红点，若是发送方(flag=0)，则count置零，接收方count加一
            pstmt.executeUpdate();

        } catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void updata_unprocessed_group_messages(Chat_Message chat_message){
        String sender = chat_message.getSender_id();
        String newest_message = chat_message.getContent();
        String groupId = chat_message.getGroup_id();

        String send_time = chat_message.getSender_time();
        //String recver = chat_message.getRec_id();

        try {

            String sql = "CALL Update_Unprocessed_Message_forGroups(?,?,?,?);";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, groupId);
            pstmt.setString(2, newest_message);
            pstmt.setString(3, send_time);
            pstmt.setString(4, sender);
            pstmt.executeUpdate();

        } catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public synchronized User updata_profile(String id, byte[] img,String signature,String nickname){
        User user1 = new User();
        try {
            String sql = "UPDATE user_inf SET profile_photo = ? ,signature = ? ,name = ? WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            if(img == null){
                sql = "UPDATE user_inf SET signature = ? ,name = ? WHERE id = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, signature);
                pstmt.setString(2, nickname);
                pstmt.setString(3, id);
                pstmt.executeUpdate();
            }
            else{
                pstmt.setBytes(1, img);
                pstmt.setString(2, signature);
                pstmt.setString(3, nickname);
                pstmt.setString(4, id);
                pstmt.executeUpdate();
            }

            sql = "UPDATE relationships SET updated = ? WHERE id2 = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "1");
            pstmt.setString(2, id);
            pstmt.executeUpdate();

            sql = "SELECT * FROM user_inf WHERE id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet res = pstmt.executeQuery();

            while(res.next()){
                String id1 = res.getString("id");
                String nickname1 = res.getString("name");
                String sex = res.getString("sex");
                String start_time = res.getString("registration_time");
                byte[] profile = res.getBytes("profile_photo");
                String signature1 = res.getString("signature");

                user1 = new User(id1,nickname1,start_time,sex,profile,signature1);
            }

            res.close();

        } catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return user1;
    }
    public synchronized boolean request_a_friend(String id,String request_id,String request_time,String hello_words){
        boolean flag = true;
        String sql = "call Request_Friend_Procedure (?,?,?,?,?)";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, request_id);
            pstmt.setString(2, id);
            pstmt.setString(3, request_id);
            pstmt.setString(4, request_time);
            pstmt.setString(5, hello_words);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            flag = false;
            throw new RuntimeException(e);
        }finally {
            return flag;
        }

    }
    public synchronized void add_friend_success(String request_from, String request_to, String response_time) {
        //当表位空时，max为0
        String preSql = "SELECT count(*) FROM relationships WHERE id1 = ? and id2 = ?";
        String getMaxChatIdSql = "SELECT COALESCE(MAX(chat_id), 0) + 1 AS next_chat_id FROM relationships";
        String insertSql = "INSERT INTO relationships VALUES (?, ?, ?, ?, ?)";

        try {
            //防止重复响应
            PreparedStatement Stmt1 = con.prepareStatement(preSql);
            Stmt1.setString(1, request_to);
            Stmt1.setString(2, request_from);
            ResultSet rs0 = Stmt1.executeQuery();
            if (rs0.next()) {
                int count = rs0.getInt("count(*)");
                if(count > 0) return;
            }
            // 获取当前最大 chat_id
            PreparedStatement getMaxChatIdStmt = con.prepareStatement(getMaxChatIdSql);
            ResultSet rs = getMaxChatIdStmt.executeQuery();
            int nextChatId = 1; // 默认 chat_id 为 1，如果没有记录

            if (rs.next()) {
                nextChatId = rs.getInt("next_chat_id");
            }

            // 插入新的关系记录
            PreparedStatement insertStmt = con.prepareStatement(insertSql);
            insertStmt.setString(1, request_from);
            insertStmt.setString(2, request_to);
            insertStmt.setString(3, response_time);
            insertStmt.setInt(4, nextChatId);
            insertStmt.setString(5, "1");
            insertStmt.executeUpdate();

            // 反向插入新的关系记录
            insertStmt.setString(1, request_to);
            insertStmt.setString(2, request_from);
            insertStmt.setString(3, response_time);
            insertStmt.setInt(4, nextChatId);
            insertStmt.setString(5, "1");
            insertStmt.executeUpdate();

            String sql = "CALL Update_Unprocessed_Message(?,?,?,?,1);";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, request_to);
            pstmt.setString(2, "我们已经是好友了，开始聊天吧！");
            pstmt.setString(3, response_time);
            pstmt.setString(4, request_from);
            pstmt.executeUpdate();

            pstmt.setString(4, request_to);
            pstmt.setString(2, "我们已经是好友了，开始聊天吧！");
            pstmt.setString(3, response_time);
            pstmt.setString(1, request_from);
            pstmt.executeUpdate();

            //删除当初请求的那条表项，新增一条同意的表项
            sql = "DELETE FROM requests WHERE id= ? AND request_from = ? AND request_to = ? AND type = 'request'";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, request_to);
            pstmt.setString(2, request_from);
            pstmt.setString(3, request_to);
            pstmt.executeUpdate();

            sql = "INSERT INTO requests VALUES(?,?,?,?,'已同意好友申请。','0','response_agree');";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, request_to);
            pstmt.setString(2, request_from);
            pstmt.setString(3, request_to);
            pstmt.setString(4, response_time);
            pstmt.executeUpdate();

            pstmt.setString(1, request_from);
            pstmt.setString(3, request_to);
            pstmt.setString(2, request_from);
            pstmt.setString(4, response_time);
            pstmt.executeUpdate();

            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void  handle_a_message(String id,String friend_id){
        try {

            String sql = "UPDATE unprocessed_messages " +
                    "SET unhandled_count = 0 " +
                    "WHERE id = ? AND content_char1 = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, friend_id);
            pstmt.executeUpdate();

        } catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public synchronized List<String> get_friends_id(String id){
        List<String> users = new ArrayList<>();
        ResultSet res = null;
        try {

            String sql1 = "SELECT id2 " +
                    "FROM relationships " +
                    "WHERE id1 = ?";
            PreparedStatement pstmt = con.prepareStatement(sql1);
            pstmt.setString(1, id);
            res = pstmt.executeQuery();

            while (res.next()) {
                String id2 = res.getString("id2");
                users.add(id2);
            }
        } catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                res.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return users;
    }

    public synchronized User get_a_user(String id){
        ResultSet res1 = null;
        User user = new User();
        try {

            String sql1 = "SELECT *" +
                    "FROM user_inf " +
                    "WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql1);
            pstmt.setString(1, id);
            res1 = pstmt.executeQuery();

            while (res1.next()) {
                user.setnick_name(res1.getString("name"));
                user.setsex(res1.getString("sex"));
                user.setUserID(res1.getString("id"));
                user.setstart_time(res1.getString("registration_time"));
                user.setSignature(res1.getString("signature"));
                user.setProfile(res1.getBytes("profile_photo"));
            }
        } catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                res1.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }
    public synchronized List<Group> get_groups_list(String id) {
        //User[] users = null;
        List<Group> groups = new ArrayList<>();
        ResultSet res = null;
        try {

            String sql1 = "SELECT groups_members.*,groups_inf.* " +
                    "FROM groups_members " +
                    "INNER JOIN groups_inf ON groups_inf.group_id = groups_members.group_id " +
                    "WHERE groups_members.member_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql1);
            pstmt.setString(1, id);
            res = pstmt.executeQuery();

            while (res.next()) {
                String group_id = res.getString("group_id");
                String start_time = res.getString("start_time");
                int chat_id = res.getInt("chat_id");
                String group_name = res.getString("group_name");
                String signature = res.getString("lead_words");
                byte[] profile = res.getBytes("profile");
                String if_master = res.getString("is_master");
                String if_admin = res.getString("is_administrator");

                System.out.println("群聊名称："  + group_name);
                Group temp = new Group(group_id,group_name,start_time,profile,if_master,if_admin,signature,chat_id);
                groups.add(temp);
            }
        } catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                res.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return groups;
    }

    public synchronized List<String> get_group_members_ID(String id) {
        List<String> userIDs = new ArrayList<>();
        ResultSet res1 = null;
        try {

            String sql1 = "SELECT groups_members.member_id " +
                    "FROM groups_members " +
                    "WHERE groups_members.group_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql1);
            pstmt.setString(1, id);
            res1 = pstmt.executeQuery();

            while (res1.next()) {

                userIDs.add(res1.getString("member_id"));
            }
        } catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                res1.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return userIDs;
    }

    public synchronized List<User> get_group_members(String id) {
        List<User> users = new ArrayList<>();
        ResultSet res1 = null;
        try {

            String sql1 = "SELECT groups_members.*,user_inf.* " +
                    "FROM groups_members " +
                    "INNER JOIN user_inf ON user_inf.id = groups_members.member_id " +
                    "WHERE groups_members.group_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql1);
            pstmt.setString(1, id);
            res1 = pstmt.executeQuery();

            while (res1.next()) {
                User user = new User();
                user.setnick_name(res1.getString("name"));
                user.setsex(res1.getString("sex"));
                user.setUserID(res1.getString("id"));
                user.setstart_time(res1.getString("registration_time"));
                user.setSignature(res1.getString("signature"));
                user.setProfile(res1.getBytes("profile_photo"));

                users.add(user);
            }
        } catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                res1.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return users;
    }

}
