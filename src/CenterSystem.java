import java.beans.BeanInfo;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.beans.Introspector;
import java.beans.Statement;
import java.beans.PropertyDescriptor;

public class CenterSystem extends UnicastRemoteObject implements CenterServer {

    protected  ConcurrentHashMap<Character,ArrayList<Records>> database = new ConcurrentHashMap<>();
    private static Registry centerRegistry;

    static {
        try {
            centerRegistry = LocateRegistry.getRegistry();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public CenterSystem() throws RemoteException {
        super();
    }

    public void createTRecord(String managerId, String firstName, String lastName, String address, int phone, String specialization, String location) throws RemoteException {
        TeacherRecord teacherRecord = new TeacherRecord(firstName,lastName,address,phone,specialization,location);
        char key = lastName.charAt(0);
        if(database.get(key)==null){
            ArrayList<Records> value =  new ArrayList<>();
            value.add(teacherRecord);
            database.put(key, value);
        } else{
            ArrayList<Records> value =database.get(key);
            value.add(teacherRecord);
            database.put(key, value);
        }
    }

    public void createSRecord(String managerId,String firstName, String lastName, String courseRegistered, String status, String statusDate) throws RemoteException {
        StudentRecord studentRecord = new StudentRecord(firstName,lastName, courseRegistered, status, statusDate);
        char key = lastName.charAt(0);
        if(database.get(key)==null){
            ArrayList<Records> value =  new ArrayList<>();
            value.add(studentRecord);
            database.put(key, value);
        } else{
            ArrayList<Records> value =database.get(key);
            value.add(studentRecord);
            database.put(key, value);
        }
    }

    public String getRecordCounts(String managerId) throws Exception {
        String result = "";
        Registry registry = LocateRegistry.getRegistry();
        String[] servers = registry.list();
        for (String server : servers) {
            CenterServer curServer = (CenterServer) registry.lookup(server);
            result += server + ":" + curServer.getLocalRecordCount() + " ";
        }
        return result;
    }

    public int getLocalRecordCount() throws RemoteException{
        return this.database.size();
    }

    public String editRecord(String managerId,String recordID, String fieldName, String newValue) throws Exception {
        String result = "";

        for (char key : database.keySet()){
            for (Records record : database.get(key)){
                if (record.getRecordID().equals(recordID)){
                    BeanInfo recordInfo = Introspector.getBeanInfo(record.getClass());
                    PropertyDescriptor[] recordPds = recordInfo.getPropertyDescriptors();
                    for (PropertyDescriptor prop : recordPds){
                        if (prop.getName().equals(fieldName)){
                            Statement stmt = new Statement(record, prop.getWriteMethod().getName(), new Object[]{newValue});
                            stmt.execute();
                            result = "Record updated";
                            return result;
                        }
                        else{
                            result = "fieldName doesn't match record type";
                        }
                    }

                }
            }
        }
        return  result;
    }

}
