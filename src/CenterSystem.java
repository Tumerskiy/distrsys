import java.beans.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class CenterSystem extends UnicastRemoteObject implements CenterServer {
    private String centerName = "";
    protected  ConcurrentHashMap<Character,ArrayList<Records>> database = new ConcurrentHashMap<>();
    private static Registry centerRegistry;
    private static int randomId=9999;

    static {
        try {
            centerRegistry = LocateRegistry.getRegistry(2964);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public CenterSystem() throws RemoteException {
        super();
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public void createTRecord(String managerId, String firstName, String lastName, String address, int phone, String specialization, String location) throws RemoteException {
        TeacherRecord teacherRecord = new TeacherRecord(firstName,lastName,address,phone,specialization,location);
        teacherRecord.setRecordID("TR"+String.valueOf(++randomId));
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
        Log.log(Log.getCurrentTime(),centerName,managerId,"createTRecord","Successful");
    }

    public void createSRecord(String managerId, String firstName, String lastName, ArrayList<String> courseRegistered, String status, String statusDate) throws RemoteException {
        StudentRecord studentRecord = new StudentRecord(firstName,lastName, courseRegistered, status, statusDate);
        studentRecord.setRecordID("SR"+String.valueOf(++randomId));
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
        Log.log(Log.getCurrentTime(),centerName,managerId,"createSRecord","Successful");
    }

    public String getRecordCounts(String managerId) throws RemoteException, NotBoundException {
        String result = "";
        Registry registry = LocateRegistry.getRegistry(2964);
        String[] servers = registry.list();
        for (String server : servers) {
            CenterServer curServer = (CenterServer) registry.lookup(server);
            result += server + ":" + curServer.getLocalRecordCount() + " ";
        }
        Log.log(Log.getCurrentTime(),centerName,managerId,"getRecordCounts","Successful");
        return result;
    }

    public int getLocalRecordCount() throws RemoteException{
        return this.database.size();
    }

    public String editRecord(String managerId,String recordID, String fieldName, String newValue) throws Exception {
        String result = "";
        Boolean ableModified = true;

        for (char key : database.keySet()){
            for (Records record : database.get(key)){
                if (record.getRecordID().equals(recordID)){
                    /* following reads information about the object, more precisely of its class, into BeanInfo
                     not sure if in this particular case it will get proper specific class of record, eg StudentRecord or
                     Teacher record, it could take its superclass Record...
                    */
                    BeanInfo recordInfo = Introspector.getBeanInfo(record.getClass());

                    /*
                    recordPds in this case is the array of properties available in this class
                     */
                    PropertyDescriptor[] recordPds = recordInfo.getPropertyDescriptors();
                    for (PropertyDescriptor prop : recordPds){
                        if (prop.getName().equals(fieldName)){
                            if (fieldName.equals("location")){
                                ableModified = newValue.equals("MTL") || newValue.equals("LVL") || newValue.equals("DDO");
                            }
                            /*
                            Here we form the statement to execute, in our case, update the field in the object.
                            We rely on property names captured in previous recordPds. There is no need in explicit definition
                            of particular Student of TeacherRecord class, since we can just analyze whatever record found
                            with recordId.
                            prop.getWriteMethod() looks for method which writes to property, which was filtered with previous
                            prop.getName().equals(fieldName). As a result newValue is passed as argument to method found, hopefully,
                            it is the proper setter in the end.

                            * look into java reflection and java beans library.
                             */
                            if (ableModified) {
                                Statement stmt = new Statement(record, prop.getWriteMethod().getName(), new Object[]{newValue});
                                stmt.execute();
                                result = "Record updated";
                                String operation = "edit: " + prop.getName();
                                Log.log(Log.getCurrentTime(),centerName,managerId,operation,"Successful");
                                return result;
                            }else {
                                String operation = "edit: " + prop.getName();
                                Log.log(Log.getCurrentTime(),centerName,managerId,operation,"Failed");
                                return result = "The newValue is not valided!";
                            }
                        }

                    }
                    result = "fieldName doesn't match record type";
                    String operation = "edit: " + fieldName;
                    Log.log(Log.getCurrentTime(),centerName,managerId,operation,"Failed");
                }
            }
        }
        return  result;
    }

}
