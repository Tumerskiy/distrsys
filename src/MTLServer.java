import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MTLServer {

    public static void main(String args[]) throws Exception {
        String centerRegistryHost = "localhost";
        int centerRegistryRMIPort = 2000;
        int centerRegistryUDPPort = 8190;

        CenterSystem server = new CenterSystem(8180,centerRegistryHost, centerRegistryUDPPort);
        Registry registry = LocateRegistry.getRegistry(centerRegistryHost,centerRegistryRMIPort);
        registry.bind("MTL", server);
        UDPClient.request("register:MTL:"+InetAddress.getLocalHost().getHostName()+":8180",centerRegistryHost, centerRegistryUDPPort);

        System.out.printf("MTL is launched");
    }
}
