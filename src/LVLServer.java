import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class LVLServer {
    public static void main(String args[]) throws Exception {
        String centerRegistryHost = "localhost";
        int centerRegistryRMIPort = 2000;
        int centerRegistryUDPPort = 8190;

        CenterSystem server = new CenterSystem(8181,centerRegistryHost, centerRegistryUDPPort);
        server.setCenterName("LVL");
        Registry registry = LocateRegistry.getRegistry(centerRegistryHost,centerRegistryRMIPort);
        registry.bind("LVL", server);
        UDPClient.request("register:LVL:"+InetAddress.getLocalHost().getHostName()+":8181",centerRegistryHost, centerRegistryUDPPort);

        System.out.println("LVL is launched");
        System.out.println("press stop to shut down!");
        Scanner scanner = new Scanner(System.in);
        if (scanner.nextLine().equals("stop")){
            server.stopServer();
        }
    }
}
