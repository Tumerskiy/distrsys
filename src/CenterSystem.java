import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Date;
public class CenterSystem implements CenterServer {

    public CenterSystem() {
        super();
    }

    @Override
    public void createTRecord(String managerId,String firstName, String lastName, String address, int phone, String specialization, String location) throws RemoteException {
        TeacherRecord teacherRecord = new TeacherRecord(firstName,lastName,address,phone,specialization,location);
        CenterRepositry.addToCenter(managerId,teacherRecord);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd--HH:mm:ss");
        String dateTime = simpleDateFormat.format(date);

        


    }

    @Override
    public void createSRecord(String managerId,String firstName, String lastName, String courseRegistered, String status, String statusDaten) throws RemoteException {

    }

    @Override
    public int getRecordCounts(String managerId) throws RemoteException {
        return 0;
    }

    @Override
    public void editRecord(String managerId,String recordID, String fieldName, String newValue) throws RemoteException {

    }

    public static void main(String[] args) {
        if (System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        try{

        }catch (Exception e){

        }
    }
}
