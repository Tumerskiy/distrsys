import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CenterServer extends Remote {
    public void createTRecord (String firstName,String lastName,String address,int phone,String specialization,String location) throws RemoteException;
    public void createSRecord (String firstName,String lastName,String courseRegistered, String status, String statusDaten) throws RemoteException;
    public int getRecordCounts () throws RemoteException;
    public void editRecord ( String recordID, String fieldName,String newValue) throws RemoteException;

}
