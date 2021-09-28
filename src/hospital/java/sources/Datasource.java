package hospital.java.sources;

import hospital.java.helpers.MapString;
import hospital.java.models.CagModel;
import hospital.java.models.DoctorModel;
import hospital.java.models.Patient;
import hospital.java.models.PciModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.sqlite.mc.SQLiteMCConfig;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.List;

import static hospital.java.sources.CagTable.*;
import static hospital.java.sources.DoctorTable.*;
import static hospital.java.sources.PatientTable.*;
import static hospital.java.sources.PciTable.*;

public class Datasource {

    public static final Datasource instance = new Datasource();
    //    public static final String DB_NAME = "sql6418067";
    public static final String DB_NAME = "database.db";
    public static final String TRIGGER_DELETE = "del_patient";
    //    public static final String USER_NAME = "sql6418067";
//    public static final String PASSWORD = "Vf776vEWVS";
//    public static final String CONNECTION_STRING = "jdbc:mysql://sql6.freesqldatabase.com:3306/" + DB_NAME;

//    public static final String CONNECTION_STRING = "jdbc:sqlite:" + "\\\\192.162.1.6\\Hospital Management\\" + DB_NAME;
//    public static final String CONNECTION_STRING = "jdbc:sqlite:" + "\\\\LAPTOP-5991VD3Q\\Hospital Management\\" + DB_NAME;
//      public static final String CONNECTION_STRING = "jdbc:sqlite:" + "\\\\192.168.1.6\\Hospital Management\\database.db";

    //    public static final String CONNECTION_STRING = "jdbc:sqlite:" + "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Hospital Management\\" + DB_NAME;
    public static final String CREATE_DELETE_TRIGGER = "CREATE TRIGGER IF NOT EXISTS " +
            TRIGGER_DELETE +
            " AFTER DELETE ON " +
            TABLE_PATIENTS + " BEGIN DELETE FROM " +
            TABLE_CAG + " WHERE " +
            TABLE_CAG + "." +
            COLUMN_CAG_ID + "=OLD." +
            COLUMN_CAG_ID + "; DELETE FROM " +
            TABLE_PCI + " WHERE " +
            TABLE_PCI + "." +
            COLUMN_PCI_ID + "=OLD." +
            COLUMN_PCI_ID + "; END;";
    private static String ipAddress;
    private static ObservableList<Patient> patients;
    private static ObservableList<Patient> filteredPatients;
    private static ObservableList<DoctorModel> doctors;
    private static HashMap<Integer, CagModel> cags;
    private static HashMap<Integer, PciModel> pcis;
    public boolean isConnected = false;
    private Connection conn;
    private PreparedStatement insertPatient;
    private PreparedStatement insertDoctor;
    private PreparedStatement getPatientByUhid;
    private PreparedStatement getPatientByid;
    private PreparedStatement getDoctorByid;
    private PreparedStatement removePatientById;
    private PreparedStatement removeDoctorById;
    private PreparedStatement updatePatientById;
    private PreparedStatement updateDoctorById;
    private PreparedStatement updateCAG;
    private PreparedStatement updatePCI;
    private PreparedStatement insertCag;
    private PreparedStatement insertPci;
    private PreparedStatement queryCag;
    private PreparedStatement queryPci;

    private Datasource() {
    }

    public static ObservableList<DoctorModel> getDoctors() {
        return doctors;
    }

    public static ObservableList<Patient> getPatients() {
        return patients;
    }

    public static ObservableList<Patient> getFilteredPatients() {
        return filteredPatients;
    }

    public static HashMap<Integer, CagModel> getCags() {
        return cags;
    }

    public static HashMap<Integer, PciModel> getPcis() {
        return pcis;
    }

    public static void setIpAddress(String ipAddress) {
        Datasource.ipAddress = ipAddress;
    }

    public boolean checkIsConnected() {
        return isConnected;
    }

    public void open() throws SQLException, ClassNotFoundException {
//        public static final String CONNECTION_STRING = ;
        String CONNECTION_STRING;
        if (ipAddress.equals("0.0.0.0")) {
            CONNECTION_STRING = "jdbc:sqlite:" + "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\VMC Patient database v1.1.1\\" + DB_NAME;
        } else {
            CONNECTION_STRING = "jdbc:sqlite:" + "\\\\" + ipAddress + "\\VMC Patient database v1.1.1\\database.db";
        }

        File path = new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\VMC Patient database v1.1.1");
        if (!path.exists()) {
            path.mkdir();
        }

        System.out.println("jdbc:sqlite:" + "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\VMC Patient database v1.1.1\\" + DB_NAME);
        patients = FXCollections.observableArrayList();
        filteredPatients = FXCollections.observableArrayList();
        doctors = FXCollections.observableArrayList();
        cags = new HashMap<>();
        pcis = new HashMap<>();
        DriverManager.setLoginTimeout(5);
//        conn = DriverManager.getConnection(CONNECTION_STRING);
        conn = DriverManager.getConnection(CONNECTION_STRING, new SQLiteMCConfig().withKey("V3l@mm@l").toProperties());
//        conn = DriverManager.getConnection("jdbc:sqlite:" + "\\smb:\\\\"+ ipAddress +"\\VMC Patient database v1.1.1\\" + DB_NAME);
        this.isConnected = true;

        Statement statement = conn.createStatement();
        statement.executeUpdate(CREATE_TABLE_PATIENTS);
        statement.executeUpdate(CREATE_TABLE_CAG);
        statement.executeUpdate(CREATE_TABLE_PCI);
        statement.executeUpdate(CREATE_TABLE_DOCTORS);
        statement.executeUpdate(CREATE_DELETE_TRIGGER);

        try {
            insertPatient = conn.prepareStatement(INSERT_PATIENT);
            insertDoctor = conn.prepareStatement(INSERT_DOCTOR);
            getPatientByUhid = conn.prepareStatement(GET_PATIENT_BY_UHID);
            getPatientByid = conn.prepareStatement(GET_PATIENT_BY_ID);
            getDoctorByid = conn.prepareStatement(GET_DOCTOR_BY_ID);
            removePatientById = conn.prepareStatement(REMOVE_PATIENT_BY_ID);
            removeDoctorById = conn.prepareStatement(REMOVE_DOCTOR_BY_ID);
            updatePatientById = conn.prepareStatement(UPDATE_PATIENT_BY_ID);
            updateDoctorById = conn.prepareStatement(UPDATE_DOCTOR_BY_ID);
            updateCAG = conn.prepareStatement(UPDATE_CAG_BY_ID);
            updatePCI = conn.prepareStatement(UPDATE_PCI_BY_ID);

            insertCag = conn.prepareStatement(INSERT_CAG);
            insertPci = conn.prepareStatement(INSERT_PCI);
            queryCag = conn.prepareStatement(QUERY_CAG);
            queryPci = conn.prepareStatement(QUERY_PCI);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void close() throws SQLException {

        if (conn != null)
            conn.close();
        if (insertPatient != null)
            insertPatient.close();
        if (insertDoctor != null)
            insertDoctor.close();
        if (getPatientByUhid != null)
            getPatientByUhid.close();
        if (getPatientByid != null)
            getPatientByid.close();
        if (getDoctorByid != null)
            getDoctorByid.close();
        if (removePatientById != null)
            removePatientById.close();
        if (removeDoctorById != null)
            removeDoctorById.close();
        if (updatePatientById != null)
            updatePatientById.close();
        if (updateDoctorById != null)
            updateDoctorById.close();
        if (updateCAG != null)
            updateCAG.close();
        if (updatePCI != null)
            updatePCI.close();
        if (insertCag != null)
            insertCag.close();
        if (insertPci != null)
            insertPci.close();
        if (queryCag != null)
            queryCag.close();
        if (queryPci != null)
            queryPci.close();
    }

    public List<Patient> queryFilteredPatient(String query) throws SQLException, IOException, ClassNotFoundException {
        Statement statement = conn.createStatement();
        ResultSet results = statement.executeQuery(query);
        filteredPatients.clear();

        while (results.next()) {
            Patient filteredPatient = new Patient();
            filteredPatient.setId(results.getInt(INDEX_PATIENT_ID));
            filteredPatient.setName(results.getString(INDEX_PATIENT_NAME));
            filteredPatient.setUHID(results.getString(INDEX_PATIENT_UHID));
            filteredPatient.setAge(results.getInt(INDEX_PATIENT_AGE));
            filteredPatient.setSex(results.getString(INDEX_PATIENT_SEX));
            filteredPatient.setComplaints(results.getString(INDEX_PATIENT_COMPLAINTS));
            filteredPatient.setRiskFactors(results.getString(INDEX_PATIENT_RISK_FACTORS));
            filteredPatient.setOtherComorbidities(results.getString(INDEX_PATIENT_OTHER_COMORBIDITIES));
            filteredPatient.setCad(results.getString(INDEX_PATIENT_CAD));
            filteredPatient.setTreatmentForPastCad(results.getString(INDEX_PATIENT_TREAMENT_FOR_PAST_CAD));
            filteredPatient.setEcho(results.getString(INDEX_PATIENT_ECHO));
            filteredPatient.setCurrentDiagnosis(results.getString(INDEX_PATIENT_CURRENT_DIAGNOSIS));
            filteredPatient.setComplicationsInHospitalPredischarge(
                    results.getString(INDEX_PATIENT_COMPLICATIONS_PREDESCRIPTION));
            filteredPatient.setOperator(results.getString(INDEX_PATIENT_OPERATOR));

            filteredPatient.setComplaintsField(results.getString(INDEX_PATIENT_COMPLAINTS_VALUE));
            filteredPatient.setRiskFactorsField(results.getString(INDEX_PATIENT_RISK_FACTORS_VALUE));
            filteredPatient.setOtherComorbiditiesField(results.getString(INDEX_PATIENT_OTHER_COMORBIDITIES_VALUE));
            filteredPatient.setPastCadField(results.getString(INDEX_PATIENT_CAD_VALUE));
            filteredPatient.setTreatmentPastCadField(results.getString(INDEX_PATIENT_TREAMENT_FOR_PAST_CAD_VALUE));
            filteredPatient.setEchoField(results.getString(INDEX_PATIENT_ECHO_VALUE));
            filteredPatient.setCurrentDiagnosisField(results.getString(INDEX_PATIENT_CURRENT_DIAGNOSIS_VALUE));
            filteredPatient.setComplicationsField(results.getString(INDEX_PATIENT_COMPLICATIONS_PREDESCRIPTION_VALUE));
            filteredPatient.setDate((results.getDate(INDEX_PATIENT_DATE)).toLocalDate());
            filteredPatient.setAdditionalDetails(MapString.deserialize(results.getString(INDEX_PATIENT_ADD_DETAILS)));

            filteredPatient.setComplaintsAdditional(MapString.deserialize(results.getString(INDEX_PATIENT_COMPLAINTS_ADD)));
            filteredPatient.setRiskFactorsAdditional(MapString.deserialize(results.getString(INDEX_PATIENT_RISK_FACTORS_ADD)));
            filteredPatient.setOtherComorbiditiesAdditional(MapString.deserialize(results.getString(INDEX_PATIENT_OTHER_COMORBIDITIES_ADD)));
            filteredPatient.setPastCadAdditional(MapString.deserialize(results.getString(INDEX_PATIENT_CAD_ADD)));
            filteredPatient.setTreatmentPastCadAdditional(MapString.deserialize(results.getString(INDEX_PATIENT_TREAMENT_FOR_PAST_CAD_ADD)));
            filteredPatient.setEchoAdditional(MapString.deserialize(results.getString(INDEX_PATIENT_ECHO_ADD)));
            filteredPatient.setCurrentDiagnosisAdditional(MapString.deserialize(results.getString(INDEX_PATIENT_CURRENT_DIAGNOSIS_ADD)));
            filteredPatient.setComplicationsAdditional(MapString.deserialize(results.getString(INDEX_PATIENT_COMPLICATIONS_PREDESCRIPTION_ADD)));

            filteredPatient.setHasCag(results.getBoolean(INDEX_PATIENT_HAS_CAG));
            filteredPatient.setHasPci(results.getBoolean(INDEX_PATIENT_HAS_PCI));
            filteredPatient.setHasLmca(results.getBoolean(INDEX_PATIENT_HAS_LMCA));
            filteredPatient.setHasBifurication(results.getBoolean(INDEX_PATIENT_HAS_BIFURICATION));
            filteredPatient.setHasStentLad(results.getBoolean(INDEX_PATIENT_HAS_STENT_LAD));
            filteredPatient.setHasStentLcx(results.getBoolean(INDEX_PATIENT_HAS_STENT_LAX));
            filteredPatient.setHasStentRca(results.getBoolean(INDEX_PATIENT_HAS_STENT_RCA));
            filteredPatient.setHasStentLmca(results.getBoolean(INDEX_PATIENT_HAS_STENT_LMCA));
            filteredPatient.setHasStentDiagonal(results.getBoolean(INDEX_PATIENT_HAS_STENT_DIAGONAL));
            filteredPatient.setHasStentOm(results.getBoolean(INDEX_PATIENT_HAS_STENT_DM));

            filteredPatient.setDopCag(results.getDate(INDEX_CAG_DATE_OF_PROCEDURE + 42) == null ? null : (results.getDate(INDEX_CAG_DATE_OF_PROCEDURE + 42)).toLocalDate());
            filteredPatient.setDopPci(results.getDate(INDEX_PCI_DATE_OF_PROCEDURE + 42 + 27) == null ? null : (results.getDate(INDEX_PCI_DATE_OF_PROCEDURE + 42 + 27)).toLocalDate());

            filteredPatients.add(filteredPatient);
        }
        return filteredPatients;
    }

    public int queryProcedure(String query) {
        try {
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery(query);
            return results.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
    }

    public List<DoctorModel> queryDoctors() throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet results = statement.executeQuery(QUERY_DOCTORS);
        doctors.clear();

        while (results.next()) {
            DoctorModel doctor = new DoctorModel();
            doctor.setId(results.getInt(INDEX_DOCTOR_ID));
            doctor.setName(results.getString(INDEX_DOCTOR_NAME));
            doctor.setQualification(results.getString(INDEX_DOCTOR_QUALIFICATION));
            doctor.setDepartment(results.getString(INDEX_DOCTOR_DEPARTMENT));
            doctor.setRegNo(results.getString(INDEX_DOCTOR_REG_NO));
            doctor.setHospital(results.getString(INDEX_DOCTOR_HOSPITAL));
            doctor.setDistrict(results.getString(INDEX_DOCTOR_DISTRICT));
            doctor.setPincode(results.getString(INDEX_DOCTOR_PINCODE));
            doctor.setMobile(results.getString(INDEX_DOCTOR_MOBILE));
            doctor.setEmail(results.getString(INDEX_DOCTOR_EMAIL));

            doctors.add(doctor);
        }
        return doctors;
    }

    public List<Patient> queryPatients() throws SQLException {

        Statement statement = conn.createStatement();
        ResultSet results = statement.executeQuery(QUERY_PATIENTS);

        while (results.next()) {
            try {
                Patient patient = new Patient();
                patient.setId(results.getInt(INDEX_PATIENT_ID));
                patient.setName(results.getString(INDEX_PATIENT_NAME));
                patient.setUHID(results.getString(INDEX_PATIENT_UHID));
                patient.setAge(results.getInt(INDEX_PATIENT_AGE));
                patient.setSex(results.getString(INDEX_PATIENT_SEX));
                patient.setComplaints(results.getString(INDEX_PATIENT_COMPLAINTS));
                patient.setRiskFactors(results.getString(INDEX_PATIENT_RISK_FACTORS));
                patient.setOtherComorbidities(results.getString(INDEX_PATIENT_OTHER_COMORBIDITIES));
                patient.setCad(results.getString(INDEX_PATIENT_CAD));
                patient.setTreatmentForPastCad(results.getString(INDEX_PATIENT_TREAMENT_FOR_PAST_CAD));
                patient.setEcho(results.getString(INDEX_PATIENT_ECHO));
                patient.setCurrentDiagnosis(results.getString(INDEX_PATIENT_CURRENT_DIAGNOSIS));
                patient.setComplicationsInHospitalPredischarge(
                        results.getString(INDEX_PATIENT_COMPLICATIONS_PREDESCRIPTION));
                patient.setOperator(results.getString(INDEX_PATIENT_OPERATOR));

                patient.setComplaintsField(results.getString(INDEX_PATIENT_COMPLAINTS_VALUE));
                patient.setRiskFactorsField(results.getString(INDEX_PATIENT_RISK_FACTORS_VALUE));
                patient.setOtherComorbiditiesField(results.getString(INDEX_PATIENT_OTHER_COMORBIDITIES_VALUE));
                patient.setPastCadField(results.getString(INDEX_PATIENT_CAD_VALUE));
                patient.setTreatmentPastCadField(results.getString(INDEX_PATIENT_TREAMENT_FOR_PAST_CAD_VALUE));
                patient.setEchoField(results.getString(INDEX_PATIENT_ECHO_VALUE));
                patient.setCurrentDiagnosisField(results.getString(INDEX_PATIENT_CURRENT_DIAGNOSIS_VALUE));
                patient.setComplicationsField(results.getString(INDEX_PATIENT_COMPLICATIONS_PREDESCRIPTION_VALUE));
                patient.setDate((results.getDate(INDEX_PATIENT_DATE)).toLocalDate());
                patient.setAdditionalDetails(MapString.deserialize(results.getString(INDEX_PATIENT_ADD_DETAILS)));

                patient.setComplaintsAdditional(MapString.deserialize(results.getString(INDEX_PATIENT_COMPLAINTS_ADD)));
                patient.setRiskFactorsAdditional(MapString.deserialize(results.getString(INDEX_PATIENT_RISK_FACTORS_ADD)));
                patient.setOtherComorbiditiesAdditional(MapString.deserialize(results.getString(INDEX_PATIENT_OTHER_COMORBIDITIES_ADD)));
                patient.setPastCadAdditional(MapString.deserialize(results.getString(INDEX_PATIENT_CAD_ADD)));
                patient.setTreatmentPastCadAdditional(MapString.deserialize(results.getString(INDEX_PATIENT_TREAMENT_FOR_PAST_CAD_ADD)));
                patient.setEchoAdditional(MapString.deserialize(results.getString(INDEX_PATIENT_ECHO_ADD)));
                patient.setCurrentDiagnosisAdditional(MapString.deserialize(results.getString(INDEX_PATIENT_CURRENT_DIAGNOSIS_ADD)));
                patient.setComplicationsAdditional(MapString.deserialize(results.getString(INDEX_PATIENT_COMPLICATIONS_PREDESCRIPTION_ADD)));

                patient.setHasCag(results.getBoolean(INDEX_PATIENT_HAS_CAG));
                patient.setHasPci(results.getBoolean(INDEX_PATIENT_HAS_PCI));
                patient.setHasLmca(results.getBoolean(INDEX_PATIENT_HAS_LMCA));
                patient.setHasBifurication(results.getBoolean(INDEX_PATIENT_HAS_BIFURICATION));
                patient.setHasStentLad(results.getBoolean(INDEX_PATIENT_HAS_STENT_LAD));
                patient.setHasStentLcx(results.getBoolean(INDEX_PATIENT_HAS_STENT_LAX));
                patient.setHasStentRca(results.getBoolean(INDEX_PATIENT_HAS_STENT_RCA));
                patient.setHasStentLmca(results.getBoolean(INDEX_PATIENT_HAS_STENT_LMCA));
                patient.setHasStentDiagonal(results.getBoolean(INDEX_PATIENT_HAS_STENT_DIAGONAL));
                patient.setHasStentOm(results.getBoolean(INDEX_PATIENT_HAS_STENT_DM));

                System.out.println("Datasource " + patient.getName());
                patients.add(patient);
                System.out.println("Datasource " + patients.size());

                queryCag(patient.getId());
                queryPci(patient.getId());

                System.out.println("patient added");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return patients;
    }

    public void queryCag(int id) throws SQLException, IOException, ClassNotFoundException {

        queryCag.setInt(1, id);
        ResultSet results = queryCag.executeQuery();

        while (results.next()) {
            CagModel cag = new CagModel();

            cag.setAccessRoute(results.getString(INDEX_CAG_ACCESS_ROUTE));
            cag.setAccessRouteField(results.getString(INDEX_CAG_ACCESS_ROUTE_VALUE));
            cag.setAccessRouteAdditional(MapString.deserialize(results.getString(INDEX_PCI_ACCESS_ROUTE_ADD)));
            cag.setBp_1(results.getString(INDEX_CAG_BP_1));
            cag.setBp_2(results.getString(INDEX_CAG_BP_2));
            cag.setBp_3(results.getString(INDEX_CAG_BP_3));
            cag.setHr(results.getString(INDEX_CAG_HR));
            cag.setLmca(results.getString(INDEX_CAG_LMCA));
            cag.setLad(results.getString(INDEX_CAG_LAD));
            cag.setD1(results.getString(INDEX_CAG_D1));
            cag.setLcx(results.getString(INDEX_CAG_LCX));
            cag.setOm(results.getString(INDEX_CAG_OM));
            cag.setRca(results.getString(INDEX_CAG_RCA));
            cag.setDiagnosis(results.getString(INDEX_CAG_DIAGNOSIS));
            cag.setBifurcation(results.getString(INDEX_CAG_BIFURCATION));
            cag.setCalcification(results.getString(INDEX_CAG_CALCIFICATION));
            cag.setRestenosis(results.getString(INDEX_CAG_RESTENOSIS));
            cag.setLmcaDisease(results.getString(INDEX_CAG_LMCA_DISEASE));
            cag.setLmcaDiseaseField(results.getString(INDEX_CAG_LMCA_DISEASE_VALUE));
            cag.setBipurificationDisease(results.getString(INDEX_CAG_BIPURIFICATION_DISEASE));
            cag.setBipurificationDiseaseField(results.getString(INDEX_CAG_BIPURIFICATION_DISEASE_VALUE));
            cag.setMiscellaneous(results.getString(INDEX_CAG_MISCELLANEOUS));
            cag.setFinalImpression(results.getString(INDEX_CAG_FINAL_IMPRESSION));
            cag.setRecommendation(results.getString(INDEX_CAG_RECOMMENDATION));
            cag.setDateOfProcedure(results.getDate(INDEX_CAG_DATE_OF_PROCEDURE) == null ? null : (results.getDate(INDEX_CAG_DATE_OF_PROCEDURE)).toLocalDate());
            cag.setAdditionalDetails(MapString.deserialize(results.getString(INDEX_CAG_ADD_DETAILS)));

            cag.setId(id);

            cags.put(id, cag);
        }
    }

    public void queryPci(int id) throws SQLException, IOException, ClassNotFoundException {

        queryPci.setInt(1, id);
        ResultSet results = queryPci.executeQuery();

        while (results.next()) {
            PciModel pci = new PciModel();

            pci.setAccessRoute(results.getString(INDEX_PCI_ACCESS_ROUTE));
            pci.setAccessRouteField(results.getString(INDEX_PCI_ACCESS_ROUTE_VALUE));
            pci.setAccessRouteAdditional(MapString.deserialize(results.getString(INDEX_PCI_ACCESS_ROUTE_ADD)));
            pci.setBp_1(results.getString(INDEX_PCI_BP_1));
            pci.setBp_2(results.getString(INDEX_PCI_BP_2));
            pci.setBp_3(results.getString(INDEX_PCI_BP_3));
            pci.setHr(results.getString(INDEX_PCI_HR));
            pci.setCatheter(results.getString(INDEX_PCI_CATHETER));
            pci.setWire(results.getString(INDEX_PCI_WIRE));
            pci.setThrombus(results.getString(INDEX_PCI_THROMBUS));
            pci.setPredilation(results.getString(INDEX_PCI_PREDILATION));
            pci.setStent(results.getString(INDEX_PCI_STENT));
            pci.setStentField(results.getString(INDEX_PCI_STENT_VALUE));
            pci.setStentAdditional(MapString.deserialize(results.getString(INDEX_PCI_STENT_ADD)));
            pci.setPostDilation(results.getString(INDEX_PCI_POST_DILATION));
            pci.setTimiFlow(results.getString(INDEX_PCI_TIMI_FLOW));
            pci.setTimeFirstMedical(results.getString(INDEX_PCI_TIME_FIRST_MEDICAL));
            pci.setDbt(results.getString(INDEX_PCI_DBT));
            pci.setComplications(results.getString(INDEX_PCI_COMPLICATIONS));
            pci.setBifurcation(results.getString(INDEX_PCI_BIFURCATION));
            pci.setLmca(results.getString(INDEX_PCI_LMCA));
            pci.setSpecialBalloons(results.getString(INDEX_PCI_SPECIAL_BALLONS));
            pci.setGuideCatheter(results.getString(INDEX_PCI_GUIDE_CATHETER));
            pci.setIvusFindings(results.getString(INDEX_PCI_IVUS_FINDINGS));
            pci.setOctFindings(results.getString(INDEX_PCI_OCT_FINDINGS));
            pci.setRotablation(results.getString(INDEX_PCI_ROTABLATION));
            pci.setLmcaDisease(results.getString(INDEX_PCI_LMCA_DISEASE));
            pci.setLmcaDiseaseField(results.getString(INDEX_PCI_LMCA_DISEASE_VALUE));
            pci.setBipurificationDisease(results.getString(INDEX_PCI_BIPURIFICATION_DISEASE));
            pci.setBipurificationDiseaseField(results.getString(INDEX_PCI_BIPURIFICATION_DISEASE_VALUE));
            pci.setSyntaxScore(results.getString(INDEX_PCI_SYNTAX_SCORE));
            pci.setFinalImpression(results.getString(INDEX_PCI_FINAL_IMPRESSION));
            pci.setDateOfProcedure(results.getDate(INDEX_PCI_DATE_OF_PROCEDURE) == null ? null : (results.getDate(INDEX_PCI_DATE_OF_PROCEDURE)).toLocalDate());
            pci.setAdditionalDetails(MapString.deserialize(results.getString(INDEX_PCI_ADD_DETAILS)));

            pci.setId(id);

            pcis.put(id, pci);
        }

    }

    public int insertDoctor(DoctorModel doctor) throws SQLException {
        insertDoctor.setString(1, doctor.getName());
        insertDoctor.setString(2, doctor.getQualification());
        insertDoctor.setString(3, doctor.getDepartment());
        insertDoctor.setString(4, doctor.getRegNo());
        insertDoctor.setString(5, doctor.getHospital());
        insertDoctor.setString(6, doctor.getDistrict());
        insertDoctor.setString(7, doctor.getPincode());
        insertDoctor.setString(8, doctor.getMobile());
        insertDoctor.setString(9, doctor.getEmail());

        int affectedRows = insertDoctor.executeUpdate();

        if (affectedRows != 1)
            throw new SQLException("Couldn't insert Patient!");

        ResultSet generatedKeys = insertPatient.getGeneratedKeys();
        if (generatedKeys.next()) {
            PageSource.form.page1Controller().operatorField.getItems().add(doctor.getName());
            return generatedKeys.getInt(1);
        } else {
            throw new SQLException("Couldn't get _id for album");
        }
    }

    public int insertPatient(Patient patient)
            throws SQLException, IOException {

        insertPatient.setString(1, patient.getName());
        insertPatient.setString(2, patient.getUHID());
        insertPatient.setInt(3, patient.getAge());
        insertPatient.setString(4, patient.getSex());
        insertPatient.setString(5, patient.getComplaints());
        insertPatient.setString(6, patient.getComplaintsField());
        insertPatient.setString(7, MapString.serialize(patient.getComplaintsAdditional()));
        insertPatient.setString(8, patient.getRiskFactors());
        insertPatient.setString(9, patient.getRiskFactorsField());
        insertPatient.setString(10, MapString.serialize(patient.getRiskFactorsAdditional()));
        insertPatient.setString(11, patient.getOtherComorbidities());
        insertPatient.setString(12, patient.getOtherComorbiditiesField());
        insertPatient.setString(13, MapString.serialize(patient.getOtherComorbiditiesAdditional()));
        insertPatient.setString(14, patient.getCad());
        insertPatient.setString(15, patient.getPastCadField());
        insertPatient.setString(16, MapString.serialize(patient.getPastCadAdditional()));
        insertPatient.setString(17, patient.getTreatmentForPastCad());
        insertPatient.setString(18, patient.getTreatmentPastCadField());
        insertPatient.setString(19, MapString.serialize(patient.getTreatmentPastCadAdditional()));
        insertPatient.setString(20, patient.getEcho());
        insertPatient.setString(21, patient.getEchoField());
        insertPatient.setString(22, MapString.serialize(patient.getEchoAdditional()));
        insertPatient.setString(23, patient.getCurrentDiagnosis());
        insertPatient.setString(24, patient.getCurrentDiagnosisField());
        insertPatient.setString(25, MapString.serialize(patient.getCurrentDiagnosisAdditional()));
        insertPatient.setString(26, patient.getComplicationsInHospitalPredischarge());
        insertPatient.setString(27, patient.getComplicationsField());
        insertPatient.setString(28, MapString.serialize(patient.getComplicationsAdditional()));
        insertPatient.setString(29, patient.getOperator());
        insertPatient.setDate(30, (Date.valueOf(patient.getDate())));
        insertPatient.setString(31, MapString.serialize(patient.getAdditionalDetails()));
        insertPatient.setBoolean(32, patient.isHasCag());
        insertPatient.setBoolean(33, patient.isHasPci());
        insertPatient.setBoolean(34, patient.isHasLmca());
        insertPatient.setBoolean(35, patient.isHasBifurication());
        insertPatient.setBoolean(36, patient.isHasStentLad());
        insertPatient.setBoolean(37, patient.isHasStentLcx());
        insertPatient.setBoolean(38, patient.isHasStentRca());
        insertPatient.setBoolean(39, patient.isHasStentLmca());
        insertPatient.setBoolean(40, patient.isHasStentDiagonal());
        insertPatient.setBoolean(41, patient.isHasStentOm());

        int affectedRows = insertPatient.executeUpdate();

        if (affectedRows != 1)
            throw new SQLException("Couldn't insert Patient!");

        ResultSet generatedKeys = insertPatient.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        } else {
            throw new SQLException("Couldn't get _id for album");
        }

    }

    public void insertCagPci(int id, CagModel cag, PciModel pci) throws SQLException, IOException {


        insertCag.setString(1, cag.getAccessRoute());
        insertCag.setString(2, cag.getAccessRouteField());
        insertCag.setString(3, MapString.serialize(cag.getAccessRouteAdditional()));
        insertCag.setString(4, cag.getBp_1());
        insertCag.setString(5, cag.getBp_2());
        insertCag.setString(6, cag.getBp_3());
        insertCag.setString(7, cag.getHr());
        insertCag.setString(8, cag.getLmca());
        insertCag.setString(9, cag.getLad());
        insertCag.setString(10, cag.getD1());
        insertCag.setString(11, cag.getLcx());
        insertCag.setString(12, cag.getOm());
        insertCag.setString(13, cag.getRca());
        insertCag.setString(14, cag.getDiagnosis());
        insertCag.setString(15, cag.getBifurcation());
        insertCag.setString(16, cag.getCalcification());
        insertCag.setString(17, cag.getRestenosis());
        insertCag.setString(18, cag.getLmcaDisease());
        insertCag.setString(19, cag.getLmcaDiseaseField());
        insertCag.setString(20, cag.getBipurificationDisease());
        insertCag.setString(21, cag.getBipurificationDiseaseField());
        insertCag.setString(22, cag.getMiscellaneous());
        insertCag.setString(23, cag.getFinalImpression());
        insertCag.setString(24, cag.getRecommendation());
        insertCag.setDate(25, cag.getDateOfProcedure() == null ? null : (Date.valueOf(cag.getDateOfProcedure())));
        insertCag.setString(26, MapString.serialize(cag.getAdditionalDetails()));
        insertCag.setInt(27, id);

//        System.out.println("insertCag finished");
        System.out.println(insertCag.toString());


        insertPci.setString(1, pci.getAccessRoute());
        insertPci.setString(2, pci.getAccessRouteField());
        insertPci.setString(3, MapString.serialize(pci.getAccessRouteAdditional()));
        insertPci.setString(4, pci.getBp_1());
        insertPci.setString(5, pci.getBp_2());
        insertPci.setString(6, pci.getBp_3());
        insertPci.setString(7, pci.getHr());
        insertPci.setString(8, pci.getCatheter());
        insertPci.setString(9, pci.getWire());
        insertPci.setString(10, pci.getThrombus());
        insertPci.setString(11, pci.getPredilation());
        insertPci.setString(12, pci.getStent());
        insertPci.setString(13, pci.getStentField());
        insertPci.setString(14, MapString.serialize(pci.getStentAdditional()));
        insertPci.setString(15, pci.getPostDilation());
        insertPci.setString(16, pci.getTimiFlow());
        insertPci.setString(17, pci.getTimeFirstMedical());
        insertPci.setString(18, pci.getDbt());
        insertPci.setString(19, pci.getComplications());
        insertPci.setString(20, pci.getBifurcation());
        insertPci.setString(21, pci.getLmca());
        insertPci.setString(22, pci.getSpecialBalloons());
        insertPci.setString(23, pci.getGuideCatheter());
        insertPci.setString(24, pci.getIvusFindings());
        insertPci.setString(25, pci.getOctFindings());
        insertPci.setString(26, pci.getRotablation());
        insertPci.setString(27, pci.getLmcaDisease());
        insertPci.setString(28, pci.getLmcaDiseaseField());
        insertPci.setString(29, pci.getBipurificationDisease());
        insertPci.setString(30, pci.getBipurificationDiseaseField());
        insertPci.setString(31, pci.getSyntaxScore());
        insertPci.setString(32, pci.getFinalImpression());
        insertPci.setDate(33, pci.getDateOfProcedure() == null ? null : (Date.valueOf(pci.getDateOfProcedure())));
        insertPci.setString(34, MapString.serialize(pci.getAdditionalDetails()));
        insertPci.setInt(35, id);
        System.out.println(insertPci.toString());

        int cagAffectedRows = insertCag.executeUpdate();
        int pciAffectedRows = insertPci.executeUpdate();
        if (cagAffectedRows != 1)
            throw new SQLException("Couldn't insert CAG!");
        if (pciAffectedRows != 1)
            throw new SQLException("Couldn't insert PCI!");

        cags.put(id, cag);
        pcis.put(id, pci);
    }

    public boolean deleteDoctor(DoctorModel doctor) throws SQLException {
        getDoctorByid.setInt(1, doctor.getId());
        ResultSet results = getDoctorByid.executeQuery();
        if (results.next()) {
            removeDoctorById.setInt(1, doctor.getId());
            int affectedRows = removeDoctorById.executeUpdate();

            if (affectedRows != 1)
                throw new SQLException("Couldn't delete Doctor!");

            PageSource.form.page1Controller().operatorField.getItems().remove(doctor.getName());
            return true;
        }
        return false;
    }

    public boolean deletePatient(int id) throws SQLException {

        getPatientByid.setInt(1, id);
        ResultSet results = getPatientByid.executeQuery();
        if (results.next()) {
            removePatientById.setInt(1, id);
            int affectedRows = removePatientById.executeUpdate();

            if (affectedRows != 1)
                throw new SQLException("Couldn't delete Patient!");
            return true;
        }
        return false;
    }

    public boolean updateDoctor(DoctorModel doctor, String oldName) throws SQLException {
        updateDoctorById.setString(1, doctor.getName());
        updateDoctorById.setString(2, doctor.getQualification());
        updateDoctorById.setString(3, doctor.getDepartment());
        updateDoctorById.setString(4, doctor.getRegNo());
        updateDoctorById.setString(5, doctor.getHospital());
        updateDoctorById.setString(6, doctor.getDistrict());
        updateDoctorById.setString(7, doctor.getPincode());
        updateDoctorById.setString(8, doctor.getMobile());
        updateDoctorById.setString(9, doctor.getEmail());
        updateDoctorById.setInt(10, doctor.getId());
        int affectedRows = updateDoctorById.executeUpdate();
        if (affectedRows != 1) {
            throw new SQLException("Couldn't update Doctor!");
        }
        PageSource.form.page1Controller().operatorField.getItems().set(PageSource.form.page1Controller().operatorField.getItems().indexOf(oldName), doctor.getName());

        return true;
    }

    public boolean updatePatient(Patient patient, CagModel cag, PciModel pci) {

        try {
            updatePatientById.setString(1, patient.getName());
            updatePatientById.setString(2, patient.getUHID());
            updatePatientById.setInt(3, patient.getAge());
            updatePatientById.setString(4, patient.getSex());
            updatePatientById.setString(5, patient.getComplaints());
            updatePatientById.setString(6, patient.getComplaintsField());
            updatePatientById.setString(7, MapString.serialize(patient.getComplaintsAdditional()));
            updatePatientById.setString(8, patient.getRiskFactors());
            updatePatientById.setString(9, patient.getRiskFactorsField());
            updatePatientById.setString(10, MapString.serialize(patient.getRiskFactorsAdditional()));
            updatePatientById.setString(11, patient.getOtherComorbidities());
            updatePatientById.setString(12, patient.getOtherComorbiditiesField());
            updatePatientById.setString(13, MapString.serialize(patient.getOtherComorbiditiesAdditional()));
            updatePatientById.setString(14, patient.getCad());
            updatePatientById.setString(15, patient.getPastCadField());
            updatePatientById.setString(16, MapString.serialize(patient.getPastCadAdditional()));
            updatePatientById.setString(17, patient.getTreatmentForPastCad());
            updatePatientById.setString(18, patient.getTreatmentPastCadField());
            updatePatientById.setString(19, MapString.serialize(patient.getTreatmentPastCadAdditional()));
            updatePatientById.setString(20, patient.getEcho());
            updatePatientById.setString(21, patient.getEchoField());
            updatePatientById.setString(22, MapString.serialize(patient.getEchoAdditional()));
            updatePatientById.setString(23, patient.getCurrentDiagnosis());
            updatePatientById.setString(24, patient.getCurrentDiagnosisField());
            updatePatientById.setString(25, MapString.serialize(patient.getCurrentDiagnosisAdditional()));
            updatePatientById.setString(26, patient.getComplicationsInHospitalPredischarge());
            updatePatientById.setString(27, patient.getComplicationsField());
            updatePatientById.setString(28, MapString.serialize(patient.getComplicationsAdditional()));
            updatePatientById.setString(29, patient.getOperator());
            updatePatientById.setDate(30, Date.valueOf(patient.getDate()));
            updatePatientById.setString(31, MapString.serialize(patient.getAdditionalDetails()));
            updatePatientById.setBoolean(32, patient.isHasCag());
            updatePatientById.setBoolean(33, patient.isHasPci());
            updatePatientById.setBoolean(34, patient.isHasLmca());
            updatePatientById.setBoolean(35, patient.isHasBifurication());
            updatePatientById.setBoolean(36, patient.isHasStentLad());
            updatePatientById.setBoolean(37, patient.isHasStentLcx());
            updatePatientById.setBoolean(38, patient.isHasStentRca());
            updatePatientById.setBoolean(39, patient.isHasStentLmca());
            updatePatientById.setBoolean(40, patient.isHasStentDiagonal());
            updatePatientById.setBoolean(41, patient.isHasStentOm());
            updatePatientById.setInt(42, patient.getId());

            updatePCI.setString(1, pci.getAccessRoute());
            updatePCI.setString(2, pci.getAccessRouteField());
            updatePCI.setString(3, MapString.serialize(pci.getAccessRouteAdditional()));
            updatePCI.setString(4, pci.getBp_1());
            updatePCI.setString(5, pci.getBp_2());
            updatePCI.setString(6, pci.getBp_3());
            updatePCI.setString(7, pci.getHr());
            updatePCI.setString(8, pci.getCatheter());
            updatePCI.setString(9, pci.getWire());
            updatePCI.setString(10, pci.getThrombus());
            updatePCI.setString(11, pci.getPredilation());
            updatePCI.setString(12, pci.getStent());
            updatePCI.setString(13, pci.getStentField());
            updatePCI.setString(14, MapString.serialize(pci.getStentAdditional()));
            updatePCI.setString(15, pci.getPostDilation());
            updatePCI.setString(16, pci.getTimiFlow());
            updatePCI.setString(17, pci.getTimeFirstMedical());
            updatePCI.setString(18, pci.getDbt());
            updatePCI.setString(19, pci.getComplications());
            updatePCI.setString(20, pci.getBifurcation());
            updatePCI.setString(21, pci.getLmca());
            updatePCI.setString(22, pci.getSpecialBalloons());
            updatePCI.setString(23, pci.getGuideCatheter());
            updatePCI.setString(24, pci.getIvusFindings());
            updatePCI.setString(25, pci.getOctFindings());
            updatePCI.setString(26, pci.getRotablation());
            updatePCI.setString(27, pci.getLmcaDisease());
            updatePCI.setString(28, pci.getLmcaDiseaseField());
            updatePCI.setString(29, pci.getBipurificationDisease());
            updatePCI.setString(30, pci.getBipurificationDiseaseField());
            updatePCI.setString(31, pci.getSyntaxScore());
            updatePCI.setString(32, pci.getFinalImpression());
            updatePCI.setDate(33, pci.getDateOfProcedure() == null ? null : Date.valueOf(pci.getDateOfProcedure()));
            updatePCI.setString(34, MapString.serialize(pci.getAdditionalDetails()));
            updatePCI.setInt(35, pci.getId());

            updateCAG.setString(1, cag.getAccessRoute());
            updateCAG.setString(2, cag.getAccessRouteField());
            updateCAG.setString(3, MapString.serialize(cag.getAccessRouteAdditional()));
            updateCAG.setString(4, cag.getBp_1());
            updateCAG.setString(5, cag.getBp_2());
            updateCAG.setString(6, cag.getBp_3());
            updateCAG.setString(7, cag.getHr());
            updateCAG.setString(8, cag.getLmca());
            updateCAG.setString(9, cag.getLad());
            updateCAG.setString(10, cag.getD1());
            updateCAG.setString(11, cag.getLcx());
            updateCAG.setString(12, cag.getOm());
            updateCAG.setString(13, cag.getRca());
            updateCAG.setString(14, cag.getDiagnosis());
            updateCAG.setString(15, cag.getBifurcation());
            updateCAG.setString(16, cag.getCalcification());
            updateCAG.setString(17, cag.getRestenosis());
            updateCAG.setString(18, cag.getLmcaDisease());
            updateCAG.setString(19, cag.getLmcaDiseaseField());
            updateCAG.setString(20, cag.getBipurificationDisease());
            updateCAG.setString(21, cag.getBipurificationDiseaseField());
            updateCAG.setString(22, cag.getMiscellaneous());
            updateCAG.setString(23, cag.getFinalImpression());
            updateCAG.setString(24, cag.getRecommendation());
            updateCAG.setDate(25, cag.getDateOfProcedure() == null ? null : Date.valueOf(cag.getDateOfProcedure()));
            updateCAG.setString(26, MapString.serialize(cag.getAdditionalDetails()));
            updateCAG.setInt(27, cag.getId());

            System.out.println(updateCAG.toString());
            System.out.println(updatePCI.toString());

            int affectedRows = updatePatientById.executeUpdate();
            if (affectedRows != 1) {
                throw new SQLException("Couldn't update Patient!");
            }

            affectedRows = updatePCI.executeUpdate();
            if (affectedRows != 1) {
                throw new SQLException("Couldn't update pci!");
            }
            affectedRows = updateCAG.executeUpdate();
            if (affectedRows != 1) {
                throw new SQLException("Couldn't update cag!");
            }
            return true;
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    public boolean isUnique(String uhid) {
        try {
            getPatientByUhid.setString(1, uhid);
            ResultSet results = getPatientByUhid.executeQuery();
            return !results.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
