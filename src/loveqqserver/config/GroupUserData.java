package loveqqserver.config;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jason
 * @version 1.0
 * @date 12/31/2019 9:32 AM
 * @describe:
 */
public class GroupUserData {
    public static List<Socket> sockets;
    static {
        sockets=new ArrayList<Socket>();
    }
}
