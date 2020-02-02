package loveqqserver.threads;

import java.net.DatagramSocket;

/**
 * @author Jason
 * @version 1.0
 * @date 12/19/2019 8:34 AM
 * @describe:
 */
public class MessageForwardThread extends Thread{
    private DatagramSocket socket;
    public MessageForwardThread(DatagramSocket socket){
        super();
        this.socket=socket;
    }
    @Override
    public void run(){

    }
}
