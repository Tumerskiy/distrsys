import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface CenterServer extends Remote {
    public void createTRecord (String firstName,String lastName,String address,int phone,String specialization,String location) throws RemoteException;
    public void createSRecord (String firstName, String lastName, ArrayList<String> courseRegistered, String status, String statusDaten) throws RemoteException;
    public String getRecordCounts (String managerId) throws RemoteException;
    public void editRecord (String recordID, String fieldName,String newValue) throws RemoteException;
    public int getLocalRecordCount() throws RemoteException;

}
