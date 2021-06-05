package hospital.java.sources;

public class Usersource {
    
    private Usersource () {}
    public static final Usersource instance = new Usersource();

    private String email;
    private int id;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
