package hospital.java.sources;

public class PatientTable {
    public static final String TABLE_PATIENTS = "patients";
    public static final String COLUMN_PATIENT_ID = "id";
    public static final String COLUMN_PATIENT_NAME = "name";
    public static final String COLUMN_PATIENT_UHID = "uhid";
    public static final String COLUMN_PATIENT_AGE = "age";
    public static final String COLUMN_PATIENT_SEX = "sex";
    public static final String COLUMN_PATIENT_COMPLAINTS = "complaints";
    public static final String COLUMN_PATIENT_COMPLAINTS_VALUE = "complaints_value";
    public static final String COLUMN_PATIENT_COMPLAINTS_ADD = "complaints_add";
    public static final String COLUMN_PATIENT_RISK_FACTORS = "risk_factors";
    public static final String COLUMN_PATIENT_RISK_FACTORS_VALUE = "risk_factors_value";
    public static final String COLUMN_PATIENT_RISK_FACTORS_ADD = "risk_factors_add";
    public static final String COLUMN_PATIENT_OTHER_COMORBIDITIES = "other_comorbidities";
    public static final String COLUMN_PATIENT_OTHER_COMORBIDITIES_VALUE = "other_comorbidities_value";
    public static final String COLUMN_PATIENT_OTHER_COMORBIDITIES_ADD = "other_comorbidities_add";
    public static final String COLUMN_PATIENT_CAD = "cad";
    public static final String COLUMN_PATIENT_CAD_VALUE = "cad_value";
    public static final String COLUMN_PATIENT_CAD_ADD = "cad_add";
    public static final String COLUMN_PATIENT_TREAMENT_FOR_PAST_CAD = "treatment_for_past_cad";
    public static final String COLUMN_PATIENT_TREAMENT_FOR_PAST_CAD_VALUE = "treatment_for_past_cad_value";
    public static final String COLUMN_PATIENT_TREAMENT_FOR_PAST_CAD_ADD = "treatment_for_past_cad_add";
    public static final String COLUMN_PATIENT_ECHO = "echo";
    public static final String COLUMN_PATIENT_ECHO_VALUE = "echo_value";
    public static final String COLUMN_PATIENT_ECHO_ADD = "echo_add";
    public static final String COLUMN_PATIENT_CURRENT_DIAGNOSIS = "current_diagnosis";
    public static final String COLUMN_PATIENT_CURRENT_DIAGNOSIS_VALUE = "current_diagnosis_value";
    public static final String COLUMN_PATIENT_CURRENT_DIAGNOSIS_ADD = "current_diagnosis_add";
    public static final String COLUMN_PATIENT_COMPLICATIONS_PREDESCRIPTION = "complications_in_hospital_predischarge";
    public static final String COLUMN_PATIENT_COMPLICATIONS_PREDESCRIPTION_VALUE = "complications_in_hospital_predischarge_value";
    public static final String COLUMN_PATIENT_COMPLICATIONS_PREDESCRIPTION_ADD = "complications_in_hospital_predischarge_add";
    public static final String COLUMN_PATIENT_OPERATOR = "operator";
    public static final String COLUMN_PATIENT_DATE = "doa";
    public static final String COLUMN_PATIENT_ADD_DETAILS = "add_details";

    public static final String COLUMN_PATIENT_HAS_CAG = "has_cag";
    public static final String COLUMN_PATIENT_HAS_PCI = "has_pci";
    public static final String COLUMN_PATIENT_HAS_LMCA = "has_lmca";
    public static final String COLUMN_PATIENT_HAS_BIFURICATION = "has_bifurication";
    public static final String COLUMN_PATIENT_HAS_STENT_LAD = "has_stent_lad";
    public static final String COLUMN_PATIENT_HAS_STENT_LAX = "has_stent_lax";
    public static final String COLUMN_PATIENT_HAS_STENT_RCA = "has_stent_rca";
    public static final String COLUMN_PATIENT_HAS_STENT_LMCA = "has_stent_lmca";
    public static final String COLUMN_PATIENT_HAS_STENT_DIAGONAL = "has_stent_diagonal";
    public static final String COLUMN_PATIENT_HAS_STENT_DM = "has_stent_dm";


    public static final int INDEX_PATIENT_ID = 1;
    public static final int INDEX_PATIENT_NAME = 2;
    public static final int INDEX_PATIENT_UHID = 3;
    public static final int INDEX_PATIENT_AGE = 4;
    public static final int INDEX_PATIENT_SEX = 5;
    public static final int INDEX_PATIENT_COMPLAINTS = 6;
    public static final int INDEX_PATIENT_COMPLAINTS_VALUE = 7;
    public static final int INDEX_PATIENT_COMPLAINTS_ADD = 8;
    public static final int INDEX_PATIENT_RISK_FACTORS = 9;
    public static final int INDEX_PATIENT_RISK_FACTORS_VALUE = 10;
    public static final int INDEX_PATIENT_RISK_FACTORS_ADD = 11;
    public static final int INDEX_PATIENT_OTHER_COMORBIDITIES = 12;
    public static final int INDEX_PATIENT_OTHER_COMORBIDITIES_VALUE = 13;
    public static final int INDEX_PATIENT_OTHER_COMORBIDITIES_ADD = 14;
    public static final int INDEX_PATIENT_CAD = 15;
    public static final int INDEX_PATIENT_CAD_VALUE = 16;
    public static final int INDEX_PATIENT_CAD_ADD = 17;
    public static final int INDEX_PATIENT_TREAMENT_FOR_PAST_CAD = 18;
    public static final int INDEX_PATIENT_TREAMENT_FOR_PAST_CAD_VALUE = 19;
    public static final int INDEX_PATIENT_TREAMENT_FOR_PAST_CAD_ADD = 20;
    public static final int INDEX_PATIENT_ECHO = 21;
    public static final int INDEX_PATIENT_ECHO_VALUE = 22;
    public static final int INDEX_PATIENT_ECHO_ADD = 23;
    public static final int INDEX_PATIENT_CURRENT_DIAGNOSIS = 24;
    public static final int INDEX_PATIENT_CURRENT_DIAGNOSIS_VALUE = 25;
    public static final int INDEX_PATIENT_CURRENT_DIAGNOSIS_ADD = 26;
    public static final int INDEX_PATIENT_COMPLICATIONS_PREDESCRIPTION = 27;
    public static final int INDEX_PATIENT_COMPLICATIONS_PREDESCRIPTION_VALUE = 28;
    public static final int INDEX_PATIENT_COMPLICATIONS_PREDESCRIPTION_ADD = 29;
    public static final int INDEX_PATIENT_OPERATOR = 30;
    public static final int INDEX_PATIENT_DATE = 31;
    public static final int INDEX_PATIENT_ADD_DETAILS = 32;

    public static final int INDEX_PATIENT_HAS_CAG = 33;
    public static final int INDEX_PATIENT_HAS_PCI = 34;
    public static final int INDEX_PATIENT_HAS_LMCA = 35;
    public static final int INDEX_PATIENT_HAS_BIFURICATION = 36;
    public static final int INDEX_PATIENT_HAS_STENT_LAD = 37;
    public static final int INDEX_PATIENT_HAS_STENT_LAX = 38;
    public static final int INDEX_PATIENT_HAS_STENT_RCA = 39;
    public static final int INDEX_PATIENT_HAS_STENT_LMCA = 40;
    public static final int INDEX_PATIENT_HAS_STENT_DIAGONAL = 41;
    public static final int INDEX_PATIENT_HAS_STENT_DM = 42;


    public static final String CREATE_TABLE_PATIENTS = "CREATE TABLE IF NOT EXISTS " + TABLE_PATIENTS + " ( "
            + COLUMN_PATIENT_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_PATIENT_NAME + " VARCHAR(30), "
            + COLUMN_PATIENT_UHID + " VARCHAR(20), "
            + COLUMN_PATIENT_AGE + " INTEGER, "
            + COLUMN_PATIENT_SEX + " VARCHAR(10), "
            + COLUMN_PATIENT_COMPLAINTS + " VARCHAR(30), "
            + COLUMN_PATIENT_COMPLAINTS_VALUE + " TEXT, "
            + COLUMN_PATIENT_COMPLAINTS_ADD + " TEXT, "
            + COLUMN_PATIENT_RISK_FACTORS + " VARCHAR(30), "
            + COLUMN_PATIENT_RISK_FACTORS_VALUE + " TEXT, "
            + COLUMN_PATIENT_RISK_FACTORS_ADD + " TEXT, "
            + COLUMN_PATIENT_OTHER_COMORBIDITIES + " VARCHAR(30), "
            + COLUMN_PATIENT_OTHER_COMORBIDITIES_VALUE + " TEXT, "
            + COLUMN_PATIENT_OTHER_COMORBIDITIES_ADD + " TEXT, "
            + COLUMN_PATIENT_CAD + " VARCHAR(30), "
            + COLUMN_PATIENT_CAD_VALUE + " TEXT, "
            + COLUMN_PATIENT_CAD_ADD + " TEXT, "
            + COLUMN_PATIENT_TREAMENT_FOR_PAST_CAD + " VARCHAR(30), "
            + COLUMN_PATIENT_TREAMENT_FOR_PAST_CAD_VALUE + " TEXT, "
            + COLUMN_PATIENT_TREAMENT_FOR_PAST_CAD_ADD + " TEXT, "
            + COLUMN_PATIENT_ECHO + " VARCHAR(30), "
            + COLUMN_PATIENT_ECHO_VALUE + " TEXT, "
            + COLUMN_PATIENT_ECHO_ADD + " TEXT, "
            + COLUMN_PATIENT_CURRENT_DIAGNOSIS + " VARCHAR(30), "
            + COLUMN_PATIENT_CURRENT_DIAGNOSIS_VALUE + " TEXT, "
            + COLUMN_PATIENT_CURRENT_DIAGNOSIS_ADD + " TEXT, "
            + COLUMN_PATIENT_COMPLICATIONS_PREDESCRIPTION + " VARCHAR(30), "
            + COLUMN_PATIENT_COMPLICATIONS_PREDESCRIPTION_VALUE + " TEXT, "
            + COLUMN_PATIENT_COMPLICATIONS_PREDESCRIPTION_ADD + " TEXT, "
            + COLUMN_PATIENT_OPERATOR + " TEXT, "
            + COLUMN_PATIENT_DATE + " DATE, "
            + COLUMN_PATIENT_ADD_DETAILS + " TEXT, "

            + COLUMN_PATIENT_HAS_CAG + " BOOLEAN, "
            + COLUMN_PATIENT_HAS_PCI + " BOOLEAN, "
            + COLUMN_PATIENT_HAS_LMCA + " BOOLEAN, "
            + COLUMN_PATIENT_HAS_BIFURICATION + " BOOLEAN, "
            + COLUMN_PATIENT_HAS_STENT_LAD + " BOOLEAN, "
            + COLUMN_PATIENT_HAS_STENT_LAX + " BOOLEAN, "
            + COLUMN_PATIENT_HAS_STENT_RCA + " BOOLEAN, "
            + COLUMN_PATIENT_HAS_STENT_LMCA + " BOOLEAN, "
            + COLUMN_PATIENT_HAS_STENT_DIAGONAL + " BOOLEAN, "
            + COLUMN_PATIENT_HAS_STENT_DM + " BOOLEAN "

            + ");";


    public static final String QUERY_PATIENTS = "SELECT * FROM " + TABLE_PATIENTS + " ORDER BY " + COLUMN_PATIENT_DATE;

    public static final String INSERT_PATIENT = "INSERT INTO " + TABLE_PATIENTS + " ("
            + COLUMN_PATIENT_NAME + ", "
            + COLUMN_PATIENT_UHID + ", "
            + COLUMN_PATIENT_AGE + ", "
            + COLUMN_PATIENT_SEX + ", "
            + COLUMN_PATIENT_COMPLAINTS + ", "
            + COLUMN_PATIENT_COMPLAINTS_VALUE + ", "
            + COLUMN_PATIENT_COMPLAINTS_ADD + ", "
            + COLUMN_PATIENT_RISK_FACTORS + ", "
            + COLUMN_PATIENT_RISK_FACTORS_VALUE + ", "
            + COLUMN_PATIENT_RISK_FACTORS_ADD + ", "
            + COLUMN_PATIENT_OTHER_COMORBIDITIES + ", "
            + COLUMN_PATIENT_OTHER_COMORBIDITIES_VALUE + ", "
            + COLUMN_PATIENT_OTHER_COMORBIDITIES_ADD + ", "
            + COLUMN_PATIENT_CAD + ", "
            + COLUMN_PATIENT_CAD_VALUE + ", "
            + COLUMN_PATIENT_CAD_ADD + ", "
            + COLUMN_PATIENT_TREAMENT_FOR_PAST_CAD + ", "
            + COLUMN_PATIENT_TREAMENT_FOR_PAST_CAD_VALUE + ", "
            + COLUMN_PATIENT_TREAMENT_FOR_PAST_CAD_ADD + ", "
            + COLUMN_PATIENT_ECHO + ", "
            + COLUMN_PATIENT_ECHO_VALUE + ", "
            + COLUMN_PATIENT_ECHO_ADD + ", "
            + COLUMN_PATIENT_CURRENT_DIAGNOSIS + ", "
            + COLUMN_PATIENT_CURRENT_DIAGNOSIS_VALUE + ", "
            + COLUMN_PATIENT_CURRENT_DIAGNOSIS_ADD + ", "
            + COLUMN_PATIENT_COMPLICATIONS_PREDESCRIPTION + ", "
            + COLUMN_PATIENT_COMPLICATIONS_PREDESCRIPTION_VALUE + ", "
            + COLUMN_PATIENT_COMPLICATIONS_PREDESCRIPTION_ADD + ", "
            + COLUMN_PATIENT_OPERATOR + ", "
            + COLUMN_PATIENT_DATE + ", "
            + COLUMN_PATIENT_ADD_DETAILS + ", "

            + COLUMN_PATIENT_HAS_CAG + ", "
            + COLUMN_PATIENT_HAS_PCI + ", "
            + COLUMN_PATIENT_HAS_LMCA + ", "
            + COLUMN_PATIENT_HAS_BIFURICATION + ", "
            + COLUMN_PATIENT_HAS_STENT_LAD + ", "
            + COLUMN_PATIENT_HAS_STENT_LAX + ", "
            + COLUMN_PATIENT_HAS_STENT_RCA + ", "
            + COLUMN_PATIENT_HAS_STENT_LMCA + ", "
            + COLUMN_PATIENT_HAS_STENT_DIAGONAL + ", "
            + COLUMN_PATIENT_HAS_STENT_DM
            + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    public static final String GET_PATIENT_BY_UHID = "SELECT * FROM " + TABLE_PATIENTS + " WHERE " + COLUMN_PATIENT_UHID
            + " = ?";
    public static final String GET_PATIENT_BY_ID = "SELECT * FROM " + TABLE_PATIENTS + " WHERE " + COLUMN_PATIENT_ID
            + " = ?";

    public static final String REMOVE_PATIENT_BY_ID = "DELETE FROM " + TABLE_PATIENTS + " WHERE " + COLUMN_PATIENT_ID
            + " = ?";

    public static final String UPDATE_PATIENT_BY_ID = "UPDATE " + TABLE_PATIENTS + " SET "
            + COLUMN_PATIENT_NAME + " = ?, "
            + COLUMN_PATIENT_UHID + " = ?, "
            + COLUMN_PATIENT_AGE + " = ?, "
            + COLUMN_PATIENT_SEX + " = ?, "
            + COLUMN_PATIENT_COMPLAINTS + " = ?, "
            + COLUMN_PATIENT_COMPLAINTS_VALUE + " = ?, "
            + COLUMN_PATIENT_COMPLAINTS_ADD + " = ?, "
            + COLUMN_PATIENT_RISK_FACTORS + " = ?, "
            + COLUMN_PATIENT_RISK_FACTORS_VALUE + " = ?, "
            + COLUMN_PATIENT_RISK_FACTORS_ADD + " = ?, "
            + COLUMN_PATIENT_OTHER_COMORBIDITIES + " = ?, "
            + COLUMN_PATIENT_OTHER_COMORBIDITIES_VALUE + " = ?, "
            + COLUMN_PATIENT_OTHER_COMORBIDITIES_ADD + " = ?, "
            + COLUMN_PATIENT_CAD + " = ?, "
            + COLUMN_PATIENT_CAD_VALUE + " = ?, "
            + COLUMN_PATIENT_CAD_ADD + " = ?, "
            + COLUMN_PATIENT_TREAMENT_FOR_PAST_CAD + " = ?, "
            + COLUMN_PATIENT_TREAMENT_FOR_PAST_CAD_VALUE + " = ?, "
            + COLUMN_PATIENT_TREAMENT_FOR_PAST_CAD_ADD + " = ?, "
            + COLUMN_PATIENT_ECHO + " = ?, "
            + COLUMN_PATIENT_ECHO_VALUE + " = ?, "
            + COLUMN_PATIENT_ECHO_ADD + " = ?, "
            + COLUMN_PATIENT_CURRENT_DIAGNOSIS + " = ?, "
            + COLUMN_PATIENT_CURRENT_DIAGNOSIS_VALUE + " = ?, "
            + COLUMN_PATIENT_CURRENT_DIAGNOSIS_ADD + " = ?, "
            + COLUMN_PATIENT_COMPLICATIONS_PREDESCRIPTION + " = ?, "
            + COLUMN_PATIENT_COMPLICATIONS_PREDESCRIPTION_VALUE + " = ?, "
            + COLUMN_PATIENT_COMPLICATIONS_PREDESCRIPTION_ADD + " = ?, "
            + COLUMN_PATIENT_OPERATOR + " = ?, "
            + COLUMN_PATIENT_DATE + " = ?, "
            + COLUMN_PATIENT_ADD_DETAILS + " = ?, "

            + COLUMN_PATIENT_HAS_CAG + " = ?, "
            + COLUMN_PATIENT_HAS_PCI + " = ?, "
            + COLUMN_PATIENT_HAS_LMCA + " = ?, "
            + COLUMN_PATIENT_HAS_BIFURICATION + " = ?, "
            + COLUMN_PATIENT_HAS_STENT_LAD + " = ?, "
            + COLUMN_PATIENT_HAS_STENT_LAX + " = ?, "
            + COLUMN_PATIENT_HAS_STENT_RCA + " = ?, "
            + COLUMN_PATIENT_HAS_STENT_LMCA + " = ?, "
            + COLUMN_PATIENT_HAS_STENT_DIAGONAL + " = ?, "
            + COLUMN_PATIENT_HAS_STENT_DM

            + " = ? WHERE "

            + COLUMN_PATIENT_ID + " = ?";
}
