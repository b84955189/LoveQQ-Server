package loveqqserver.main;

import loveqqserver.config.R;
import loveqqserver.model.entity.AccountInformation;
import loveqqserver.model.entity.LQUser;
import net.sf.json.JSONObject;

/**
 * @author Jason
 * @version 1.0
 * @date 12/9/2019 10:39 AM
 * @describe The program entry Class
 */
public class MainTest {
    public static void main(String[] args){
        //test
        LQUser lqUser1=new LQUser();
        JSONObject jsonObject2=new JSONObject();
        jsonObject2.put(R.JSONS.TYPE_JSON_FIELD,R.JSONS.ADD_FRIEND_TYPE_VALUE);
        jsonObject2.put(R.JSONS.FRIEND_JSON_FIELD,lqUser1);
        System.out.println(jsonObject2.toString());
    }
}
