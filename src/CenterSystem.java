import java.rmi.RemoteException;

public class CenterSystem implements CenterServer {

    public CenterSystem() {
        super();
    }

    @Override
    public void createTRecord(String firstName, String lastName, String address, int phone, String specialization, String location) throws RemoteException {

    }

    @Override
    public void createSRecord(String firstName, String lastName, String courseRegistered, String status, String statusDaten) throws RemoteException {

    }

    @Override
    public int getRecordCounts() throws RemoteException {
        return 0;
    }

    @Override
    public void editRecord(String recordID, String fieldName, String newValue) throws RemoteException {

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
