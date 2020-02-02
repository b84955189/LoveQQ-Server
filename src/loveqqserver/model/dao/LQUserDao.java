package loveqqserver.model.dao;

import loveqqserver.config.R;
import loveqqserver.model.entity.LQUser;
import loveqqserver.utils.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
    * @author Jason
    * @version 1.0
    * @date 12/9/2019 7:37 PM
    * @describe: User Data Access Object Class
    */
    public class LQUserDao {
    /**
     * @author: Jason
     * @date: 12/9/2019
     * @time: 7:55 PM
     * @param  LQUser
     * @return boolean
     * @describe: add LQUser object to DataBase
     */
    public boolean add(LQUser lqUser){
        try {
            PreparedStatement preparedStatement = JDBCUtils.getPreAddStatement();

            preparedStatement.setString(1, lqUser.getUser_login());
            preparedStatement.setString(2,lqUser.getUser_pass());
            preparedStatement.setString(3,lqUser.getDisplay_name());
            //preparedStatement.setDate(4,new Date(new java.util.Date().getTime()));

            int responseCode=preparedStatement.executeUpdate();
            //if insert object successful, return true.
            if(responseCode!=0)
                return true;
            else
                return false;


        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    /**
     * @author: Jason
     * @date: 12/9/2019
     * @time: 10:00 PM
     * @param  String
     * @return LQUser
     * @describe: Query One LQUser Object
     */
    public LQUser queryOne(String userName){
        try {
            PreparedStatement preparedStatement = JDBCUtils.getPreQueryStatement();
            preparedStatement.setNString(1,userName);
            ResultSet set=preparedStatement.executeQuery();
            if(set.next()){

                LQUser lqUser=getLQUser(set);
                lqUser.setUser_pass(set.getString(R.Configs.USER_PASS_FIELD));
                return lqUser;
            }else{
                return null;
            }

        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     * @author: Jason
     * @date: 12/9/2019
     * @time: 10:43 PM
     * @param  null
     * @return List<LQUser>
     * @describe: Query All User of lq_users table.
     */
    public List<LQUser> queryAll(){
        List<LQUser> list=new ArrayList<LQUser>();
        try {
            Statement statement = JDBCUtils.getConnectionInstance().createStatement();
            ResultSet set=statement.executeQuery(R.SQL.QUERY_SQL);
            while(set.next()){
                LQUser lqUser=getLQUser(set);
                lqUser.setUser_pass(set.getString(R.Configs.USER_PASS_FIELD));
                list.add(lqUser);

            }

            return list;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }
    /**
     * @author: Jason
     * @date: 12/19/2019
     * @time: 11:06 PM
     * @param  
     * @return 
     * @describe: To Reduce Code
     */
    private LQUser getLQUser(ResultSet set) throws SQLException {
        LQUser lqUser=new LQUser();
        lqUser.setUser_id(set.getInt(R.Configs.USER_ID_FIELD));
        lqUser.setUser_login(set.getString(R.Configs.USER_LOGIN_FIELD));

        lqUser.setDisplay_name(set.getString(R.Configs.DISPLAY_NAME_FIELD));
        lqUser.setUser_head_url(set.getString(R.Configs.USER_HEAD_URL_FIELD));
        return lqUser;
    }
   /**
    * @author: Jason
    * @date: 12/20/2019
    * @time: 8:22 AM
    * @param  
    * @return 
    * @describe: Query Friends By ID.
    */
    public List<LQUser> queryFriends(int id){
        List<LQUser> list=new ArrayList<LQUser>();
        try {
            CallableStatement callableStatement = JDBCUtils.getPreQueryFriendCallableStatement();
            callableStatement.setInt(1,id);
            ResultSet set=callableStatement.executeQuery();
            while(set.next()){

                //Prevent password leakage.
                list.add(getLQUser(set));
            }

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean changeOnlineSign(int id,int sign){
        try {
            PreparedStatement preparedStatement = JDBCUtils.getPreUpdateOnlineSignStatement();
            preparedStatement.setInt(1, sign);
            preparedStatement.setInt(2, id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean setUpFriendRelative(int id1,int id2){
        int id_1=0,id_2=0;
        try{
            PreparedStatement preparedStatement=JDBCUtils.getPreSetUpFriendRelativeStatement();
            preparedStatement.setInt(1,id1);
            preparedStatement.setInt(2,id2);

            id_1=preparedStatement.executeUpdate();

            preparedStatement.setInt(1,id2);
            preparedStatement.setInt(2,id1);

            id_2=preparedStatement.executeUpdate();

            if(id_1>0&&id_2>0){
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
