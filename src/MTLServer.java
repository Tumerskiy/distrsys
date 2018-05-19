import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MTLServer {
    public static void main(String args[]) throws Exception {
        CenterSystem server = new CenterSystem();
        Registry registry = LocateRegistry.getRegistry();
        registry.bind("MTL", server);

    }
}
