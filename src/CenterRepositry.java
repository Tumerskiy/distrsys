import java.util.HashMap;

public class CenterRepositry {
    public static final String NAME_MONTREAL = "MTL";
    public static final String NAME_Laval = "LVL";
    public static final String NAME_DollardDesOrmeaux = "DDO";
    private static HashMap<Character,Records> mTLManager = new HashMap<>();
    private static HashMap<Character,Records> lVLManager = new HashMap<>();
    private static HashMap<Character,Records> dDoManager = new HashMap<>();

    public CenterRepositry() {
    }

    public static void addToCenter(String id,Records record){
        String identifier = id.substring(0,3);
        if (identifier.equals(NAME_MONTREAL)){
            mTLManager.put(record.getLastName().charAt(0),record);
        }else if(identifier.equals(NAME_Laval)){
            lVLManager.put(record.getLastName().charAt(0),record);
        }else if (identifier.equals(NAME_DollardDesOrmeaux)){
            dDoManager.put(record.getLastName().charAt(0),record);
        }
    }
    public static HashMap<Character, Records> getCenter(String id){
        String identifier = id.substring(0,3);

        if (identifier.equals(NAME_MONTREAL)){
            return mTLManager;
        }else if(identifier.equals(NAME_Laval)){
            return lVLManager;
        }else if (identifier.equals(NAME_DollardDesOrmeaux)){
            return dDoManager;
        }
        return null;
    }

    public static int getCountOflocationManager(String mangerID){
        int numbers = 0;
        String id = mangerID.substring(0,3);
        if (id.equals(NAME_MONTREAL)){
            numbers = mTLManager.size();
        }else if(id.equals(NAME_Laval)){
            numbers = lVLManager.size();
        }else if (id.equals(NAME_DollardDesOrmeaux)){
            numbers = dDoManager.size();
        }
        return numbers;
    }


}
