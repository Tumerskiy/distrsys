import java.beans.*;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class CenterSystem extends UnicastRemoteObject implements CenterServer {
    private String centerName = "";
    protected HashMap<Character,ArrayList<Records>> database = new HashMap<>();
    private UDPServer udpServer;
    private static Registry centerRegistry;
    private static int randomId=9999;
    private int portNumber;

    static {
        try {
            centerRegistry = LocateRegistry.getRegistry("localhost", 2000);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public int getPortNumber() {
        return portNumber;
    }


    public CenterSystem(int portNumber) throws RemoteException {
        super();
        this.portNumber = portNumber;
        udpServer = new UDPServer(portNumber,this);
        new Thread(udpServer).start();
    }

    public UDPServer getUdpServer() {
        return udpServer;
    }

    public void setUdpServer(UDPServer udpServer) {
        this.udpServer = udpServer;
        new Thread(udpServer).start();
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    private void validateRecordId(Records inRecord, char key) {
        String recordId = inRecord.getRecordID();
        if (database.get(key) != null) {
            for (Records record : database.get(key)) {
                if (record.getRecordID().equals(recordId)) {
                    inRecord.regenRecordID();
                    validateRecordId(inRecord, key);
                    break;
                }
            }
        }
    }

    public String createTRecord(String managerId, String firstName, String lastName, String address, String phone, String specialization, String location) throws RemoteException {
        TeacherRecord teacherRecord = new TeacherRecord(firstName, lastName, address, phone, specialization, location);
        char key = lastName.charAt(0);
        validateRecordId(teacherRecord, key);
        synchronized (this) {
            if (database.get(key) == null) {
                ArrayList<Records> value = new ArrayList<>();
                value.add(teacherRecord);
                database.put(key, value);
            } else {
                ArrayList<Records> value = database.get(key);
                value.add(teacherRecord);
                database.put(key, value);
            }
        }
        Log.log(Log.getCurrentTime(), managerId, "createTRecord", "Create successfully! Record ID is "+teacherRecord.getRecordID());
        return teacherRecord.getRecordID();
    }

    public String createSRecord(String managerId, String firstName, String lastName, ArrayList<String> courseRegistered, String status, String statusDate) throws RemoteException {
        StudentRecord studentRecord = new StudentRecord(firstName, lastName, courseRegistered, status, statusDate);
        char key = lastName.charAt(0);
        validateRecordId(studentRecord, key);
        if (database.get(key) == null) {
            ArrayList<Records> value = new ArrayList<>();
            value.add(studentRecord);
            database.put(key, value);
        } else {
            ArrayList<Records> value = database.get(key);
            value.add(studentRecord);
            database.put(key, value);
        }
        Log.log(Log.getCurrentTime(), managerId, "createSRecord", "Create successfully! Record ID is "+studentRecord.getRecordID());
        return studentRecord.getRecordID();
    }

//    public String getRecordCounts(String managerId) throws RemoteException, NotBoundException {
//        String result = "";
//        Registry registry = LocateRegistry.getRegistry("localhost", 2000);
//        String[] servers = registry.list();
//        for (String server : servers) {
//            CenterServer curServer = (CenterServer) registry.lookup(server);
//            result += server + ":" + curServer.getLocalRecordCount() + " ";
//        }
//        Log.log(Log.getCurrentTime(), managerId, "getRecordCounts", result);
//        return result;
//    }


    public String getRecordCounts(String managerId) throws RemoteException, NotBoundException {
        String result = "";
        result += "MTL:" + UDPClient.request("getCount","localhost",8180) + ", LVL:" + UDPClient.request("getCount","localhost",8181) +", DDO:" +UDPClient.request("getCount","localhost",8182);
        System.out.printf("\n"+result);
        //Log.log(Log.getCurrentTime(),centerName,managerId,"getRecordCounts, Successful");
        return result;
    }

    public int getLocalRecordCount() throws RemoteException {
        int sum=0;
        for (ArrayList<Records> records:
             database.values()) {
            sum+=records.size();
        }
        return sum;
    }

    public String editRecord(String managerId, String recordID, String fieldName, String newValue) throws Exception {
        String result = "";
        Boolean ableModified = true;

        for (char key : database.keySet()) {
            for (Records record : database.get(key)) {
                if (record.getRecordID().equals(recordID)) {
                    /* following reads information about the object, more precisely of its class, into BeanInfo
                     not sure if in this particular case it will get proper specific class of record, eg StudentRecord or
                     Teacher record, it could take its superclass Record...
                    */
                    BeanInfo recordInfo = Introspector.getBeanInfo(record.getClass());

                    /*
                    recordPds in this case is the array of properties available in this class
                     */
                    PropertyDescriptor[] recordPds = recordInfo.getPropertyDescriptors();
                    for (PropertyDescriptor prop : recordPds) {
                        if (prop.getName().equals(fieldName)) {
                            if (fieldName.equals("location")) {
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
                                Log.log(Log.getCurrentTime(), managerId, operation, result);
                                return result;
                            } else {
                                String operation = "edit: " + prop.getName();
                                result = "The newValue is not valid!";
                                Log.log(Log.getCurrentTime(), managerId, operation, result);
                                return result;
                            }
                        }

                    }
                    result = "fieldName doesn't match record type";
                    String operation = "edit: " + fieldName;
                    Log.log(Log.getCurrentTime(), managerId, operation, result);
                } else{
                    result = "No such record Id for this manager";
                    Log.log(Log.getCurrentTime(), managerId, "edit: " + fieldName, result);
                }
            }
        }
        return result;
    }

}
