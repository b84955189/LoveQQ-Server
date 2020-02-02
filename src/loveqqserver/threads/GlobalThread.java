package loveqqserver.threads;

import loveqqserver.config.R;
import loveqqserver.threads.runnables.OperationRunnable;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author Jason
 * @version 1.0
 * @date 12/18/2019 8:10 PM
 * @describe:
 */
public class GlobalThread extends Thread{
    private DatagramSocket socket;
    public GlobalThread(DatagramSocket socket){
        super();
        this.socket=socket;
    }
    @Override
    public void run() {
        try {
            while (true) {
                DatagramPacket packet = new DatagramPacket(new byte[R.Configs.MESSAGE_BUFFER_SIZE],0,R.Configs.MESSAGE_BUFFER_SIZE);
                socket.receive(packet);

                //Operation Thread Method.
                new Thread(new OperationRunnable(packet,socket)).start();


            }
        }catch(IOException e){
            e.printStackTrace();
            //Iteration
            this.run();
        }
    }
}
