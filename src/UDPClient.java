import java.io.IOException;
import java.net.*;

public class UDPClient {
    public static String request(String centerInitial,int centerPortNumber){
        String receivedInfor = "";
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket();
            try {
                InetAddress inetAddress = InetAddress.getLocalHost();
                int portNumber = centerPortNumber;
                byte[] specifiedCenter = centerInitial.getBytes();

                DatagramPacket datagramPacket = new DatagramPacket(specifiedCenter,centerInitial.length(),inetAddress,portNumber);
                try {
                    datagramSocket.send(datagramPacket);
                    byte[] buffer = new byte[1024];
                    DatagramPacket replayByte = new DatagramPacket(buffer,buffer.length);
                    datagramSocket.receive(replayByte);
                    receivedInfor = new String(replayByte.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return receivedInfor;
    }
}
