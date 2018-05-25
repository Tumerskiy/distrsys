import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

public class UDPRegistryServer implements Runnable {
    private int portNumber;

    public UDPRegistryServer(int portNumber) {
        this.portNumber = portNumber;
    }

    @Override
    public void run() {
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket(portNumber);
            byte[] buffer = new byte[1024];
            byte[] sendBuffer = new byte[1024];
            while (true){
                DatagramPacket request = new DatagramPacket(buffer,buffer.length);
                try {
                    datagramSocket.receive(request);
                    String receiveData = new String(request.getData(),0,request.getLength());
                    System.out.printf("received data: "+receiveData+"\n");
                    String[] requestData = receiveData.split(":");
                    String reply = "something went wrong";
                    System.out.printf("request ops: "+ requestData[0]+"\n");
                    if (requestData[0].equals("register")){
                        reply = CenterRegistry.register(requestData[1]+":"+requestData[2]+":"+requestData[3]);
                    } else if(requestData[0].equals("getservers")){
                        reply = CenterRegistry.getServers();
                    } else{
                        reply = "wrong request";
                    }
                    sendBuffer = reply.getBytes();

                    DatagramPacket send = new DatagramPacket(sendBuffer,sendBuffer.length,request.getAddress(),request.getPort());
                    datagramSocket.send(send);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }
}
