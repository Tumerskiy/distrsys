import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LVLServer {
    public static void main(String args[]) throws Exception {

        CenterSystem server = new CenterSystem();
        Registry registry = LocateRegistry.getRegistry("localhost",2000);
        registry.bind("LVL", server);
        System.out.printf("LVL is launched");
    }
}
