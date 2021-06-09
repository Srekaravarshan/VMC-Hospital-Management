package hospital.java.sources;

import hospital.java.models.Patient;
import hospital.java.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class Datasource {

    private Datasource() {
    }

    public static final Datasource instance = new Datasource();

    private static ObservableList<Patient> patients;

    public static ObservableList<Patient> getPatients() {
        return patients;
    }

    public static final String DB_NAME = "database.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\" + DB_NAME;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "_id";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASS = "pass";

    public static final int INDEX_USER_ID = 1;
    public static final int INDEX_USER_EMAIL = 2;
    public static final int INDEX_USER_PASS = 3;

    public static final String TABLE_PATIENTS = "patients";
    public static final String COLUMN_PATIENT_ID = "id";
    public static final String COLUMN_PATIENT_NAME = "name";
    public static final String COLUMN_PATIENT_UHID = "uhid";
    public static final String COLUMN_PATIENT_AGE = "age";
    public static final String COLUMN_PATIENT_SEX = "sex";
    public static final String COLUMN_PATIENT_RISK_FACTORS = "risk_factors";
    public static final String COLUMN_PATIENT_OTHER_COMORBIDITIES = "other_comorbidities";
    public static final String COLUMN_PATIENT_CAD = "cad";
    public static final String COLUMN_PATIENT_TREAMENT_FOR_PAST_CAD = "treatment_for_past_cad";
    public static final String COLUMN_PATIENT_ECHO = "echo";
    public static final String COLUMN_PATIENT_CURRENT_DIAGNOSIS = "current_diagnosis";
    public static final String COLUMN_PATIENT_CORONARY_ANGIOGRAPHY = "coronary_angiography";
    public static final String COLUMN_PATIENT_PCI = "pci";
    public static final String COLUMN_PATIENT_COMPLICATIONS_PREDESCRIPTION = "complications_in_hospital_predischarge";
    public static final String COLUMN_PATIENT_POST_PCI = "post_pci";

    public static final int INDEX_PATIENT_ID = 1;
    public static final int INDEX_PATIENT_NAME = 2;
    public static final int INDEX_PATIENT_UHID = 3;
    public static final int INDEX_PATIENT_AGE = 4;
    public static final int INDEX_PATIENT_SEX = 5;
    public static final int INDEX_PATIENT_RISK_FACTORS = 6;
    public static final int INDEX_PATIENT_OTHER_COMORBIDITIES = 7;
    public static final int INDEX_PATIENT_CAD = 8;
    public static final int INDEX_PATIENT_TREAMENT_FOR_PAST_CAD = 9;
    public static final int INDEX_PATIENT_ECHO = 10;
    public static final int INDEX_PATIENT_CURRENT_DIAGNOSIS = 11;
    public static final int INDEX_PATIENT_CORONARY_ANGIOGRAPHY = 12;
    public static final int INDEX_PATIENT_PCI = 13;
    public static final int INDEX_PATIENT_COMPLICATIONS_PREDESCRIPTION = 14;
    public static final int INDEX_PATIENT_POST_PCI = 15;


    // CREATE TABLE patients ( _id INTEGER PRIMARY KEY, name TEXT NOT NULL, address TEXT, father_name TEXT, mother_name TEXT );
    private final String CREATE_TABLE_PATIENTS = "CREATE TABLE IF NOT EXISTS " +
            TABLE_PATIENTS + " ( " +
            COLUMN_PATIENT_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_PATIENT_NAME + " TEXT, " +
            COLUMN_PATIENT_UHID + " VARCHAR(20), " +
            COLUMN_PATIENT_AGE + " INTEGER, " +
            COLUMN_PATIENT_SEX + " VARCHAR(10), " +
            COLUMN_PATIENT_RISK_FACTORS + " TEXT, " +
            COLUMN_PATIENT_OTHER_COMORBIDITIES + " TEXT, " +
            COLUMN_PATIENT_CAD + " TEXT, " +
            COLUMN_PATIENT_TREAMENT_FOR_PAST_CAD + " TEXT, " +
            COLUMN_PATIENT_ECHO + " TEXT, " +
            COLUMN_PATIENT_CURRENT_DIAGNOSIS + " TEXT, " +
            COLUMN_PATIENT_CORONARY_ANGIOGRAPHY + " TEXT, " +
            COLUMN_PATIENT_PCI + " TEXT, " +
            COLUMN_PATIENT_COMPLICATIONS_PREDESCRIPTION + " TEXT, " +
            COLUMN_PATIENT_POST_PCI + " TEXT );";

    // CREATE TABLE users ( _id INTEGER PRIMARY key, email TEXT UNIQUE NOT NULL, pass TEXT NOT NULL );
    private final String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS " +
            TABLE_USERS + " ( " +
            COLUMN_USER_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_USER_EMAIL + " TEXT UNIQUE NOT NULL, " +
            COLUMN_USER_PASS + " TEXT NOT NULL );";

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
    public static final String INSERT_PATIENT = "INSERT INTO " + TABLE_PATIENTS + " ("
            + COLUMN_PATIENT_NAME + ", "
            + COLUMN_PATIENT_UHID + ", "
            + COLUMN_PATIENT_AGE + ", "
            + COLUMN_PATIENT_SEX + ", "
            + COLUMN_PATIENT_RISK_FACTORS + ", "
            + COLUMN_PATIENT_OTHER_COMORBIDITIES + ", "
            + COLUMN_PATIENT_CAD + ", "
            + COLUMN_PATIENT_TREAMENT_FOR_PAST_CAD + ", "
            + COLUMN_PATIENT_ECHO + ", "
            + COLUMN_PATIENT_CURRENT_DIAGNOSIS + ", "
            + COLUMN_PATIENT_CORONARY_ANGIOGRAPHY + ", "
            + COLUMN_PATIENT_PCI + ", "
            + COLUMN_PATIENT_COMPLICATIONS_PREDESCRIPTION + ", "
            + COLUMN_PATIENT_POST_PCI
            + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";


    // SELECT * FROM patients WHERE _id = 1;
    public static final String GET_PATIENT_BY_ID = "SELECT * FROM " + TABLE_PATIENTS + " WHERE " + COLUMN_PATIENT_ID
            + " = ?";

    // DELETE FROM patients WHERE _id = 1;
    public static final String REMOVE_PATIENT_BY_ID = "DELETE FROM " + TABLE_PATIENTS + " WHERE " + COLUMN_PATIENT_ID
            + " = ?";

    // UPDATE Patients SET address = 'Chennai' WHERE roll_num = 10;
    public static final String UPDATE_PATIENT_BY_ID = "UPDATE " + TABLE_PATIENTS + " SET "
            + COLUMN_PATIENT_NAME + " = ?, "
            + COLUMN_PATIENT_UHID + " = ?, "
            + COLUMN_PATIENT_AGE + " = ?, "
            + COLUMN_PATIENT_SEX + " = ?, "
            + COLUMN_PATIENT_RISK_FACTORS + " = ?, "
            + COLUMN_PATIENT_OTHER_COMORBIDITIES + " = ?, "
            + COLUMN_PATIENT_CAD + " = ?, "
            + COLUMN_PATIENT_TREAMENT_FOR_PAST_CAD + " = ?, "
            + COLUMN_PATIENT_ECHO + " = ?, "
            + COLUMN_PATIENT_CURRENT_DIAGNOSIS + " = ?, "
            + COLUMN_PATIENT_CORONARY_ANGIOGRAPHY + " = ?, "
            + COLUMN_PATIENT_PCI + " = ?, "
            + COLUMN_PATIENT_COMPLICATIONS_PREDESCRIPTION + " = ?, "
            + COLUMN_PATIENT_POST_PCI + " = ? WHERE "

            + COLUMN_PATIENT_ID + " = ?";


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

            Statement statement = conn.createStatement();
            int results = statement.executeUpdate(CREATE_TABLE_USERS);
            results = statement.executeUpdate(CREATE_TABLE_PATIENTS);

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
            System.out.println(generatedKeys.getInt(1));
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
        return results.next();

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
                patient.setUHID(results.getString(INDEX_PATIENT_UHID));
                patient.setAge(results.getInt(INDEX_PATIENT_AGE));
                patient.setSex(results.getString(INDEX_PATIENT_SEX));
                patient.setRiskFactors(results.getString(INDEX_PATIENT_RISK_FACTORS));
                patient.setOtherComorbidities(results.getString(INDEX_PATIENT_OTHER_COMORBIDITIES));
                patient.setCad(results.getString(INDEX_PATIENT_CAD));
                patient.setTreatmentForPastCad(results.getString(INDEX_PATIENT_TREAMENT_FOR_PAST_CAD));
                patient.setEcho(results.getString(INDEX_PATIENT_ECHO));
                patient.setCurrentDiagnosis(results.getString(INDEX_PATIENT_CURRENT_DIAGNOSIS));
                patient.setCoronaryAngiography(results.getString(INDEX_PATIENT_CORONARY_ANGIOGRAPHY));
                patient.setPci(results.getString(INDEX_PATIENT_PCI));
                patient.setComplicationsInHospitalPredischarge(
                        results.getString(INDEX_PATIENT_COMPLICATIONS_PREDESCRIPTION));
                patient.setPostPci(results.getString(INDEX_PATIENT_POST_PCI));

                patients.add(patient);
            }
            return patients;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public int insertPatient(String name, String UHID, int age, String sex, String riskFactors,
                             String otherComorbidities, String cad, String treatmentForPastCad, String echo, String currentDiagnosis,
                             String coronaryAngiography, String pci, String complicationsInHospitalPredischarge, String postPci)
            throws SQLException {

        insertPatient.setString(1, name);
        insertPatient.setString(2, UHID);
        insertPatient.setInt(3, age);
        insertPatient.setString(4, sex);
        insertPatient.setString(5, riskFactors);
        insertPatient.setString(6, otherComorbidities);
        insertPatient.setString(7, cad);
        insertPatient.setString(8, treatmentForPastCad);
        insertPatient.setString(9, echo);
        insertPatient.setString(10, currentDiagnosis);
        insertPatient.setString(11, coronaryAngiography);
        insertPatient.setString(12, pci);
        insertPatient.setString(13, complicationsInHospitalPredischarge);
        insertPatient.setString(14, postPci);

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
            updatePatientById.setString(1, patient.getName());
            updatePatientById.setString(2, patient.getUHID());
            updatePatientById.setInt(3, patient.getAge());
            updatePatientById.setString(4, patient.getSex());
            updatePatientById.setString(5, patient.getRiskFactors());
            updatePatientById.setString(6, patient.getOtherComorbidities());
            updatePatientById.setString(7, patient.getCad());
            updatePatientById.setString(8, patient.getTreatmentForPastCad());
            updatePatientById.setString(9, patient.getEcho());
            updatePatientById.setString(10, patient.getCurrentDiagnosis());
            updatePatientById.setString(11, patient.getCoronaryAngiography());
            updatePatientById.setString(12, patient.getPci());
            updatePatientById.setString(13, patient.getComplicationsInHospitalPredischarge());
            updatePatientById.setString(14, patient.getPostPci());
            updatePatientById.setInt(15, patient.getId());
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
            return !results.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

}
