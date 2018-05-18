import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class CenterSystem extends UnicastRemoteObject implements CenterServer {

    private static ConcurrentHashMap<Character,ArrayList<Records>> database = new ConcurrentHashMap<>();

    public CenterSystem() throws RemoteException {
        super();
    }

    public void createTRecord(String managerId,String firstName, String lastName, String address, int phone, String specialization, String location) throws RemoteException {
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
//    @Override
//    public void createTRecord(String managerId,String firstName, String lastName, String address, int phone, String specialization, String location) throws RemoteException {
//        TeacherRecord teacherRecord = new TeacherRecord(firstName,lastName,address,phone,specialization,location);
//        CenterRepositry.addToCenter(managerId,teacherRecord);
//        Date date = new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd--HH:mm:ss");
//        String dateTime = simpleDateFormat.format(date);
//    }

//    @Override
//    public void createSRecord(String managerId,String firstName, String lastName, String courseRegistered, String status, String statusDate) throws RemoteException {
//        StudentRecord studentRecord = new StudentRecord(firstName,lastName, courseRegistered, status, statusDate);
//        CenterRepositry.addToCenter(managerId,studentRecord);
//    }

//    @Override
    public int getRecordCounts(String managerId) throws RemoteException {
        return 0;
    }

//    @Override
    public void editRecord(String managerId,String recordID, String fieldName, String newValue) throws RemoteException {

    }

//    public static void main(String[] args) {
//        if (System.getSecurityManager() == null){
//            System.setSecurityManager(new SecurityManager());
//        }
//        try{
//
//        }catch (Exception e){
//
//        }
//    }
}
