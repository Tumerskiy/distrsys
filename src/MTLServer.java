import java.rmi.Naming;

public class MTLServer {
    public static void main(String args[]) throws Exception {
        CenterSystem server = new CenterSystem();
        Naming.rebind("MTL", server);
    }
}
