import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class DDOServer {
    public static void main(String args[]) throws Exception {
        CenterSystem server = new CenterSystem();
        Registry registry = LocateRegistry.getRegistry();
        registry.bind("DDO", server);

    }
}

