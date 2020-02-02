package loveqqserver.utils;

import loveqqserver.config.R;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;

/**
 * @author Jason
 * @version 1.0
 * @date 12/18/2019 2:43 PM
 * @describe:
 */
public class UDPUtils {
    //-----TCP
    private static ServerSocket serverSocket;

    //--------
    private static DatagramSocket receiveSocket, sendSocket;

    //Load these statements only one.
    static{
        try{
            //----TCP--
            serverSocket=new ServerSocket(33442);

            //---------
            //System dispatch port to the program.
            receiveSocket =new DatagramSocket(R.Configs.LOGIN_PORT);
            //LoginSocket's Timeout is forever!!!
            receiveSocket.setSoTimeout(0);

            sendSocket =new DatagramSocket(R.Configs.MESSAGE_FORWARD_PORT);
            //ForwardSocket's Timeout is 3s!!!
            sendSocket.setSoTimeout(3000);


        }catch(SocketException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    /**
     * @author: Jason
     * @date: 12/17/2019
     * @time: 7:20 PM
     * @param
     * @return
     * @describe: Return (Global) Receive UDP Socket.
     */
    public static DatagramSocket getReceiveSocket(){
        return receiveSocket;
    }
    /**
     * @author: Jason
     * @date: 12/19/2019
     * @time: 8:27 AM
     * @param
     * @return
     * @describe: Return (Global) Send UDP Socket.
     */
    public static DatagramSocket getSendSocket(){
        return sendSocket;
    }
    public static boolean sendUDPPacket(JSONObject jsonData, InetAddress inetAddress,int port, int type){
        try {
            byte[] dataBytes=jsonData.toString().getBytes(R.Configs.DEFAULT_CHARSET);
            //test
            //System.out.println(jsonData.toString());
            System.out.println("从这里发出了："+inetAddress.toString()+":"+port);
            DatagramPacket packet = new DatagramPacket(dataBytes,0,dataBytes.length, inetAddress, port);
            receiveSocket.send(packet);
            System.out.println("测试：已发出！");

                return responseTimer(receiveSocket,type);








        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * @author: Jason
     * @date: 12/17/2019
     * @time: 10:14 PM
     * @param
     * @return
     * @describe: Server Response Timer ---3s
     */
    private static boolean responseTimer(DatagramSocket socket,int type){


        try {
        //Judge Type.
        switch(type){
            //Login Type No Return Json Data.
            case R.Configs.LOGIN_SEND_TYPE:{

                return true;
            }//Because ↑ Return sentence,So,"break" is unreachable forever! So,I delete this "break".
            case R.Configs.MESSAGE_FORWARD_SEND_TYPE:{
                return true;
//                DatagramPacket responsePacket = new DatagramPacket(new byte[R.Configs.MESSAGE_BUFFER_SIZE], 0, R.Configs.MESSAGE_BUFFER_SIZE);
//
//                socket.receive(responsePacket);
//                JSONObject jsonObject=JSONObject.fromObject(new String(responsePacket.getData(),0,responsePacket.getLength(),R.Configs.DEFAULT_CHARSET));
//                //Switch Type
//                switch(jsonObject.getInt(R.JSONS.RESULT_JSON_FIELD)){
//                    case R.JSONS.SUCCESS_RESULT_VALUE:{
//                            //Message Forward Successful.
//                            return true;
//                    }//Because ↑ Return sentence,So,"break" is unreachable forever! So,I delete this "break".
//                    case R.JSONS.FAILED_RESULT_VALUE:{
//                            //Message Forward Failed.
//                            return false;
//                    }//Because ↑ Return sentence,So,"break" is unreachable forever! So,I delete this "break".
//                }
            }//;break;
        }






        } catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }
//   public static JSONObject receivePacket(){
//       JSONObject messageJson=null;
//       try {
//           DatagramPacket packet = new DatagramPacket(new byte[R.Configs.MESSAGE_BUFFER_SIZE], 0, R.Configs.MESSAGE_BUFFER_SIZE);
//           sendSocket.receive(packet);
//           messageJson=JSONObject.fromObject(new String(packet.getData(),0,packet.getLength(),"UTF-8"));
//
//           //To respond to the LQ server.
//            sendSocket.send(getResponseSuccessPacket());
//
//           return messageJson;
//       } catch (IOException e) {
//           e.printStackTrace();
//
//       }catch(Exception e){
//           e.printStackTrace();
//       }
//       return null;
//   }
//    /**
//     * @author: Jason
//     * @date: 12/17/2019
//     * @time: 9:31 PM
//     * @param
//     * @return
//     * @describe: Return Response Packet for Server.
//     */
//    private static DatagramPacket getResponseSuccessPacket(){
//        byte[] data= R.JSONS.RESPONSE_JSON.getBytes();
//        return new DatagramPacket(data,0,data.length, serverAddress.getAddress(), serverAddress.getPort());
//    }
    public static ServerSocket getServerSocket(){
        return serverSocket;
    }
}
