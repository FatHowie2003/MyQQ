package com.example.my_qq_spring;

import com.example.my_qq_spring.services.Server_Services;
import com.example.my_qq_spring.socketAPI.SocketAPI;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@RestController
public class BasicController {

    Server_Services server_service = new Server_Services();
//
//    @GetMapping(value = "/if_login/{id}")
//    public Result is_login(@PathVariable String id){
//        Result res = new Result();
//
//        if(Logined_Users.check(id)){
//            res.setResponseType("LOGIN_SUCCESS");
//        }
//        else res.setResponseType("LOGIN_FAIL");
//        return res;
//    }
//    @PostMapping(value = "/login")
//    public Result login(@RequestBody Pkt user, HttpServletRequest request){
//        Result res = new Result();
//        String hostIp = request.getRemoteAddr();
//
//        System.out.println(user.getId() + "和" + user.getPassword());
//        User temp = server_service.check(user.getId(),user.getPassword());
//        if(temp != null){
//            List<User> request_user = new ArrayList<>();
//            request_user.add(temp);
//            res.setUsers(request_user);
//            res.setLogin_success("LOGIN_SUCCESS");
//            Logined_Users.add(user.getId(),hostIp);
//            System.out.println(hostIp);
//        }
//        else res.setLogin_success("LOGIN_FAIL");
//        return res;
//    }
//
//    @GetMapping(value = "/getfriends/{id}")
//    public Result get_friendslist(@PathVariable String id){
//        Result res = new Result();
//        res.setLogin_success("LOGIN_SUCEESS");
//        if(!Logined_Users.check(id)){
//            Logined_Users.delete(id);
//            res.setLogin_success("LOGIN_FAIL");
//        }
//        else{
//            System.out.println(id);
//            res.setChatingUsers(server_service.get_friends_list(id));
//        }
//        return res;
//    }
//
//    @GetMapping(value = "/getmessage/{id}/{chatid}")
//    public Result get_messages(@PathVariable String id, @PathVariable String chatid){
//        Result res = new Result();
//        res.setLogin_success("LOGIN_SUCEESS");
//        if(!Logined_Users.check(id)){
//            Logined_Users.delete(id);
//            res.setLogin_success("LOGIN_FAIL");
//        }
//        else{
//            res.setChat_messages(server_service.get_messages(chatid));
//        }
//        return res;
//    }
//
//    @PostMapping(value = "/sendmessage")
//    public Result recv_message(@RequestBody Pkt user, HttpServletRequest request){
//        Result res = new Result();
//        String sender_id = user.getId();
//        String recv_id = user.getChat_message().getRec_id();
//        res.setLogin_success("LOGIN_SUCEESS");
//        boolean insert_success = false;
//
//        if(!Logined_Users.check(sender_id)){
//            Logined_Users.delete(sender_id);
//            res.setLogin_success("LOGIN_FAIL");
//        }
//        else{
//           // System.out.println("链接过来了！！！！");
//            insert_success = server_service.recv_message(user.getChat_message());
//            server_service.updata_unprocessed_messages(user.getChat_message());
//            res.setSend_success(insert_success);
//        }
//        return res;
//
//    }
//    @GetMapping(value = "/getUnprocessedMessages/{id}/{if_first_time}")
//    public Result get_unprocessedmessages(@PathVariable String id,@PathVariable int if_first_time){
//        Result res = new Result();
//        res.setLogin_success("LOGIN_SUCEESS");
//        if(!Logined_Users.check(id)){
//            Logined_Users.delete(id);
//            res.setLogin_success("LOGIN_FAIL");
//        }
//        else{
//            res.setUnprocessed_message(new Unprocessed_Message(server_service.get_UnprocessedMessages(id,if_first_time),
//                                       server_service.get_Requests(id)));
//            //System.out.println("有东西");
//        }
//        return res;
//    }
    @PostMapping(value = "/fuzzyResearchUsers")
    public Result fuzzyResearchUsers(@RequestBody Pkt nickname_part){
        Result res = new Result();
        List<User> users;
        if(SocketAPI.LoginMap.containsKey(nickname_part.getId())){
            users = server_service.fuzzy_search_users(nickname_part.getNickname_part());
            res.setResearch_users(users);
        }
        return res;
    }
    @PostMapping(value = "/accurateResearchUsers")
    public Result accurateResearchUsers(@RequestBody Pkt pkt){
        Result res = new Result();
        List<User> users;
        if(SocketAPI.LoginMap.containsKey(pkt.getId())){
            users = server_service.accurate_search_users(pkt.getNickname_part());
            if(users != null){
                res.setResearch_users(users);
            }

        }
        return res;
    }
    @PostMapping(value = "/fuzzyResearchGroups")
    public Result fuzzyResearchGroups(@RequestBody Pkt nickname_part){
        Result res = new Result();
        List<User> users;
        if(SocketAPI.LoginMap.containsKey(nickname_part.getId())){
            users = server_service.fuzzy_search_groups(nickname_part.getNickname_part());
            res.setResearch_users(users);
        }
        return res;
    }
    @PostMapping(value = "/accurateResearchGroups")
    public Result accurateResearchGroups(@RequestBody Pkt pkt){
        Result res = new Result();
        List<User> users;
        if(SocketAPI.LoginMap.containsKey(pkt.getId())){
            users = server_service.accurate_search_groups(pkt.getNickname_part());
            if(users != null){
                res.setResearch_users(users);
            }

        }
        return res;
    }
    @GetMapping(value = "/request_group_members/{userID}/{groupID}")
    public Result request_group_members(@PathVariable String userID,@PathVariable String groupID){
        Result res = new Result();
        List<User> users;
        if(SocketAPI.LoginMap.containsKey(userID)){
            users = server_service.get_group_members(groupID);
            if(users != null){
                res.setResearch_users(users);
            }

        }
        return res;
    }
//    @PostMapping(value = "/updateInfo")
//    public Result updateInfo(@RequestParam("id") String id,
//                             @RequestParam("nickname_part") String nickname,
//                             @RequestParam("signature") String signature,
//                             @RequestParam(value = "img", required = false) MultipartFile file) {
//        Result res = new Result();
//        res.setLogin_success("LOGIN_SUCEESS");
//        if(!Logined_Users.check(id)){
//            Logined_Users.delete(id);
//            res.setLogin_success("LOGIN_FAIL");
//        }
//        try {
//            System.out.println(id + "请求修改信息！");
//            byte[] imageBytes = file.getBytes();
//            if (imageBytes != null) {
//                List<User> user = new ArrayList<>();
//                user.add(server_service.updata_profile(id,imageBytes,signature,nickname));
//                System.out.println("修改前的信息：" + nickname + " 修改后的信息：" + user.get(0).getNick_name());
//                res.setUsers(user);
//                res.setSend_success(true);
//            }
//        } catch (IOException e) {
//            System.out.println("头像更新错误！！");
//            res.setSend_success(false);
//        }
//        return res;
//    }
//    @PostMapping(value = "/request_add")
//    public Result request_add(@RequestParam("id") String id,
//                             @RequestParam("request_id") String request_id,
//                             @RequestParam("hello_words") String hello_words,
//                              @RequestParam("request_time") String request_time) {
//        System.out.println("请求时间：" + request_time + ",请求语：" + hello_words);
//        Result res = new Result();
//        res.setLogin_success("LOGIN_SUCEESS");
//        if(!Logined_Users.check(id)){
//            Logined_Users.delete(id);
//            res.setLogin_success("LOGIN_FAIL");
//        }
//        else{
//            server_service.request_a_friend(id,request_id,request_time,hello_words);
//            res.setSend_success(true);
//        }
//        return res;
//    }
//
//    @PostMapping(value = "/response_add")
//    public Result response_add(@RequestParam("response_time") String response_time,
//                              @RequestParam("request_from") String request_from,
//                              @RequestParam("request_to") String request_to,
//                              @RequestParam("request_res") String request_res) {
//        Result res = new Result();
//        res.setLogin_success("LOGIN_SUCEESS");
//        if(!Logined_Users.check(request_to)){
//            Logined_Users.delete(request_to);
//            res.setLogin_success("LOGIN_FAIL");
//        }
//        else{
//            if(request_res.equals("agree")){
//                server_service.add_friend_success(request_from,request_to,response_time);
//            }
//            else{
//                //server_service.add_friend_fail(request_from,request_to,response_time);
//            }
//
//            res.setSend_success(true);
//        }
//        return res;
//    }
    @PostMapping(value = "/handle_a_message/{id}/{friend_id}")
    public Result handle_a_message(@PathVariable String id,@PathVariable String friend_id) {
        Result res = new Result();
        res.setSend_success(false);
        if(SocketAPI.LoginMap.containsKey(id)){
            server_service.handle_a_message(id,friend_id);
            res.setSend_success(true);
        }
        return res;
    }
//
//
//
//
////    // http://127.0.0.1:8080/save_user?name=newName&age=11
////    @RequestMapping("/save_user")
////    @ResponseBody
////    public String saveUser(User u) {
////        return "user will save: name=" + u.getName() + ", age=" + u.getAge();
////    }
////
////    // http://127.0.0.1:8080/html
////    @RequestMapping("/html")
////    public String html() {
////        return "index.html";
////    }
////
////    @ModelAttribute
////    public void parseUser(@RequestParam(name = "name", defaultValue = "unknown user") String name
////            , @RequestParam(name = "age", defaultValue = "12") Integer age, User user) {
////        user.setName("zhangsan");
////        user.setAge(18);
////    }
}

//
class Pkt implements Serializable {
    private String id;
    private String password;
    private Chat_Message chat_message;
    private MultipartFile message_img;
    private String signature;
    private String nickname_part;


    public Chat_Message getChat_message() {
        return chat_message;
    }

    public void setChat_message(Chat_Message chat_message) {
        this.chat_message = chat_message;
    }

    public MultipartFile getMessage_img() {
        return message_img;
    }

    public void setMessage_img(MultipartFile message_img) {
        this.message_img = message_img;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname_part() {
        return nickname_part;
    }

    public void setNickname_part(String nickname_part) {
        this.nickname_part = nickname_part;
    }
}
