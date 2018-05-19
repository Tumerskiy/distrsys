import java.rmi.Naming;
import java.rmi.RemoteException;

public class MTLServer extends CenterSystem {
    public MTLServer() throws RemoteException {
    }

    public static void main(String args[]) throws Exception {
        CenterSystem server = new CenterSystem();
        CenterSystem.registry("MTL",server);
    }
}
