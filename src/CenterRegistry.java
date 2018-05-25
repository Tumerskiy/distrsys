import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.LinkedList;

public class CenterRegistry {
    public static Registry rmiRegistry;
    private static HashMap<String,String[]> servers = new HashMap<>();
    /*
    Exchange between clients and this server due to simplification for UDP implementation uses Strings,
    parameters are encoded within String using ":" as splitter of first level and ";" as splitter of second level.
    e.g.:
    [[1,2,3,4],[5,6,7,8,]] will be encoded/decoded as 1:2:3:4;5:6:7:8

     */


    public static  String register(String config){
        String result = "";
        String[] configFields = config.split(":");
        String[] address = {configFields[1], configFields[2]};
        servers.put(configFields[0], address);
        result=configFields[0]+ " successfully registered";
        System.out.printf(configFields[0]+ " successfully registered\n");
        return result;
    }

    public static void unRegister(String name){
            servers.remove(name);
            CenterSystem.stopServer(name);

        System.out.println("Stop:" + name + " Server Successfully!");
    }

    public static String getServers() {
        String result = "";
        for (String key:servers.keySet()){
            result+=key;
            for (String param:servers.get(key)){
                result+=":"+param;
            }
            result+=";";
        }
        System.out.println(result+"\n");
        return result;
    }

    public static void main(String[] args){
        try {
            rmiRegistry = LocateRegistry.createRegistry(2000);
            /*
            From here we don't have to do anything with rmiregistry binding, it's done on server implementation side.
             */
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        UDPRegistryServer udpserver = new UDPRegistryServer(8190);
        new Thread(udpserver).start();

        System.out.printf("rmiRegistry launched on UDP port 2000\n centerRegistry is waiting for servers requests\n");
    }

}
