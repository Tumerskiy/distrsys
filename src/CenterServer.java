import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CenterServer extends Remote {
    public void createTRecord (String managerId,String firstName,String lastName,String address,int phone,String specialization,String location) throws RemoteException;
    public void createSRecord (String managerId,String firstName,String lastName,String courseRegistered, String status, String statusDaten) throws RemoteException;
    public String getRecordCounts (String managerId) throws RemoteException;
    public void editRecord ( String managerId,String recordID, String fieldName,String newValue) throws RemoteException;
    public int getLocalRecordCount() throws RemoteException;

}
