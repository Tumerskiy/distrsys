import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class CenterSystem extends UnicastRemoteObject implements CenterServer {

    protected  ConcurrentHashMap<Character,ArrayList<Records>> database = new ConcurrentHashMap<>();
    private static Registry centerRegistry;

    /*
    Agreed with this one, since we do LocateRegistry we can keep it here and have it static.
     */
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

    public ConcurrentHashMap<Character, ArrayList<Records>> getDatabase() {
        return this.database;
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

    public String getRecordCounts(String managerId) throws RemoteException {
        HashMap<String,Integer> hashMap = new HashMap<>();
        String result = "";
        String[] serversName = centerRegistry.list();
        for (String name : serversName) {

            if (name.charAt(0) == managerId.charAt(0)){
                hashMap.put(name,this.getLocalRecordCount());
            }else {
                CenterSystem centerSystem = null;
                try {
                    centerSystem = (CenterSystem) Naming.lookup(name);
                } catch (NotBoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                hashMap.put(name,centerSystem.getLocalRecordCount());
            }
        }
        for (String s : hashMap.keySet()) {
            result += s + ":" + hashMap.get(s) + "  ";
        }


        return result;
    }

    //for test purposes
    public int getLocalRecordCount() throws RemoteException{
        int count = 0;
        for (Character character : database.keySet()) {
            count += database.get(character).size();
        }
        return count;
    }
//    @Override
//    public int[] getRecordCounts(String managerId) throws RemoteException {
//        int[] result = {0,0,0};
//        String[] serversName = centerRegistry.list();
//
//        if (managerId.charAt(0) == 'M'){
//            for (Character character : database.keySet()) {
//                result[0] += database.get(character).size();
//            }
//            CenterSystem laval = null;
//            try {
//                laval = (CenterSystem) Naming.lookup("LVL");
//            } catch (NotBoundException e) {
//                e.printStackTrace();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//            for (Character character : laval.getDatabase().keySet()) {
//                result[1] += database.get(character).size();
//            }
//            CenterSystem dollardDesOrmeaux = null;
//            try {
//                dollardDesOrmeaux = (CenterSystem) Naming.lookup("DDO");
//            } catch (NotBoundException e) {
//                e.printStackTrace();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//            for (Character character : dollardDesOrmeaux.getDatabase().keySet()) {
//                result[2] += database.get(character).size();
//            }
//
//        }else if (managerId.charAt(0) == 'L'){
//            for (Character character : database.keySet()) {
//                result[1] += database.get(character).size();
//            }
//            CenterSystem montreal = null;
//            try {
//                montreal = (CenterSystem) Naming.lookup("MTL");
//            } catch (NotBoundException e) {
//                e.printStackTrace();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//            for (Character character : montreal.getDatabase().keySet()) {
//                result[0] += database.get(character).size();
//            }
//            CenterSystem dollardDesOrmeaux = null;
//            try {
//                dollardDesOrmeaux = (CenterSystem) Naming.lookup("DDO");
//            } catch (NotBoundException e) {
//                e.printStackTrace();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//            for (Character character : dollardDesOrmeaux.getDatabase().keySet()) {
//                result[2] += database.get(character).size();
//            }
//
//        }else if (managerId.charAt(0) == 'D'){
//            for (Character character : database.keySet()) {
//                result[2] += database.get(character).size();
//            }
//            CenterSystem montreal = null;
//            try {
//                montreal = (CenterSystem) Naming.lookup("MTL");
//            } catch (NotBoundException e) {
//                e.printStackTrace();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//            for (Character character : montreal.getDatabase().keySet()) {
//                result[0] += database.get(character).size();
//            }
//            CenterSystem laval = null;
//            try {
//                laval = (CenterSystem) Naming.lookup("LVL");
//            } catch (NotBoundException e) {
//                e.printStackTrace();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//            for (Character character : laval.getDatabase().keySet()) {
//                result[1] += database.get(character).size();
//            }
//        }
//        return result;
//    }

//    @Override
    public void editRecord(String managerId,String recordID, String fieldName, String newValue) throws RemoteException {

    }

    public static void registry(String name,CenterSystem centerSystem){
        try {
            centerRegistry.rebind(name,centerSystem);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
