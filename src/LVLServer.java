import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LVLServer {
    public static void main(String args[]) throws Exception {
        CenterSystem server = new CenterSystem(1098);
        Registry registry = LocateRegistry.getRegistry();
        registry.bind("LVL", server);
        System.out.printf("LVL is launched");
    }
}
