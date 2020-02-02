package loveqqserver.threads.runnables;

import loveqqserver.config.OnlineUsers;
import loveqqserver.config.R;
import loveqqserver.control.LQActionControl;
import loveqqserver.model.entity.AccountInformation;
import loveqqserver.model.entity.LQUser;
import loveqqserver.model.entity.OnlineUser;
import loveqqserver.utils.UDPUtils;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jason
 * @version 1.0
 * @date 12/19/2019 9:04 AM
 * @describe:
 */
public class OperationRunnable implements Runnable{
    private DatagramSocket socket;
    private DatagramPacket packet;

    LQActionControl actionControl;

    public OperationRunnable(DatagramPacket packet,DatagramSocket socket){
        super();
        this.socket=socket;
        this.packet=packet;
        actionControl=new LQActionControl();
    }
    @Override
    public void run() {
        try {
            String tempStr = new String(packet.getData(), 0, packet.getLength(), R.Configs.DEFAULT_CHARSET);
            JSONObject jsonData=JSONObject.fromObject(tempStr);
            switch (jsonData.getInt(R.JSONS.TYPE_JSON_FIELD)){
                case R.JSONS.LOGIN_REQUEST_TYPE_VALUE:{
                    String tempAccount=jsonData.getString(R.JSONS.ACCOUNT_JSON_FIELD);
                    String tempPassword=jsonData.getString(R.JSONS.PASSWORD_JSON_FIELD);

                    int tempPort=jsonData.getInt(R.JSONS.CLIENT_RECEIVE_PORT_FIELD);

                    //Account Information Object
                    AccountInformation accountInformation=new AccountInformation();
                    //Query Object
                    LQUser user=actionControl.queryOneUser(tempAccount);
                    if(user==null){
                        //return login failed
                        accountInformation.setResult(R.JSONS.FAILED_RESULT_VALUE);
                        accountInformation.setTips(R.Strings.ACCOUNT_FAILED_LOGIN);

                        //test
                        System.out.println("没有此用户！");
                        //Send Result To Client.
                        UDPUtils.sendUDPPacket(JSONObject.fromObject(accountInformation),packet.getAddress(),packet.getPort(),R.Configs.LOGIN_SEND_TYPE);
                    }else if(!user.getUser_pass().equals(tempPassword)){
                        //return login failed
                        accountInformation.setResult(R.JSONS.FAILED_RESULT_VALUE);
                        accountInformation.setTips(R.Strings.PASSWORD_FAILED_LOGIN);

                        //test
                        System.out.println("密码错误！");
                        //Send Result To Client.
                        UDPUtils.sendUDPPacket(JSONObject.fromObject(accountInformation),packet.getAddress(),packet.getPort(),R.Configs.LOGIN_SEND_TYPE);
                    }else{

                        OnlineUser onlineUser=OnlineUsers.isAlreadyOnline(user.getUser_id());
                        //Verify success ,but user already login.
                        if(onlineUser!=null){
                            //return login failed
                            accountInformation.setResult(R.JSONS.FAILED_RESULT_VALUE);
                            accountInformation.setTips(R.Strings.ALREADY_LOGIN_FAILED_LOGIN);
                            //Send Result To Client.
                            UDPUtils.sendUDPPacket(JSONObject.fromObject(accountInformation),packet.getAddress(),packet.getPort(),R.Configs.LOGIN_SEND_TYPE);
                        }else{
                            //login success return user detail information
                            accountInformation.setMe(user);
                            accountInformation.setTips(R.Strings.SUCCESS_LOGIN);
                            accountInformation.setResult(R.JSONS.SUCCESS_RESULT_VALUE);
                            accountInformation.setFriends(actionControl.queryFriends(user.getUser_id()));
                            //test
                            for(LQUser temp:accountInformation.getFriends()){
                                System.out.println(temp);
                            }
                            //test
                            System.out.println("新增了一个用户id："+user.getUser_id()+"   "+packet.getAddress()+"    "+tempPort);

                            OnlineUser thisUser=new OnlineUser(user.getUser_id(),packet.getAddress(),tempPort);
                            OnlineUsers.add(thisUser);

                            //----------test----------

                            //-----------------------
                            //test
                            System.out.println("登录成功！"+tempAccount+thisUser);
                            //Send Result To Client.
                            UDPUtils.sendUDPPacket(JSONObject.fromObject(accountInformation),packet.getAddress(),packet.getPort(),R.Configs.LOGIN_SEND_TYPE);

                            //---------test------
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            //-------------------
                            //Read Message
                            BufferedReader bufferedReader=null;
                            File tempMessageFile = new File("tempmessage\\user_" + thisUser.getUser_id() + ".data");
                            try {

                                if (tempMessageFile.exists()) {
                                    bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(tempMessageFile)));
                                    List<JSONObject> tempMessageDataList=new ArrayList<JSONObject>();
                                    String tempJsonStr=null;
                                    while ((tempJsonStr=bufferedReader.readLine())!=null){
                                        JSONObject tempJSONData=JSONObject.fromObject(tempJsonStr);
                                        tempMessageDataList.add(tempJSONData);

                                    }
                                    //
//                                    for(int i=tempMessageDataList.size()-1;i>=0;i--){
//                                        Thread.sleep(100);
//                                        UDPUtils.sendUDPPacket(tempMessageDataList.get(i),thisUser.getUser_address(),thisUser.getUser_port(),R.JSONS.MESSAGE_FORWARD_TYPE_VALUE);
//                                    }
                                    //
                                    for(int i=0;i<tempMessageDataList.size();i++){
                                        Thread.sleep(100);
                                        UDPUtils.sendUDPPacket(tempMessageDataList.get(i),thisUser.getUser_address(),thisUser.getUser_port(),R.JSONS.MESSAGE_FORWARD_TYPE_VALUE);
                                    }



                                }
                            }catch (Exception e){
                                //No Operation
                                e.printStackTrace();
                                //--test---
                                System.out.println("转发失败！");
                            }finally{
                                try{
                                    if(bufferedReader!=null){
                                        bufferedReader.close();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            tempMessageFile.delete();
                            //test
                            System.out.println("离线消息转发完成！");
                        }




                    }

                };break;
                case R.JSONS.MESSAGE_FORWARD_TYPE_VALUE:{
                    int tempToUserID=jsonData.getInt(R.JSONS.MESSAGE_TO_FIELD);

                    OnlineUser toUser=OnlineUsers.isAlreadyOnline(tempToUserID);
                    //System.out.println("转发测试！"+toUser);
                    //IF Already Online.
                    if(toUser!=null){
                        //System.out.println("转发目标地址为："+toUser.getUser_address()+"    "+toUser.getUser_port());
                        UDPUtils.sendUDPPacket(jsonData,toUser.getUser_address(),toUser.getUser_port(),R.JSONS.MESSAGE_FORWARD_TYPE_VALUE);

                    }else{
                        //----test---
                        System.out.println("对方不在线！正在云存储消息！");
                        //Write to File And Wait the User Online.
                        BufferedWriter bufferedWriter=null;
                        try {
                            File tempMessageFile = new File("tempmessage\\user_" + tempToUserID + ".data");
                            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempMessageFile,true)));

                            bufferedWriter.write(jsonData.toString());
                            bufferedWriter.newLine();



                        }catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            try {
                                if(bufferedWriter!=null)
                                    bufferedWriter.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put(R.JSONS.RESULT_JSON_FIELD,R.JSONS.SUCCESS_RESULT_VALUE);
                    jsonObject.put(R.JSONS.TYPE_JSON_FIELD,R.JSONS.MESSAGE_FORWARD_TYPE_VALUE);

                    //Send Result To Client.
                    UDPUtils.sendUDPPacket(jsonObject,packet.getAddress(),packet.getPort(),R.Configs.MESSAGE_FORWARD_SEND_TYPE);

                };break;
                case R.JSONS.LOGOUT_REQUEST_TYPE_VALUE:{
                        int temp_id=jsonData.getInt(R.JSONS.MESSAGE_FROM_FIELD);
                        OnlineUsers.del(temp_id);
                };break;
                case R.JSONS.CHECK_USER_IS_ONLINE:{
                    int formID=jsonData.getInt(R.JSONS.MESSAGE_FROM_FIELD);
                    for(OnlineUser onlineUser:OnlineUsers.getUsers()){
                        if(onlineUser.getUser_id()==formID){
                            onlineUser.setCheckSign(true);
                        }
                    }
                };break;
                case R.JSONS.SEARCH_FRIEND_TYPE_VALUE:{
                    String tempAccount=jsonData.getString(R.JSONS.MESSAGE_TO_FIELD);
                    String tempAccount1=jsonData.getString(R.JSONS.MESSAGE_FROM_FIELD);
                    LQUser lqUser=actionControl.queryOneUser(tempAccount);
                    LQUser lqUser1=actionControl.queryOneUser(tempAccount1);
                    JSONObject jsonObject=new JSONObject();

                    if(lqUser!=null){

                        //Set up Friend Relative
                        boolean resultSign=actionControl.setUpFriendRelative(lqUser1.getUser_id(),lqUser.getUser_id());

                        if(resultSign){
                            //---------Request Client
                            //Message
                            jsonObject.put(R.JSONS.RESULT_JSON_FIELD,R.JSONS.SUCCESS_RESULT_VALUE);
                            jsonObject.put(R.JSONS.TIPS_JSON_FIELD,"成功查询！");
                            jsonObject.put(R.JSONS.FRIEND_JSON_FIELD,lqUser);


                            //---------Response Client


                            if(lqUser1!=null){

                                OnlineUser onlineUser1=OnlineUsers.isAlreadyOnline(lqUser.getUser_id());
                                if(onlineUser1!=null){
                                    JSONObject jsonObject2=new JSONObject();
                                    jsonObject2.put(R.JSONS.TYPE_JSON_FIELD,R.JSONS.ADD_FRIEND_TYPE_VALUE);
                                    jsonObject2.put(R.JSONS.FRIEND_JSON_FIELD,lqUser1);
                                    UDPUtils.sendUDPPacket(jsonObject2,onlineUser1.getUser_address(),onlineUser1.getUser_port(),R.Configs.ADD_FRIEND_TYPE);
                                }

                            }

                        }else{
                            //Message
                            jsonObject.put(R.JSONS.RESULT_JSON_FIELD,R.JSONS.FAILED_RESULT_VALUE);
                            jsonObject.put(R.JSONS.TIPS_JSON_FIELD,"已添加！");
                            jsonObject.put(R.JSONS.FRIEND_JSON_FIELD,lqUser);
                        }



                    }else{
                        jsonObject.put(R.JSONS.RESULT_JSON_FIELD,R.JSONS.FAILED_RESULT_VALUE);
                        jsonObject.put(R.JSONS.TIPS_JSON_FIELD,"查询失败！");
                        jsonObject.put(R.JSONS.FRIEND_JSON_FIELD,null);
                    }
                    UDPUtils.sendUDPPacket(jsonObject,packet.getAddress(),packet.getPort(),R.Configs.SEARCH_FRIEND_TYPE);

                };break;
                case R.JSONS.HOLE_UDP_TEST_PACKET:{
                    //No Operation.
                    //---------test-------
                    //System.out.println("打洞成功！！！"+packet.getPort()+","+packet.getAddress().getHostAddress());
                };break;

            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
