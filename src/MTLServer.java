
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MTLServer {

    public static void main(String args[]) throws Exception {

        CenterSystem server = new CenterSystem(8180);
        Registry registry = LocateRegistry.createRegistry(2000);
        registry.bind("MTL", server);
        System.out.printf("MTL is launched");
    }
}
