import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Database {
    private Map<Character,LinkedList<Records>> database = new HashMap<>();

    public Database() {
    }

    public void add(Records record){
        if (database.containsKey(record.getLastName().charAt(0))){
                database.get(record.getLastName().charAt(0)).add(record);
        }else {
            LinkedList<Records> newLastNameList = new LinkedList<>();
            newLastNameList.add(record);
            database.put(record.getLastName().charAt(0),newLastNameList);
        }
    }
}
