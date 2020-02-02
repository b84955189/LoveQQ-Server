package loveqqserver.main;

import loveqqserver.config.OnlineUsers;
import loveqqserver.model.entity.OnlineUser;
import loveqqserver.threads.GlobalThread;
import loveqqserver.threads.GroupThread;
import loveqqserver.utils.JDBCUtils;
import loveqqserver.utils.UDPUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Jason
 * @version 1.0
 * @date 12/9/2019 10:40 AM
 * @describe Test Class
 */
public class Test {
    public static void main(String[] args) throws IOException {
//        LQActionControl lqActionControl=new LQActionControl();
//
//        List<LQUser> list=lqActionControl.queryAllUser();
//        for(LQUser lqUser:list){
//            System.out.println(lqUser);
//        }
        JDBCUtils.getConnectionInstance();
        //Login Thread
        new GlobalThread(UDPUtils.getReceiveSocket()).start();

        //Group Chat
        new GroupThread(UDPUtils.getServerSocket()).start();


       Timer onlineUserCheckTimer=new Timer();
       onlineUserCheckTimer.schedule(new TimerTask() {
           @Override
           public void run() {
               for(OnlineUser onlineUser:OnlineUsers.getUsers()){
                   onlineUser.setCheckSign(false);
               }
               //-----------Sleep 10s.
               try {
                   Thread.sleep(10000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               //------------------
               //Because it involves delete,so,choose to use Iterator.
               Iterator<OnlineUser> iterator=OnlineUsers.getUsers().iterator();
               while(iterator.hasNext()){
                   OnlineUser onlineUser=iterator.next();
                   if(!onlineUser.getCheckSign()){
                       //Pay Attention to it !!!!!!!!!
                      iterator.remove();
                      //-------test-------
                      System.out.println("删了！！！id:"+onlineUser.getUser_id());
                      //------------------
                   }
               }
           }
       },500,1000);




        //new MessageForwardThread(UDPUtils.getSendSocket()).start();

//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put(R.Configs.USER_ID_FIELD,1);
//        jsonObject.put(R.Configs.USER_LOGIN_FIELD,"admin");
//        jsonObject.put(R.Configs.USER_PASS_FIELD,"123456");
//        jsonObject.put(R.Configs.DISPLAY_NAME_FIELD,"听风");
//        System.out.println(jsonObject.toString());
//          List<LQUser> list=new ArrayList<LQUser>();
//          LQUser lqUser=new LQUser();
//          lqUser.setUser_id(1);
//          lqUser.setUser_login("admin");
//          lqUser.setUser_pass("123456");
//          lqUser.setDisplay_name("听风");
//          lqUser.setUser_head_url("http://storage.lking.top/14a338db84d7f71841d732ad387740d7.jpeg");
//          LQUser lqUser1=new LQUser();
//          lqUser1.setUser_id(1);
//          lqUser1.setUser_login("admin");
//          lqUser1.setUser_pass("123456");
//          lqUser1.setDisplay_name("听风");
//          lqUser1.setUser_head_url("http://storage.lking.top/14a338db84d7f71841d732ad387740d7.jpeg");
//
//        LQUser lqUser11=new LQUser();
//        lqUser11.setUser_id(1);
//        lqUser11.setUser_login("admin");
//        lqUser11.setUser_pass("123456");
//        lqUser11.setDisplay_name("听风");
//        lqUser11.setUser_head_url("http://storage.lking.top/14a338db84d7f71841d732ad387740d7.jpeg");
//
//          list.add(lqUser);
//          list.add(lqUser1);
//
//          JSONObject jsonObject1=new JSONObject();
//          jsonObject1.put("Result",200);
//          jsonObject1.put("Me",lqUser11);
//          jsonObject1.put("Friends",list);
//          jsonObject1.put("Tips","成功登录");

//          jsonObject1.put(R.Configs.USER_ID_FIELD,1);
//          jsonObject1.put(R.Configs.USER_LOGIN_FIELD,"admin");
//          jsonObject1.put(R.Configs.USER_PASS_FIELD,"123456");
//          jsonObject1.put(R.Configs.DISPLAY_NAME_FIELD,"听风");
//          jsonObject1.put(R.Configs.USER_HEAD_URL_FIELD,"http://storage.lking.top/14a338db84d7f71841d732ad387740d7.jpeg");


//          System.out.println(jsonObject1.toString());

//            LQActionControl lqActionControl=new LQActionControl();
//            LQUser lqUser=new LQUser();
//            lqUser.setUser_login("admintest");
//            lqUser.setUser_pass("123456");
//            //lqUser.setDisplay_name("小");
//            System.out.println(lqActionControl.addUser(lqUser));

    }
}
