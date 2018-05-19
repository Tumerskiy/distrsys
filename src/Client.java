import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String args[]) throws Exception{

        /*
        using scanner get manager name, check if subs(0,3) within MTL LVL DDO
        set serverName based on that
        */
        Registry registry = LocateRegistry.getRegistry();
        CenterServer stub = (CenterServer) registry.lookup("MTL");
        System.out.println(stub.getLocalRecordCount());

        /*
        some method calls part
        in form of:
        mtlServer.createTRecord(a,b,c,d...)

        probably client should be interactive, and consume input with Scanner
        based on which we should make case{} for called methods
         */
    }
}
