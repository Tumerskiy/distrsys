import java.util.HashMap;

public class UDPPortManament {
    private static HashMap<String,Integer> portmap = new HashMap<>();

    static  {
        portmap.put("MTL",2096);
        portmap.put("LVL",2967);
        portmap.put("DDO",3001);
    }

    public static int getPortNumber(String name){
        return portmap.get(name);
    }

}
