package loveqqserver.threads;


import loveqqserver.config.GroupUserData;
import loveqqserver.threads.runnables.GroupRunnable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Jason
 * @version 1.0
 * @date 12/31/2019 9:31 AM
 * @describe:
 */
public class GroupThread extends Thread{
    private ServerSocket serverSocket;
    public GroupThread(ServerSocket serverSocket){
        super();
        this.serverSocket=serverSocket;
    }
    public void run(){
        try {
        while(true){

                Socket socket=serverSocket.accept();
                GroupUserData.sockets.add(socket);
                System.out.println(socket.getInetAddress().getHostAddress()+"登陆了");
                new Thread(new GroupRunnable(socket)).start();
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
