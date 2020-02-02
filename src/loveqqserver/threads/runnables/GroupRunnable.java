package loveqqserver.threads.runnables;

import loveqqserver.config.GroupUserData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Iterator;

/**
 * @author Jason
 * @version 1.0
 * @date 12/31/2019 9:39 AM
 * @describe:
 */
public class GroupRunnable implements Runnable{
    public Socket socket;
    public GroupRunnable(Socket socket){
        super();
        this.socket=socket;

    }
    @Override
    public void run() {
        try {
            socket.setSoTimeout(0);
            DataInputStream dataInputStream=new DataInputStream(socket.getInputStream());
            String tempMessage=null;
            while((tempMessage=dataInputStream.readUTF())!=null){
                Iterator<Socket> iterator=GroupUserData.sockets.iterator();
                while(iterator.hasNext()){
                    Socket otherSocket=iterator.next();
                    if(otherSocket!=socket){
                        DataOutputStream dataOutputStream=new DataOutputStream(otherSocket.getOutputStream());
                        System.out.println("TCP测试"+tempMessage);
                        dataOutputStream.writeUTF(tempMessage);
                    }
                }
            }
            GroupUserData.sockets.remove(socket);




        }catch (Exception e){

            e.printStackTrace();
            GroupUserData.sockets.remove(socket);
        }
    }
}
