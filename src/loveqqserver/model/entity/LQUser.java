package loveqqserver.model.entity;

/**
 * @author Jason
 * @version 1.0
 * @date 12/9/2019 7:32 PM
 * @describe: User Object Class & Generated  by JsonFormat Plug-in.
 */
public class LQUser {
    /**
     * user_head_url : http://storage.lking.top/14a338db84d7f71841d732ad387740d7.jpeg
     * user_id : 1
     * user_login : admin
     * display_name : 听风
     * user_pass : 123456
     */

    private String user_head_url;
    private int user_id;
    private String user_login;
    private String display_name;
    private String user_pass;

    public String getUser_head_url() {
        return user_head_url;
    }

    public void setUser_head_url(String user_head_url) {
        this.user_head_url = user_head_url;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }


    @Override
    public String toString() {
        return user_id+","+user_login+","+user_pass+","+display_name+","+user_head_url;
    }
}
