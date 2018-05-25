import java.io.Serializable;
import java.util.Random;

public class TeacherRecord extends Records implements Serializable {

    private String address;
    private String phone;
    private String specialiazation;
    private String location;

    public TeacherRecord(String firstName, String lastName, String address, String phone, String specialiazation, String location) {

        super(firstName, lastName);
        this.recordID = genRecordID();
        this.address = address;
        this.phone = phone;
        this.specialiazation = specialiazation;
        this.location = location;
    }

    @Override
    public String genRecordID(){
        String recordId = "TR";
        String chars = "1234567890";
        while (recordId.length()<6){
            recordId+=chars.charAt(new Random().nextInt(chars.length()));
        }
        return recordId;
    }

    @Override
    public void regenRecordID() {
        this.recordID = genRecordID();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSpecialiazation() {
        return specialiazation;
    }

    public void addSpecialiazation(String specialiazation) {
        this.specialiazation = specialiazation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
