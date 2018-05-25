import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class DDOServer {
    public static void main(String args[]) throws Exception {
        CenterSystem server = new CenterSystem(2095);
        Registry registry = LocateRegistry.getRegistry();

        registry.bind("DDO", server);
        System.out.println("DDO is launched");
    }
}

