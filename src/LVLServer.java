import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LVLServer {
    public static void main(String args[]) throws Exception {
        String centerRegistryHost = "localhost";
        int centerRegistryRMIPort = 2000;
        int centerRegistryUDPPort = 8190;

        CenterSystem server = new CenterSystem(8181,centerRegistryHost, centerRegistryUDPPort);
        Registry registry = LocateRegistry.getRegistry(centerRegistryHost,centerRegistryRMIPort);
        registry.bind("LVL", server);
        UDPClient.request("register:LVL:"+InetAddress.getLocalHost().getHostName()+":8181",centerRegistryHost, centerRegistryUDPPort);

        System.out.printf("LVL is launched");
    }
}
