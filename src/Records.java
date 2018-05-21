import java.io.Serializable;
import java.util.ArrayList;

public  class  Records implements Serializable {
    private String firstName;
    private String lastName;
    protected String recordID;

    public Records(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
//        this.recordID = recordID;
    }

    public Records() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

}
