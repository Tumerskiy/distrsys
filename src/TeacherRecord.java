import java.io.Serializable;

public class TeacherRecord extends Records implements Serializable {

    private String address;
    private int phone;
    private String specialiazation;
    private String location;

    public TeacherRecord(String firstName, String lastName, String address, int phone, String specialiazation, String location) {

        super(firstName, lastName);
        this.address = address;
        this.phone = phone;
        this.specialiazation = specialiazation;
        this.location = location;
    }

    public TeacherRecord() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
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
