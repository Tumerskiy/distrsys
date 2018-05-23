import java.io.Serializable;
import java.util.ArrayList;

public class StudentRecord extends Records implements Serializable {

    private ArrayList<String> coursesRegistered;
    private String status;
    private String statusDate;

    public ArrayList<String> getCoursesRegistered() {
        return coursesRegistered;
    }

    public synchronized void setCoursesRegistered(ArrayList<String> coursesRegistered) {
        this.coursesRegistered = coursesRegistered;
    }

    public String getStatus() {
        return status;
    }

    public synchronized void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDate() {
        return statusDate;
    }

    public synchronized void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    public StudentRecord(String firstName, String lastName, ArrayList<String> coursesRegistered, String status, String statusDate) {
        super(firstName, lastName);
        this.coursesRegistered=coursesRegistered;
        this.status = status;
        this.statusDate = statusDate;
    }
}
