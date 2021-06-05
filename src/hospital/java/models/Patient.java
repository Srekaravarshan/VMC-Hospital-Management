package hospital.java.models;

public class Patient {
    private int id;
    private String name;
    private String address;
    private String fatherName;
    private String motherName;
    
    public static String getDetailsString(Patient patient) {
        return patient.getName()
                + "\n\nAddress: " + patient.getAddress()
                + "\n\nFather's Name: "+ patient.getFatherName()
                + "\nMother's Name: "+ patient.getMotherName();
    }

    public String getMotherName() {
        return motherName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getFatherName() {
        return fatherName;
    }
    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }
}
