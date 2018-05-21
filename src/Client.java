import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String args[]) throws Exception {

        /*
        using scanner get manager name, check if subs(0,3) within MTL LVL DDO
        set serverName based on that
        */
        /*
        some method calls part
        in form of:
        mtlServer.createTRecord(a,b,c,d...)

        probably client should be interactive, and consume input with Scanner
        based on which we should make case{} for called methods
         */

        /*
        TODO:
         Figure out how to simulate several managers use the server concurrently.
         */
        new Client().scan();
    }

    public void scan() throws Exception {
        Registry registry = LocateRegistry.getRegistry();
        CenterServer stub;
        String managerId = "";
        boolean ifContinue = true;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please input your manager ID:");
            managerId = scanner.nextLine();
            if (!verifyId(managerId)) {
                System.out.println("ManagerId error. Please input again");
                continue;
            }
            switch (managerId.substring(0, 3)) {
                case "MTL": {
                    stub = (CenterServer) registry.lookup("MTL");
                    ifContinue = processOperation(stub,managerId);
                    break;
                }
                case "LVL": {
                    stub = (CenterServer) registry.lookup("LVL");
                    ifContinue = processOperation(stub,managerId);
                    break;
                }
                case "DDO": {
                    stub = (CenterServer) registry.lookup("DDO");
                    ifContinue = processOperation(stub,managerId);
                    break;
                }
            }
        } while (ifContinue);
    }

    public boolean processOperation(CenterServer stub, String managerId) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int option = -1;
        System.out.println("Please select your operation:");
        System.out.println("1> Create Teacher Record.");
        System.out.println("2> Create Student Record.");
        System.out.println("3> Get Record Counts.");
        System.out.println("4> Edit Record.");
        System.out.println("5> Exit.");
        option = scanner.nextInt();
        switch (option) {
            case 1: {
                createTRecord(stub,managerId);
                break;
            }
            case 2: {
                createSRecord(stub,managerId);
                break;
            }
            case 3: {
                getRecordCounts(stub,managerId);
                break;
            }
            case 4: {
                editRecord(stub, managerId);
                break;
            }
            case 5: {
                System.out.println("GoodBye.");
                break;
            }
        }
        if (option == 5) return false;
        else return true;
    }

    public void createTRecord(CenterServer stub, String managerId) throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        TeacherRecord teacher = new TeacherRecord();
        System.out.println("Please input teacher's first name:");
        teacher.setFirstName(scanner.nextLine().trim());
        System.out.println("Please input teacher's last name:");
        teacher.setLastName(scanner.nextLine().trim());
        System.out.println("Please input teacher's address");
        teacher.setAddress(scanner.nextLine().trim());
        System.out.println("Please input your teacher's specialization:");
        teacher.addSpecialiazation(scanner.nextLine().trim());
        System.out.println("Please input teacher's location:");
        teacher.setLocation(scanner.nextLine().trim());
        System.out.println("Please input teacher's phone:");
        teacher.setPhone(scanner.nextInt());
        stub.createTRecord(managerId, teacher.getFirstName(), teacher.getLastName(), teacher.getAddress(), teacher.getPhone(), teacher.getSpecialiazation(), teacher.getLocation());
    }

    public void createSRecord(CenterServer stub, String managerId) throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        StudentRecord student = new StudentRecord();
        System.out.println("Please input student's first name:");
        student.setFirstName(scanner.nextLine().trim());
        System.out.println("Please input student's last name:");
        student.setLastName(scanner.nextLine().trim());
        System.out.println("Please input student's status:");
        student.setStatus(scanner.nextLine().trim());
        System.out.println("Please input your student's statusDate:");
        student.setStatusDate(scanner.nextLine().trim());
        System.out.println("Please input student's courses(split with space):");
        for (String s :
                scanner.nextLine().split(" ")) {
            student.getCoursesRegistered().add(s);
        }
        stub.createSRecord(managerId, student.getFirstName(), student.getLastName(), student.getCoursesRegistered(), student.getStatus(), student.getStatusDate());
    }

    public void getRecordCounts(CenterServer stub, String managerId) throws RemoteException, NotBoundException {
        System.out.println(stub.getRecordCounts(managerId));
    }

    public void editRecord(CenterServer stub, String managerId) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input your record id:");
        String recordId = scanner.nextLine().trim();
        System.out.println("Please input the field name you want to change:");
        String fieldName = scanner.nextLine().trim();
        System.out.println("Please input new value:");
        String newValue = scanner.nextLine().trim();
        stub.editRecord(managerId, recordId, fieldName, newValue);
    }


    public boolean verifyId(String managerId) throws Exception {
        String addr = managerId.substring(0, 3);
        if (addr.equals("MTL") || addr.equals("LVL") || addr.equals("DDO")) {
            return true;
        } else {
            return false;
        }
    }
}
