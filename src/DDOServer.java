import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class DDOServer {
    public static void main(String args[]) throws Exception {
        CenterSystem server = new CenterSystem(8182);
        Registry registry = LocateRegistry.getRegistry("localhost", 2000);
        registry.bind("DDO", server);
        System.out.println("DDO is launched");
    }
}

