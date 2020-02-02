package loveqqserver.control;

import loveqqserver.model.dao.LQUserDao;
import loveqqserver.model.entity.LQUser;

import java.util.List;

/**
 * @author Jason
 * @version 1.0
 * @date 12/9/2019 9:23 PM
 * @describe: LQ Control Class
 */
public class LQActionControl {
    private LQUserDao lqUserDao;

    public LQActionControl(){
        initDao();
    }
    /**
     * @author: Jason
     * @date: 12/9/2019
     * @time: 9:25 PM
     * @param  null
     * @return void
     * @describe: init Data Access Object
     */
    private void initDao(){
        lqUserDao=new LQUserDao();

    }

    /**
     * @author: Jason
     * @date: 12/9/2019
     * @time: 9:28 PM
     * @param  LQUser
     * @return boolean
     * @describe: Add User Object
     */
    public boolean addUser(LQUser lqUser){
        return lqUserDao.add(lqUser);
    }
    /**
     * @author: Jason
     * @date: 12/9/2019
     * @time: 10:06 PM
     * @param  String
     * @return LQUser
     * @describe: Query One LQUser Object
     */
    public LQUser queryOneUser(String userName){
        return lqUserDao.queryOne(userName);
    }
    /**
     * @author: Jason
     * @date: 12/9/2019
     * @time: 10:45 PM
     * @param  null
     * @return List<LQUser>
     * @describe: Query All User of lq_users table.
     */
    public List<LQUser> queryAllUser(){
        return lqUserDao.queryAll();
    }
    /**
     * @author: Jason
     * @date: 12/20/2019
     * @time: 8:23 AM
     * @param
     * @return
     * @describe: Query Friends By ID.
     */
    public List<LQUser> queryFriends(int id){
        return lqUserDao.queryFriends(id);
    }

    public boolean changeOnlineSign(int id,int sign){
        return lqUserDao.changeOnlineSign(id,sign);
    }
    public boolean setUpFriendRelative(int id1,int id2){
        return lqUserDao.setUpFriendRelative(id1,id2);
    }
}
