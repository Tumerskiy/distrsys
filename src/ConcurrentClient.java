import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class ConcurrentClient {
    public static void main(String[] args) throws Exception {
        new ConcurrentClient().scan();
    }

    public void scan() throws Exception {
        File file=new File("src/operation.txt");
        InputStreamReader reader=new InputStreamReader(new FileInputStream(file));
        BufferedReader input=new BufferedReader(reader);
        String line=input.readLine();
        Registry registry= LocateRegistry.getRegistry("localhost", 2000);
        while (line!=null){
            String[] parameters=line.split("\\|");
            if (!verifyId(parameters[0])){
                line=input.readLine();
                continue;
            }
            CenterServer stub=(CenterServer) registry.lookup(parameters[0].substring(0,3));
            CompletableFuture<String> result;
            switch (parameters[1]) {
                case "createTRecord": {
                    result=createTRecord(stub,parameters);
                    result.thenAccept(s-> System.out.println("Create Successful, recordId is "+s));
                    break;
                }
                case "createSRecord": {
                    result=createSRecord(stub,parameters);
                    result.thenAccept(s-> System.out.println("Create Successful, recordId is "+s));
                    break;
                }
                case "getRecordCounts": {
                    result=getRecordCounts(stub,parameters[0]);
                    result.thenAccept(s-> System.out.println("Record number is "+s));
                    break;
                }
                case "editRecord": {
                    result=editRecord(stub, parameters);
                    result.thenAccept(s-> System.out.println(s));
                    break;
                }
                case "Exit": {
                    System.out.println("GoodBye.");
                    break;
                }
            }
            line=input.readLine();
        }
    }

    public CompletableFuture<String> createTRecord(CenterServer stub, String[] parameters) throws RemoteException {
        String firstName = parameters[2];
        String lastName = parameters[3];
        String address = parameters[4];
        String specialiazation=parameters[5];
        String location = parameters[6];
        String phone =  parameters[7];
        CompletableFuture<String> future=new CompletableFuture<>();
        new Thread(()->{
            try {
                String recordId=stub.createTRecord(parameters[0], firstName, lastName, address, phone, specialiazation, location);
                future.complete(recordId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }).start();
        return future;
    }

    public CompletableFuture<String> createSRecord(CenterServer stub, String[] parameters) throws RemoteException {
        String firstName = parameters[2];
        String lastName = parameters[3];
        String status = parameters[4];
        String statusDate = parameters[5];
        ArrayList<String> coursesRegistered = new ArrayList<>();
        for (String s :
                parameters[6].split(" ")) {
            coursesRegistered.add(s);
        }
        CompletableFuture<String> future=new CompletableFuture<>();
        new Thread(()->{
            try {
                String recordId=stub.createSRecord(parameters[0], firstName, lastName, coursesRegistered, status, statusDate);
                future.complete(recordId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }).start();
        return future;
    }

    public CompletableFuture<String> getRecordCounts(CenterServer stub, String managerId) throws RemoteException, NotBoundException {
        CompletableFuture<String> future=new CompletableFuture<>();
        new Thread(()->{
            try {
                String counts=stub.getRecordCounts(managerId);
                future.complete(counts);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        }).start();
        return future;
    }

    public CompletableFuture<String> editRecord(CenterServer stub, String[] parameters) throws Exception {
        String recordId = parameters[2];
        String fieldName = parameters[3];
        String newValue = parameters[4];
        CompletableFuture<String> future=new CompletableFuture<>();
        new Thread(()->{
            try {
                String result=stub.editRecord(parameters[0], recordId, fieldName, newValue);;
                future.complete(result);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        return future;
    }

    public boolean verifyId(String managerId) {
        String addr = managerId.substring(0, 3);
        if (addr.equals("MTL") || addr.equals("LVL") || addr.equals("DDO")) {
            return true;
        } else {
            return false;
        }
    }
}
