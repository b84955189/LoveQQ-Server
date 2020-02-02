package loveqqserver.model.entity;

import java.net.InetAddress;

/**
 * @author Jason
 * @version 1.0
 * @date 12/25/2019 3:15 PM
 * @describe:
 */
public class OnlineUser {
    private int user_id;
    private InetAddress user_address;
    private int user_port;

    private boolean checkSign;

    public OnlineUser(int user_id, InetAddress user_address, int user_port) {
        this.user_id = user_id;
        this.user_address = user_address;
        this.user_port = user_port;
        this.checkSign=true;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public InetAddress getUser_address() {
        return user_address;
    }

    public void setUser_address(InetAddress user_address) {
        this.user_address = user_address;
    }

    public int getUser_port() {
        return user_port;
    }

    public void setUser_port(int user_port) {
        this.user_port = user_port;
    }

    private OnlineUser(){
        super();
    }

    @Override
    public String toString() {
        return "OnlineUser{" +
                "user_id=" + user_id +
                ", user_address=" + user_address +
                ", user_port=" + user_port +
                '}';
    }

    public boolean getCheckSign() {
        return checkSign;
    }

    public void setCheckSign(boolean checkSign) {
        this.checkSign = checkSign;
    }
}
