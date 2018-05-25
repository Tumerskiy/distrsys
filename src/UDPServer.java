import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer implements Runnable {
    private int portNumber;
    private CenterSystem centerSystem;

    public UDPServer(int portNumber, CenterSystem centerSystem) {
        this.portNumber = portNumber;
        this.centerSystem = centerSystem;
    }

    public CenterSystem getCenterSystem() {
        return centerSystem;
    }

    public void setCenterSystem(CenterSystem centerSystem) {
        this.centerSystem = centerSystem;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
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
                    String receiveData = new String(request.getData());
                    if (receiveData.charAt(0) == centerSystem.getCenterName().charAt(0)) {
                        String reply = centerSystem.getLocalRecordCount() + "";
                        sendBuffer = reply.getBytes();
                    }
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
