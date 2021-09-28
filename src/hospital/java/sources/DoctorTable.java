package hospital.java.sources;

public class DoctorTable {

    public static final String TABLE_DOCTORS = "doctors";
    public static final String COLUMN_DOCTOR_ID = "id";
    public static final String COLUMN_DOCTOR_NAME = "name";
    public static final String COLUMN_DOCTOR_QUALIFICATION = "qualification";
    public static final String COLUMN_DOCTOR_DEPARTMENT = "department";
    public static final String COLUMN_DOCTOR_REG_NO = "reg_no";
    public static final String COLUMN_DOCTOR_HOSPITAL = "hospital";
    public static final String COLUMN_DOCTOR_DISTRICT = "district";
    public static final String COLUMN_DOCTOR_PINCODE = "pincode";
    public static final String COLUMN_DOCTOR_MOBILE = "mobile";
    public static final String COLUMN_DOCTOR_EMAIL = "email";

    public static final int INDEX_DOCTOR_ID = 1;
    public static final int INDEX_DOCTOR_NAME = 2;
    public static final int INDEX_DOCTOR_QUALIFICATION = 3;
    public static final int INDEX_DOCTOR_DEPARTMENT = 4;
    public static final int INDEX_DOCTOR_REG_NO = 5;
    public static final int INDEX_DOCTOR_HOSPITAL = 6;
    public static final int INDEX_DOCTOR_DISTRICT = 7;
    public static final int INDEX_DOCTOR_PINCODE = 8;
    public static final int INDEX_DOCTOR_MOBILE = 9;
    public static final int INDEX_DOCTOR_EMAIL = 10;

    public static final String CREATE_TABLE_DOCTORS = "CREATE TABLE IF NOT EXISTS " + TABLE_DOCTORS + " ( "
            + COLUMN_DOCTOR_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_DOCTOR_NAME + " VARCHAR(30), "
            + COLUMN_DOCTOR_QUALIFICATION + " TEXT, "
            + COLUMN_DOCTOR_DEPARTMENT + " TEXT, "
            + COLUMN_DOCTOR_REG_NO + " TEXT, "
            + COLUMN_DOCTOR_HOSPITAL + " TEXT, "
            + COLUMN_DOCTOR_DISTRICT + " TEXT, "
            + COLUMN_DOCTOR_PINCODE + " TEXT, "
            + COLUMN_DOCTOR_MOBILE + " TEXT, "
            + COLUMN_DOCTOR_EMAIL + " TEXT " +
            ");";

    public static final String QUERY_DOCTORS = "SELECT * FROM " + TABLE_DOCTORS + " ORDER BY " + COLUMN_DOCTOR_NAME;

    public static final String INSERT_DOCTOR = "INSERT INTO " + TABLE_DOCTORS + " ("
            + COLUMN_DOCTOR_NAME + ", "
            + COLUMN_DOCTOR_QUALIFICATION + ", "
            + COLUMN_DOCTOR_DEPARTMENT + ", "
            + COLUMN_DOCTOR_REG_NO + ", "
            + COLUMN_DOCTOR_HOSPITAL + ", "
            + COLUMN_DOCTOR_DISTRICT + ", "
            + COLUMN_DOCTOR_PINCODE + ", "
            + COLUMN_DOCTOR_MOBILE + ", "
            + COLUMN_DOCTOR_EMAIL + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

    public static final String GET_DOCTOR_BY_ID = "SELECT * FROM " + TABLE_DOCTORS + " WHERE " + COLUMN_DOCTOR_ID
            + " = ?";

    public static final String REMOVE_DOCTOR_BY_ID = "DELETE FROM " + TABLE_DOCTORS + " WHERE " + COLUMN_DOCTOR_ID
            + " = ?";

    public static final String UPDATE_DOCTOR_BY_ID = "UPDATE " + TABLE_DOCTORS + " SET "
            + COLUMN_DOCTOR_NAME + " = ?, "
            + COLUMN_DOCTOR_QUALIFICATION + " = ?, "
            + COLUMN_DOCTOR_DEPARTMENT + " = ?, "
            + COLUMN_DOCTOR_REG_NO + " = ?, "
            + COLUMN_DOCTOR_HOSPITAL + " = ?, "
            + COLUMN_DOCTOR_DISTRICT + " = ?, "
            + COLUMN_DOCTOR_PINCODE + " = ?, "
            + COLUMN_DOCTOR_MOBILE + " = ?, "
            + COLUMN_DOCTOR_EMAIL + " = ? WHERE "

            + COLUMN_DOCTOR_ID + " = ?";
}
