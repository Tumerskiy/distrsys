import java.util.HashMap;

public class ManagerRepositry {
    public static final String NAME_MONTREAL = "MTL";
    public static final String NAME_Laval = "LVL";
    public static final String NAME_DollardDesOrmeaux = "DDO";
    private static HashMap<String,String> mTLManager = new HashMap<>();
    private static HashMap<String,String> lVLManager = new HashMap<>();
    private static HashMap<String,String> dDoManager = new HashMap<>();

    public ManagerRepositry() {
    }

    public static void addManagerToCenter(String id){
        String identifier = id.substring(0,3);
        if (identifier.equals(NAME_MONTREAL)){
            mTLManager.put(id,id);
        }else if(identifier.equals(NAME_Laval)){
            lVLManager.put(id,id);
        }else if (identifier.equals(NAME_DollardDesOrmeaux)){
            dDoManager.put(id,id);;
        }
    }
    public static boolean hasManager(String id){
        boolean hasID = false;
        if (mTLManager.containsKey(id) || lVLManager.containsKey(id) || dDoManager.containsKey(id) ){
            hasID = true;
        }
        return hasID;
    }

    public static int getCountOflocationManager(String nameOfLocation){
        int numbers = 0;
        if (nameOfLocation.equals(NAME_MONTREAL)){
            numbers = mTLManager.size();
        }else if(nameOfLocation.equals(NAME_Laval)){
            numbers = lVLManager.size();
        }else if (nameOfLocation.equals(NAME_DollardDesOrmeaux)){
            numbers = dDoManager.size();
        }
        return numbers;
    }


}
