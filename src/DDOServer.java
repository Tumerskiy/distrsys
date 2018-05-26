import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class DDOServer {
    public static void main(String args[]) throws Exception {
        String centerRegistryHost = "localhost";
        int centerRegistryRMIPort = 2000;
        int centerRegistryUDPPort = 8190;

        CenterSystem server = new CenterSystem(8182,centerRegistryHost, centerRegistryUDPPort);
        server.setCenterName("DDO");
        Registry registry = LocateRegistry.getRegistry(centerRegistryHost,centerRegistryRMIPort);
        registry.bind("DDO", server);
        UDPClient.request("register:DDO:"+InetAddress.getLocalHost().getHostName()+":8182",centerRegistryHost, centerRegistryUDPPort);

        System.out.println("DDO is launched");
        System.out.println("press stop to shut down!");
        Scanner scanner = new Scanner(System.in);
        if (scanner.nextLine().equals("stop")){
            server.stopServer();
        }
    }
}

