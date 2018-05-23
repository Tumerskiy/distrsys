import java.io.Serializable;

public class TeacherRecord extends Records implements Serializable {

    private String address;
    private String phone;
    private String specialiazation;
    private String location;

    public TeacherRecord(String firstName, String lastName, String address, String phone, String specialiazation, String location) {

        super(firstName, lastName);
        this.address = address;
        this.phone = phone;
        this.specialiazation = specialiazation;
        this.location = location;
    }


    public String getAddress() {
        return address;
    }

    public synchronized void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public synchronized void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSpecialiazation() {
        return specialiazation;
    }

    public synchronized void addSpecialiazation(String specialiazation) {
        this.specialiazation = specialiazation;
    }

    public String getLocation() {
        return location;
    }

    public synchronized void setLocation(String location) {
        this.location = location;
    }
}
