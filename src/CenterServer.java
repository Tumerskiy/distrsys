import java.beans.IntrospectionException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface CenterServer extends Remote {
    public String createTRecord (String managerId,String firstName,String lastName,String address,String phone,String specialization,String location) throws RemoteException;
    public String createSRecord (String managerId,String firstName,String lastName,ArrayList<String> courseRegistered, String status, String statusDaten) throws RemoteException;
    public String getRecordCounts (String managerId) throws RemoteException, NotBoundException;
    public String editRecord ( String managerId,String recordID, String fieldName,String newValue) throws Exception;
    public int getLocalRecordCount() throws RemoteException;

}
