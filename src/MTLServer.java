import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MTLServer {
    public static void main(String args[]) throws Exception {
        CenterSystem server = new CenterSystem();
        Registry registry = LocateRegistry.createRegistry(2964);
        registry.bind("MTL", server);
        System.out.println("server is start");
    }
}
