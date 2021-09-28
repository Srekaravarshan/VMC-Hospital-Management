package hospital.java.sources;

public class CagTable {

    public static final String TABLE_CAG = "cag";
    public static final String COLUMN_CAG_ACCESS_ROUTE = "access_route";
    public static final String COLUMN_CAG_ACCESS_ROUTE_VALUE = "access_route_value";
    public static final String COLUMN_CAG_ACCESS_ROUTE_ADD = "access_route_add";
    public static final String COLUMN_CAG_BP_1 = "blood_pressure_1";
    public static final String COLUMN_CAG_BP_2 = "blood_pressure_2";
    public static final String COLUMN_CAG_BP_3 = "blood_pressure_3";
    public static final String COLUMN_CAG_HR = "heart_rate";
    public static final String COLUMN_CAG_LMCA = "lmca";
    public static final String COLUMN_CAG_LAD = "lad";
    public static final String COLUMN_CAG_D1 = "d1";
    public static final String COLUMN_CAG_LCX = "lcx";
    public static final String COLUMN_CAG_OM = "om";
    public static final String COLUMN_CAG_RCA = "rca";
    public static final String COLUMN_CAG_DIAGNOSIS = "diagnosis";
    public static final String COLUMN_CAG_BIFURCATION = "bifurcation";
    public static final String COLUMN_CAG_CALCIFICATION = "calcification";
    public static final String COLUMN_CAG_RESTENOSIS = "restenosis";
    public static final String COLUMN_CAG_LMCA_DISEASE = "lmca_disease";
    public static final String COLUMN_CAG_LMCA_DISEASE_VALUE = "lmca_disease_value";
    public static final String COLUMN_CAG_BIPURIFICATION_DISEASE = "bipurification_disease";
    public static final String COLUMN_CAG_BIPURIFICATION_DISEASE_VALUE = "bipurification_disease_value";
    public static final String COLUMN_CAG_MISCELLANEOUS = "miscellaneous";
    public static final String COLUMN_CAG_FINAL_IMPRESSION = "final_impression";
    public static final String COLUMN_CAG_RECOMMENDATION = "recommendation";
    public static final String COLUMN_CAG_DATE_OF_PROCEDURE = "dop";
    public static final String COLUMN_CAG_ADD_DETAILS = "add_details";
    public static final String COLUMN_CAG_ID = "id";


    public static final int INDEX_CAG_ACCESS_ROUTE = 1;
    public static final int INDEX_CAG_ACCESS_ROUTE_VALUE = 2;
    public static final int INDEX_CAG_ACCESS_ROUTE_ADD = 3;
    public static final int INDEX_CAG_BP_1 = 4;
    public static final int INDEX_CAG_BP_2 = 5;
    public static final int INDEX_CAG_BP_3 = 6;
    public static final int INDEX_CAG_HR = 7;
    public static final int INDEX_CAG_LMCA = 8;
    public static final int INDEX_CAG_LAD = 9;
    public static final int INDEX_CAG_D1 = 10;
    public static final int INDEX_CAG_LCX = 11;
    public static final int INDEX_CAG_OM = 12;
    public static final int INDEX_CAG_RCA = 13;
    public static final int INDEX_CAG_DIAGNOSIS = 14;
    public static final int INDEX_CAG_BIFURCATION = 15;
    public static final int INDEX_CAG_CALCIFICATION = 16;
    public static final int INDEX_CAG_RESTENOSIS = 17;
    public static final int INDEX_CAG_LMCA_DISEASE = 18;
    public static final int INDEX_CAG_LMCA_DISEASE_VALUE = 19;
    public static final int INDEX_CAG_BIPURIFICATION_DISEASE = 20;
    public static final int INDEX_CAG_BIPURIFICATION_DISEASE_VALUE = 21;
    public static final int INDEX_CAG_MISCELLANEOUS = 22;
    public static final int INDEX_CAG_FINAL_IMPRESSION = 23;
    public static final int INDEX_CAG_RECOMMENDATION = 24;
    public static final int INDEX_CAG_DATE_OF_PROCEDURE = 25;
    public static final int INDEX_CAG_ADD_DETAILS = 26;
    public static final int INDEX_CAG_ID = 27;

    public static final String CREATE_TABLE_CAG = "CREATE TABLE IF NOT EXISTS " + TABLE_CAG + " ( "
            + COLUMN_CAG_ACCESS_ROUTE + " TEXT, "  // 1
            + COLUMN_CAG_ACCESS_ROUTE_VALUE + " TEXT, "  // 2
            + COLUMN_CAG_ACCESS_ROUTE_ADD + " TEXT, "   // 13
            + COLUMN_CAG_BP_1 + " TEXT, "  // 3
            + COLUMN_CAG_BP_2 + " TEXT, "  // 4
            + COLUMN_CAG_BP_3 + " TEXT, "  // 5
            + COLUMN_CAG_HR + " TEXT, "  // 6
            + COLUMN_CAG_LMCA + " TEXT, "  // 7
            + COLUMN_CAG_LAD + " TEXT , "  // 8
            + COLUMN_CAG_D1 + " TEXT, "  // 9
            + COLUMN_CAG_LCX + " TEXT, "  // 10
            + COLUMN_CAG_OM + " TEXT, "  // 11
            + COLUMN_CAG_RCA + " TEXT, "  // 12
            + COLUMN_CAG_DIAGNOSIS + " TEXT, "  // 13
            + COLUMN_CAG_BIFURCATION + " TEXT, "  // 14
            + COLUMN_CAG_CALCIFICATION + " TEXT, "  // 15
            + COLUMN_CAG_RESTENOSIS + " TEXT, "  // 16
            + COLUMN_CAG_LMCA_DISEASE + " TEXT, "  // 17
            + COLUMN_CAG_LMCA_DISEASE_VALUE + " TEXT, "  // 18
            + COLUMN_CAG_BIPURIFICATION_DISEASE + " TEXT, "  // 19
            + COLUMN_CAG_BIPURIFICATION_DISEASE_VALUE + " TEXT, "  // 20
            + COLUMN_CAG_MISCELLANEOUS + " TEXT, "  // 21
            + COLUMN_CAG_FINAL_IMPRESSION + " TEXT, "  // 22
            + COLUMN_CAG_RECOMMENDATION + " TEXT, "  // 23
            + COLUMN_CAG_DATE_OF_PROCEDURE + " DATE, "  // 24
            + COLUMN_CAG_ADD_DETAILS + " TEXT, "  // 25
            + COLUMN_CAG_ID + " INT "  // 26
            + ");";

    public static final String QUERY_CAG = "SELECT * FROM " + TABLE_CAG + " WHERE id = ?";

    public static final String INSERT_CAG = "INSERT INTO " + TABLE_CAG + " ("
            + COLUMN_CAG_ACCESS_ROUTE + ", "  // 1
            + COLUMN_CAG_ACCESS_ROUTE_VALUE + ", "  // 2
            + COLUMN_CAG_ACCESS_ROUTE_ADD + ", "      // 13
            + COLUMN_CAG_BP_1 + ", "  // 3
            + COLUMN_CAG_BP_2 + ", "  // 4
            + COLUMN_CAG_BP_3 + ", "  // 5
            + COLUMN_CAG_HR + ", "  // 6
            + COLUMN_CAG_LMCA + ", "  // 7
            + COLUMN_CAG_LAD + ", "  // 8
            + COLUMN_CAG_D1 + ", "  // 9
            + COLUMN_CAG_LCX + ", "  // 10
            + COLUMN_CAG_OM + ", "  // 11
            + COLUMN_CAG_RCA + ", "  // 12
            + COLUMN_CAG_DIAGNOSIS + ", "  // 13
            + COLUMN_CAG_BIFURCATION + ", "  // 14
            + COLUMN_CAG_CALCIFICATION + ", "  // 15
            + COLUMN_CAG_RESTENOSIS + ", "  // 16
            + COLUMN_CAG_LMCA_DISEASE + ", "  // 17
            + COLUMN_CAG_LMCA_DISEASE_VALUE + ", "  // 18
            + COLUMN_CAG_BIPURIFICATION_DISEASE + ", "  // 19
            + COLUMN_CAG_BIPURIFICATION_DISEASE_VALUE + ", "  // 20
            + COLUMN_CAG_MISCELLANEOUS + ", "  // 21
            + COLUMN_CAG_FINAL_IMPRESSION + ", "  // 22
            + COLUMN_CAG_RECOMMENDATION + ", "  // 23
            + COLUMN_CAG_DATE_OF_PROCEDURE + ", "  // 24
            + COLUMN_CAG_ADD_DETAILS + ", "  // 25
            + COLUMN_CAG_ID  // 26
            + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    public static final String REMOVE_CAG_BY_ID = "DELETE FROM " + TABLE_CAG + " WHERE " + COLUMN_CAG_ID
            + " = ?";

    public static final String UPDATE_CAG_BY_ID = "UPDATE " + TABLE_CAG + " SET "
            + COLUMN_CAG_ACCESS_ROUTE + " = ?, "    // 1
            + COLUMN_CAG_ACCESS_ROUTE_VALUE + " = ?, "    // 2
            + COLUMN_CAG_ACCESS_ROUTE_ADD + " = ?, " // 13
            + COLUMN_CAG_BP_1 + " = ?, "    // 3
            + COLUMN_CAG_BP_2 + " = ?, "    // 4
            + COLUMN_CAG_BP_3 + " = ?, "    // 5
            + COLUMN_CAG_HR + " = ?, "    // 6
            + COLUMN_CAG_LMCA + " = ?, "    // 7
            + COLUMN_CAG_LAD + " = ?, "    // 8
            + COLUMN_CAG_D1 + " = ?, "    // 9
            + COLUMN_CAG_LCX + " = ?, "    // 10
            + COLUMN_CAG_OM + " = ?, "    // 11
            + COLUMN_CAG_RCA + " = ?, "    // 12
            + COLUMN_CAG_DIAGNOSIS + " = ?, "    // 13
            + COLUMN_CAG_BIFURCATION + " = ?, "    // 14
            + COLUMN_CAG_CALCIFICATION + " = ?, "    // 15
            + COLUMN_CAG_RESTENOSIS + " = ?, "    // 16
            + COLUMN_CAG_LMCA_DISEASE + " = ?, "    // 17
            + COLUMN_CAG_LMCA_DISEASE_VALUE + " = ?, "    // 18
            + COLUMN_CAG_BIPURIFICATION_DISEASE + " = ?, "    // 19
            + COLUMN_CAG_BIPURIFICATION_DISEASE_VALUE + " = ?, "    // 20
            + COLUMN_CAG_MISCELLANEOUS + " = ?, "    // 21
            + COLUMN_CAG_FINAL_IMPRESSION + " = ?, "    // 22
            + COLUMN_CAG_RECOMMENDATION + " = ?, "    // 23
            + COLUMN_CAG_DATE_OF_PROCEDURE + " = ?, "    // 24
            + COLUMN_CAG_ADD_DETAILS    // 25
            + " = ? WHERE "

            + COLUMN_CAG_ID + " = ?";  // 26
}
