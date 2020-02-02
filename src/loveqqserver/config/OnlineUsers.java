package loveqqserver.config;


import loveqqserver.control.LQActionControl;
import loveqqserver.model.entity.OnlineUser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jason
 * @version 1.0
 * @date 12/25/2019 3:10 PM
 * @describe:
 */
public class OnlineUsers {

    private static List<OnlineUser> users;
    private static LQActionControl lqActionControl;
    static{
        users=new ArrayList<OnlineUser>();
        lqActionControl=new LQActionControl();
    }

    public static List<OnlineUser> getUsers() {
        return users;
    }
    public static void add(OnlineUser user){
        //Check This User Whether Have Message.
        if(isAlreadyOnline(user.getUser_id())!=null)
            del(user.getUser_id());

        users.add(user);
        lqActionControl.changeOnlineSign(user.getUser_id(),R.SQL.ONLINE_SIGN_ON);
    }
    public static void del(int id){
        for(int i=0;i<users.size();i++){
            OnlineUser temp=users.get(i);
            if(temp.getUser_id()==id){
                users.remove(i);

                lqActionControl.changeOnlineSign(temp.getUser_id(),R.SQL.ONLINE_SIGN_OFF);

                break;
            }
        }
    }
    public static OnlineUser isAlreadyOnline(int id){
        for(OnlineUser temp:users){
            if(temp.getUser_id()==id){
                return temp;
            }
        }
        return null;
    }





}
