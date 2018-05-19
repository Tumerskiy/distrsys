import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

public class test {
    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.getRegistry();
        System.out.println(Arrays.toString(registry.list()));
    }
}
