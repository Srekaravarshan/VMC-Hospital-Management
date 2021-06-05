package hospital.java.sources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import hospital.java.models.Patient;
import hospital.java.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Datasource {

    private Datasource() {
    }

    public static final Datasource instance = new Datasource();

    private static ObservableList<Patient> patients;

    public static ObservableList<Patient> getPatients() {
        return patients;
    }

    public static final String DB_NAME = "database.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:D:\\java\\hospital_management\\" + DB_NAME;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "_id";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASS = "pass";

    public static final int INDEX_USER_ID = 1;
    public static final int INDEX_USER_EMAIL = 2;
    public static final int INDEX_USER_PASS = 3;

    public static final String TABLE_PATIENTS = "patients";
    public static final String COLUMN_PATIENT_ID = "_id";
    public static final String COLUMN_PATIENT_NAME = "name";
    public static final String COLUMN_PATIENT_ADDRESS = "address";
    public static final String COLUMN_PATIENT_FATHER_NAME = "father_name";
    public static final String COLUMN_PATIENT_MOTHER_NAME = "mother_name";

    public static final int INDEX_PATIENT_ID = 1;
    public static final int INDEX_PATIENT_NAME = 2;
    public static final int INDEX_PATIENT_ADDRESS = 3;
    public static final int INDEX_PATIENT_FATHER_NAME = 4;
    public static final int INDEX_PATIENT_MOTHER_NAME = 5;

    // SELECT * FROM users WHERE user_id = '';
    private final String GET_USER_BY_EMAIL = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_EMAIL + " = ?";

    // INSERT INTO users (user_id, email, pass) VALUES ('', '', '')
    private final String ADD_USER = "INSERT INTO " + TABLE_USERS + " (" + COLUMN_USER_EMAIL + ", " + COLUMN_USER_PASS
            + ") VALUES ( ?, ?)";

    // SELECT pass FROM users WHERE user_id = '';
    private final String GET_ENPASS = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_EMAIL + " = ?";

    public static final String QUERY_PATIENTS = "SELECT * FROM " + TABLE_PATIENTS + " ORDER BY " + COLUMN_PATIENT_NAME
            + " COLLATE NOCASE";

    // INSERT INTO patients (name, address, father_name, mother_name)
    // VALUES ( 'sre', 'Madurai', 'sre', 'sre')
    public static final String INSERT_PATIENT = "INSERT INTO " + TABLE_PATIENTS + " (" + COLUMN_PATIENT_NAME + ", "
            + COLUMN_PATIENT_ADDRESS + ", " + COLUMN_PATIENT_FATHER_NAME + ", " + COLUMN_PATIENT_MOTHER_NAME
            + ") VALUES (?, ?, ?, ?);";

    // SELECT * FROM patients WHERE _id = 1;
    public static final String GET_PATIENT_BY_ID = "SELECT * FROM " + TABLE_PATIENTS + " WHERE " + COLUMN_PATIENT_ID
            + " = ?";

    // DELETE FROM patients WHERE _id = 1;
    public static final String REMOVE_PATIENT_BY_ID = "DELETE FROM " + TABLE_PATIENTS + " WHERE " + COLUMN_PATIENT_ID
            + " = ?";

    // UPDATE Patients SET address = 'Chennai' WHERE roll_num = 10;
    public static final String UPDATE_PATIENT_BY_ID = "UPDATE " + TABLE_PATIENTS + " SET " + COLUMN_PATIENT_NAME
            + " = ?, " + COLUMN_PATIENT_ADDRESS + " = ?, "
            + COLUMN_PATIENT_FATHER_NAME + " = ?, " + COLUMN_PATIENT_MOTHER_NAME + " = ? WHERE " + COLUMN_PATIENT_ID
            + " = ?";

    private Connection conn;

    private PreparedStatement getUserByEmail;
    private PreparedStatement addUser;
    private PreparedStatement getEnPass;
    private PreparedStatement insertPatient;
    private PreparedStatement getPatientById;
    private PreparedStatement removePatientById;
    private PreparedStatement updatePatientById;

    public boolean open() {
        try {
            patients = FXCollections.observableArrayList();
            conn = DriverManager.getConnection(CONNECTION_STRING);
            getUserByEmail = conn.prepareStatement(GET_USER_BY_EMAIL);
            addUser = conn.prepareStatement(ADD_USER);
            getEnPass = conn.prepareStatement(GET_ENPASS);
            insertPatient = conn.prepareStatement(INSERT_PATIENT);
            getPatientById = conn.prepareStatement(GET_PATIENT_BY_ID);
            removePatientById = conn.prepareStatement(REMOVE_PATIENT_BY_ID);
            updatePatientById = conn.prepareStatement(UPDATE_PATIENT_BY_ID);

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
            if (getUserByEmail != null) {
                getUserByEmail.close();
            }
            if (addUser != null) {
                addUser.close();
            }
            if (getEnPass != null) {
                getEnPass.close();
            }
            if (insertPatient != null) {
                insertPatient.close();
            }
            if (getPatientById != null) {
                getPatientById.close();
            }
            if (removePatientById != null) {
                removePatientById.close();
            }
            if (updatePatientById != null) {
                updatePatientById.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public User register(String email, String pass) throws SQLException {
        if (userExists(email)) {
            return null;
        }
        addUser.setString(1, email);
        addUser.setString(2, pass);

        int affectedRows = addUser.executeUpdate();
        if (affectedRows != 1) {
            throw new SQLException("Can't Register User");
        }

        ResultSet generatedKeys = addUser.getGeneratedKeys();
        User user = new User();
        if (generatedKeys.next()) {
            user.setId(generatedKeys.getInt(1));
        } else {
            throw new SQLException("Couldn't get id for User");
        }

        user.setEmail(email);
        return user;
    }

    public boolean userExists(String email) throws SQLException {
        getUserByEmail.setString(1, email);
        ResultSet results = getUserByEmail.executeQuery();
        if (results.next()) {
            return true;
        }
        return false;

    }

    public HashMap<String, String> getEnPassword(String email) throws SQLException {
        getEnPass.setString(1, email);
        ResultSet result = getEnPass.executeQuery();
        HashMap<String, String> userData = new HashMap<>();
        if (result.next()) {
            userData.put("enpass", result.getString(INDEX_USER_PASS));
            userData.put("email", email);
            userData.put("id", String.valueOf(result.getInt(INDEX_USER_ID)));
        }
        return userData;
    }

    public List<Patient> queryPatients() {

        try (Statement statement = conn.createStatement()) {
            ResultSet results = statement.executeQuery(QUERY_PATIENTS);

            while (results.next()) {
                Patient patient = new Patient();
                patient.setId(results.getInt(INDEX_PATIENT_ID));
                patient.setName(results.getString(INDEX_PATIENT_NAME));
                patient.setAddress(results.getString(INDEX_PATIENT_ADDRESS));
                patient.setFatherName(results.getString(INDEX_PATIENT_FATHER_NAME));
                patient.setMotherName(results.getString(INDEX_PATIENT_MOTHER_NAME));

                patients.add(patient);
            }
            return patients;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public int insertPatient(String name, String address, String fatherName, String motherName) throws SQLException {

        insertPatient.setString(1, name);
        insertPatient.setString(2, address);
        insertPatient.setString(3, fatherName);
        insertPatient.setString(4, motherName);

        int affectedRows = insertPatient.executeUpdate();

        if (affectedRows != 1) {
            throw new SQLException("Couldn't insert Patient!");
        }

        ResultSet generatedKeys = insertPatient.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        } else {
            throw new SQLException("Couldn't get _id for album");
        }

    }

    public boolean deletePatient(int id) throws SQLException {
        getPatientById.setInt(1, id);
        ResultSet results = getPatientById.executeQuery();
        if (results.next()) {
            removePatientById.setInt(1, id);
            int affectedRows = removePatientById.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Couldn't delete Patient!");
            }
            return true;
        }
        return false;
    }

    public boolean updatePatient(Patient patient) {
        try {
            updatePatientById.setString(1, patient.getName());
            updatePatientById.setString(2, patient.getAddress());
            updatePatientById.setString(3, patient.getFatherName());
            updatePatientById.setString(4, patient.getMotherName());
            int affectedRows = updatePatientById.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Couldn't update Patient!");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isUnique(int id) {
        try {
            getPatientById.setInt(1, id);
            ResultSet results = getPatientById.executeQuery();
            if (results.next()) {
                return false;
            }
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

}
