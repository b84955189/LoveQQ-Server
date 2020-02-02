package loveqqserver.config;

import sun.security.krb5.Config;

/**
 * @author Jason
 * @version 1.0
 * @date 12/9/2019 10:53 AM
 * @describe Global Resource Class
 */
public class R {

    public interface Strings{
        //Login Return State
        String SUCCESS_LOGIN="成功登录！";
        String ACCOUNT_FAILED_LOGIN="账户不存在！";
        String PASSWORD_FAILED_LOGIN="密码错误！";
        String ALREADY_LOGIN_FAILED_LOGIN="已经登录！";
    }
    public interface Configs{
        //Global Port
        int LOGIN_PORT=54199;
        int MESSAGE_FORWARD_PORT=52121;
        //Global Charset
        String DEFAULT_CHARSET="UTF-8";
        //Message Buffer Size
        int MESSAGE_BUFFER_SIZE=10240;

        //UDP Send Type
        //UDP Login Type
        int LOGIN_SEND_TYPE=3456;
        //UDP Message Forward Type
        int MESSAGE_FORWARD_SEND_TYPE=321;

        int ADD_FRIEND_TYPE=43326;
        int SEARCH_FRIEND_TYPE=23454;

        //MySQL Config
        //=====================
        String MYSQL_ADDRESS="localhost";
        int MYSQL_PORT=000;//数据库端口
        String MYSQL_NAME="wordpress";
        String MYSQL_TABLE_NAME="lq_users";
        String MYSQL_URL="jdbc:mysql://"+MYSQL_ADDRESS+":"+MYSQL_PORT+"/"+MYSQL_NAME+"?useSSL=false&useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false";//This is database' name!!!
        String MYSQL_USER="root";
        String MYSQL_PASS="数据库密码";
        //=====================
        //Partial JSONObject Key (Part in JSONS ↓↓↓)&& MySQL Field
        String USER_ID_FIELD="user_id";
        String USER_LOGIN_FIELD="user_login";
        String USER_PASS_FIELD="user_pass";
        String DISPLAY_NAME_FIELD="display_name";
        String USER_HEAD_URL_FIELD="user_head_url";
        String USER_REGISTER_TIME_FIELD="user_register_time";



//        //Login UDP Packet Buffer Size
//        int LOGIN_UDP_PACKET_SIZE=1024;
//        //Message Forward UDP Packet Buffer Size
//        int MESSAGE_FORWARD_UDP_PACKET_SIZE=10240;
    }
    //SQL Sentence
    public interface SQL{
        //Pay attention to spaces in SQL sentence!!!
        String PRE_ADD_SQL="INSERT INTO "+ Configs.MYSQL_TABLE_NAME+"("+ Configs.USER_LOGIN_FIELD+","+ Configs.USER_PASS_FIELD+","+ Configs.DISPLAY_NAME_FIELD+") VALUES(?,?,?)";
        String PRE_QUERY_SQL="SELECT * FROM "+ Configs.MYSQL_TABLE_NAME+" WHERE "+ Configs.USER_LOGIN_FIELD+"=?";
        String QUERY_SQL="SELECT * FROM "+ Configs.MYSQL_TABLE_NAME;

        String UPDATE_ONLINE_SIGN="UPDATE lq_users SET online_sign=? WHERE user_id=?";

        String QUERY_FRIENDS_PROCEDURE_CALL_SQL="CALL query_friends_procedure(?)";

        //String QUERY_FRIEND_ID_BY_ACCOUNT_SQL="SELECT user_id FROM lq_users WHERE user_login=?";
        String SET_UP_FRIEND_RELATIVE="INSERT INTO user_friend VALUES(?,?);";
        int ONLINE_SIGN_ON=1;
        int ONLINE_SIGN_OFF=0;
    }
    public interface JSONS{

        //JSON FIELD
        String TYPE_JSON_FIELD="type";//---↓↓↓ Value
        String ACCOUNT_JSON_FIELD="account";
        String PASSWORD_JSON_FIELD="password";
        String RESULT_JSON_FIELD="result";
        String ME_JSON_FIELD="me";
        String FRIENDS_JSON_FIELD="friends";
        String FRIEND_JSON_FIELD="friend";
        String TIPS_JSON_FIELD="tips";


        //Message Forward.
        String CLIENT_RECEIVE_PORT_FIELD="cport";

        String MESSAGE_FROM_FIELD="from";
        String MESSAGE_TO_FIELD="to";
        String MESSAGE_DATE_FIELD="date";
        String MESSAGE_TYPE_FIELD="mtype";
        String MESSAGE_CONTENT_FIELD="content";

        //JSON FIELD VALUE
        int LOGIN_REQUEST_TYPE_VALUE=10101;
        int MESSAGE_FORWARD_TYPE_VALUE=1221;

        int ADD_FRIEND_TYPE_VALUE=43453;

        int SEARCH_FRIEND_TYPE_VALUE=24423;

        int LOGOUT_REQUEST_TYPE_VALUE=13232;

        int CHECK_USER_IS_ONLINE=43222;

        int HOLE_UDP_TEST_PACKET=12312;

        int SUCCESS_RESULT_VALUE=200;
        int FAILED_RESULT_VALUE=500;





    }

}
