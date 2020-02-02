package loveqqserver.model.entity;

import java.util.List;

/**
 * @author Jason
 * @version 1.0
 * @date 12/19/2019 9:05 PM
 * @describe:
 */
public class AccountInformation {

    /**
     * Friends : [{"user_head_url":"http://storage.lking.top/14a338db84d7f71841d732ad387740d7.jpeg","user_id":1,"user_login":"admin","display_name":"听风","user_pass":"123456"},{"user_head_url":"http://storage.lking.top/14a338db84d7f71841d732ad387740d7.jpeg","user_id":1,"user_login":"admin","display_name":"听风","user_pass":"123456"}]
     * Me : {"user_head_url":"http://storage.lking.top/14a338db84d7f71841d732ad387740d7.jpeg","user_id":1,"user_login":"admin","display_name":"听风","user_pass":"123456"}
     * Result : 200
     * Tips : 成功登录
     */

    private LQUser me;
    private int result;
    private String tips;
    private List<LQUser> friends;

    public LQUser getMe() {
        return me;
    }

    public void setMe(LQUser me) {
        this.me = me;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public List<LQUser> getFriends() {
        return friends;
    }

    public void setFriends(List<LQUser> friends) {
        this.friends = friends;
    }




}
