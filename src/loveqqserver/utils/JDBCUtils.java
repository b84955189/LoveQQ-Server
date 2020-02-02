package loveqqserver.utils;

import loveqqserver.config.R;

import java.sql.*;

/**
 * @author Jason
 * @version 1.0
 * @date 12/9/2019 10:43 AM
 * @describe JDBC Util Class
 */
public class JDBCUtils {

    private static Connection con;
    private static PreparedStatement preAddStatement,preQueryStatement,preUpdateOnlineSignStatement,preSetUpFriendRelativeStatement;
    private static CallableStatement preQueryFriendCallableStatement;



    //init Connection object when load the util class
    static{
        try {
            //The Server's mysql version is 5.1
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(R.Configs.MYSQL_URL,R.Configs.MYSQL_USER,R.Configs.MYSQL_PASS);
            preAddStatement=con.prepareStatement(R.SQL.PRE_ADD_SQL);
            preQueryStatement=con.prepareStatement(R.SQL.PRE_QUERY_SQL);
            preQueryFriendCallableStatement=con.prepareCall(R.SQL.QUERY_FRIENDS_PROCEDURE_CALL_SQL);
            preUpdateOnlineSignStatement=con.prepareStatement(R.SQL.UPDATE_ONLINE_SIGN);
            preSetUpFriendRelativeStatement=con.prepareStatement(R.SQL.SET_UP_FRIEND_RELATIVE);
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @author: Jason
     * @date: 12/9/2019
     * @time: 7:09 PM
     * @param  null
     * @return  Connection
     * @describe:  obtain Connection object
     */
    public static Connection getConnectionInstance(){
        return con;
    }
    /**
     * @author: Jason
     * @date: 12/9/2019
     * @time: 8:04 PM
     * @param
     * @return PreparedStatement
     * @describe: Get Add Precompile Statement Object
     */
    public static PreparedStatement getPreAddStatement(){
        return preAddStatement;
    }
    /**
     * @author: Jason
     * @date: 12/9/2019
     * @time: 9:57 PM
     * @param  null
     * @return PreparedStatement
     * @describe: Get Query Precompile Statement Object
     */
    public static PreparedStatement getPreQueryStatement(){
        return preQueryStatement;
    }
    /**
     * @author: Jason
     * @date: 12/19/2019
     * @time: 11:06 PM
     * @param
     * @return
     * @describe: Get CallableStatement For Query Friends
     */
    public static CallableStatement getPreQueryFriendCallableStatement(){
        return preQueryFriendCallableStatement;
    }

    public static PreparedStatement getPreUpdateOnlineSignStatement() {
        return preUpdateOnlineSignStatement;
    }

    public static PreparedStatement getPreSetUpFriendRelativeStatement() {
        return preSetUpFriendRelativeStatement;
    }
}
