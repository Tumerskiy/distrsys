import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class DDOServer {
    public static void main(String args[]) throws Exception {
        String centerRegistryHost = "localhost";
        int centerRegistryRMIPort = 2000;
        int centerRegistryUDPPort = 8190;

        CenterSystem server = new CenterSystem(8182,centerRegistryHost, centerRegistryUDPPort);
        Registry registry = LocateRegistry.getRegistry(centerRegistryHost,centerRegistryRMIPort);
        registry.bind("DDO", server);
        UDPClient.request("register:DDO:"+InetAddress.getLocalHost().getHostName()+":8182",centerRegistryHost, centerRegistryUDPPort);

        System.out.printf("DDO is launched");
    }
}

