import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.LinkedList;

public class CenterRepo {
    public static Registry centerRegistry;
    public static HashMap<String,Integer> portMap = new HashMap<>();

    static {
        try {
            centerRegistry = LocateRegistry.createRegistry(2000);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void register(CenterSystem centerSystem){
        try {
            centerRegistry.rebind(centerSystem.getCenterName(),centerSystem);
            portMap.put(centerSystem.getCenterName(),centerSystem.getPortNumber());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public static Registry getCenterRegistry() {
        return centerRegistry;
    }

    public static HashMap<String, Integer> getPortMap() {
        return portMap;
    }
}
