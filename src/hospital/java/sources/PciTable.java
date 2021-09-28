package hospital.java.sources;

public class PciTable {

    public static final String TABLE_PCI = "pci";
    public static final String COLUMN_PCI_ACCESS_ROUTE = "access_route";
    public static final String COLUMN_PCI_ACCESS_ROUTE_VALUE = "access_route_value";
    public static final String COLUMN_PCI_ACCESS_ROUTE_ADD = "access_route_add";
    public static final String COLUMN_PCI_BP_1 = "blood_pressure_1";
    public static final String COLUMN_PCI_BP_2 = "blood_pressure_2";
    public static final String COLUMN_PCI_BP_3 = "blood_pressure_3";
    public static final String COLUMN_PCI_HR = "heart_rate";
    public static final String COLUMN_PCI_CATHETER = "catheter";
    public static final String COLUMN_PCI_WIRE = "wire";
    public static final String COLUMN_PCI_THROMBUS = "thrombus";
    public static final String COLUMN_PCI_PREDILATION = "predilation";
    public static final String COLUMN_PCI_STENT = "stent";
    public static final String COLUMN_PCI_STENT_VALUE = "stent_value";
    public static final String COLUMN_PCI_STENT_ADD = "stent_add";
    public static final String COLUMN_PCI_POST_DILATION = "post_dilation";
    public static final String COLUMN_PCI_TIMI_FLOW = "timi_flow";
    public static final String COLUMN_PCI_TIME_FIRST_MEDICAL = "time_first_medical";
    public static final String COLUMN_PCI_DBT = "dbt";
    public static final String COLUMN_PCI_COMPLICATIONS = "complications";
    public static final String COLUMN_PCI_BIFURCATION = "bifurcation";
    public static final String COLUMN_PCI_LMCA = "lmca";
    public static final String COLUMN_PCI_SPECIAL_BALLONS = "special_balloons";
    public static final String COLUMN_PCI_GUIDE_CATHETER = "guide_catheter";
    public static final String COLUMN_PCI_IVUS_FINDINGS = "ivus_findings";
    public static final String COLUMN_PCI_OCT_FINDINGS = "oct_findings";
    public static final String COLUMN_PCI_ROTABLATIOIN = "rotablation";
    public static final String COLUMN_PCI_LMCA_DISEASE = "lmca_disease";
    public static final String COLUMN_PCI_LMCA_DISEASE_VALUE = "lmca_disease_value";
    public static final String COLUMN_PCI_BIPURIFICATION_DISEASE = "bipurification_disease";
    public static final String COLUMN_PCI_BIPURIFICATION_DISEASE_VALUE = "bipurification_disease_value";
    public static final String COLUMN_PCI_SYNTAX_SCORE = "syntax_score";
    public static final String COLUMN_PCI_FINAL_IMPRESSION = "final_impression";
    public static final String COLUMN_PCI_DATE_OF_PROCEDURE = "dop";
    public static final String COLUMN_PCI_ADD_DETAILS = "add_details";
    public static final String COLUMN_PCI_ID = "id";

    public static final int INDEX_PCI_ACCESS_ROUTE = 1;
    public static final int INDEX_PCI_ACCESS_ROUTE_VALUE = 2;
    public static final int INDEX_PCI_ACCESS_ROUTE_ADD = 3;
    public static final int INDEX_PCI_BP_1 = 4;
    public static final int INDEX_PCI_BP_2 = 5;
    public static final int INDEX_PCI_BP_3 = 6;
    public static final int INDEX_PCI_HR = 7;
    public static final int INDEX_PCI_CATHETER = 8;
    public static final int INDEX_PCI_WIRE = 9;
    public static final int INDEX_PCI_THROMBUS = 10;
    public static final int INDEX_PCI_PREDILATION = 11;
    public static final int INDEX_PCI_STENT = 12;
    public static final int INDEX_PCI_STENT_VALUE = 13;
    public static final int INDEX_PCI_STENT_ADD = 14;
    public static final int INDEX_PCI_POST_DILATION = 15;
    public static final int INDEX_PCI_TIMI_FLOW = 16;
    public static final int INDEX_PCI_TIME_FIRST_MEDICAL = 17;
    public static final int INDEX_PCI_DBT = 18;
    public static final int INDEX_PCI_COMPLICATIONS = 19;
    public static final int INDEX_PCI_BIFURCATION = 20;
    public static final int INDEX_PCI_LMCA = 21;
    public static final int INDEX_PCI_SPECIAL_BALLONS = 22;
    public static final int INDEX_PCI_GUIDE_CATHETER = 23;
    public static final int INDEX_PCI_IVUS_FINDINGS = 24;
    public static final int INDEX_PCI_OCT_FINDINGS = 25;
    public static final int INDEX_PCI_ROTABLATION = 26;
    public static final int INDEX_PCI_LMCA_DISEASE = 27;
    public static final int INDEX_PCI_LMCA_DISEASE_VALUE = 28;
    public static final int INDEX_PCI_BIPURIFICATION_DISEASE = 29;
    public static final int INDEX_PCI_BIPURIFICATION_DISEASE_VALUE = 30;
    public static final int INDEX_PCI_SYNTAX_SCORE = 31;
    public static final int INDEX_PCI_FINAL_IMPRESSION = 32;
    public static final int INDEX_PCI_DATE_OF_PROCEDURE = 33;
    public static final int INDEX_PCI_ADD_DETAILS = 34;
    public static final int INDEX_PCI_ID = 35;

    public static final String CREATE_TABLE_PCI = "CREATE TABLE IF NOT EXISTS " + TABLE_PCI + " ( "
            + COLUMN_PCI_ACCESS_ROUTE + " TEXT, "   // 1
            + COLUMN_PCI_ACCESS_ROUTE_VALUE + " TEXT, "   // 2
            + COLUMN_PCI_ACCESS_ROUTE_ADD + " TEXT, "   // 13
            + COLUMN_PCI_BP_1 + " TEXT, "   // 3
            + COLUMN_PCI_BP_2 + " TEXT, "   // 4
            + COLUMN_PCI_BP_3 + " TEXT, "   // 5
            + COLUMN_PCI_HR + " TEXT, "   // 6
            + COLUMN_PCI_CATHETER + " TEXT, "   // 7
            + COLUMN_PCI_WIRE + " INTEGER, "   // 8
            + COLUMN_PCI_THROMBUS + " TEXT, "   // 9
            + COLUMN_PCI_PREDILATION + " TEXT, "   // 10
            + COLUMN_PCI_STENT + " TEXT, "   // 11
            + COLUMN_PCI_STENT_VALUE + " TEXT, "   // 12
            + COLUMN_PCI_STENT_ADD + " TEXT, "   // 13
            + COLUMN_PCI_POST_DILATION + " TEXT, "   // 14
            + COLUMN_PCI_TIMI_FLOW + " TEXT, "   // 15
            + COLUMN_PCI_TIME_FIRST_MEDICAL + " TEXT, "   // 16
            + COLUMN_PCI_DBT + " TEXT, "   // 17
            + COLUMN_PCI_COMPLICATIONS + " TEXT, "   // 18
            + COLUMN_PCI_BIFURCATION + " TEXT, "   // 19
            + COLUMN_PCI_LMCA + " TEXT, "   // 20
            + COLUMN_PCI_SPECIAL_BALLONS + " TEXT, "   // 21
            + COLUMN_PCI_GUIDE_CATHETER + " TEXT, "   // 22
            + COLUMN_PCI_IVUS_FINDINGS + " TEXT, "   // 23
            + COLUMN_PCI_OCT_FINDINGS + " TEXT, "   // 24
            + COLUMN_PCI_ROTABLATIOIN + " TEXT, "   // 25
            + COLUMN_PCI_LMCA_DISEASE + " TEXT, "   // 26
            + COLUMN_PCI_LMCA_DISEASE_VALUE + " TEXT, "   // 27
            + COLUMN_PCI_BIPURIFICATION_DISEASE + " TEXT, "   // 28
            + COLUMN_PCI_BIPURIFICATION_DISEASE_VALUE + " TEXT, "   // 29
            + COLUMN_PCI_SYNTAX_SCORE + " TEXT, "   // 29
            + COLUMN_PCI_FINAL_IMPRESSION + " TEXT, "   // 30
            + COLUMN_PCI_DATE_OF_PROCEDURE + " DATE, "   // 31
            + COLUMN_PCI_ADD_DETAILS + " TEXT, "   // 32
            + COLUMN_PCI_ID + " INT "   // 33
            + ");";

    public static final String QUERY_PCI = "SELECT * FROM " + TABLE_PCI + " WHERE id = ?";

    public static final String INSERT_PCI = "INSERT INTO " + TABLE_PCI + " ("
            + COLUMN_PCI_ACCESS_ROUTE + ", "      // 1
            + COLUMN_PCI_ACCESS_ROUTE_VALUE + ", "      // 2
            + COLUMN_PCI_ACCESS_ROUTE_ADD + ", "      // 13
            + COLUMN_PCI_BP_1 + ", "      // 3
            + COLUMN_PCI_BP_2 + ", "      // 4
            + COLUMN_PCI_BP_3 + ", "      // 5
            + COLUMN_PCI_HR + ", "      // 6
            + COLUMN_PCI_CATHETER + ", "      // 7
            + COLUMN_PCI_WIRE + ", "      // 8
            + COLUMN_PCI_THROMBUS + ", "      // 9
            + COLUMN_PCI_PREDILATION + ", "      // 10
            + COLUMN_PCI_STENT + ", "      // 11
            + COLUMN_PCI_STENT_VALUE + ", "      // 12
            + COLUMN_PCI_STENT_ADD + ", "      // 13
            + COLUMN_PCI_POST_DILATION + ", "      // 14
            + COLUMN_PCI_TIMI_FLOW + ", "      // 15
            + COLUMN_PCI_TIME_FIRST_MEDICAL + ", "      // 16
            + COLUMN_PCI_DBT + ", "      // 17
            + COLUMN_PCI_COMPLICATIONS + ", "      // 18
            + COLUMN_PCI_BIFURCATION + ", "      // 19
            + COLUMN_PCI_LMCA + ", "      // 20
            + COLUMN_PCI_SPECIAL_BALLONS + ", "      // 21
            + COLUMN_PCI_GUIDE_CATHETER + ", "      // 22
            + COLUMN_PCI_IVUS_FINDINGS + ", "      // 23
            + COLUMN_PCI_OCT_FINDINGS + ", "      // 24
            + COLUMN_PCI_ROTABLATIOIN + ", "      // 25
            + COLUMN_PCI_LMCA_DISEASE + ", "      // 26
            + COLUMN_PCI_LMCA_DISEASE_VALUE + ", "      // 27
            + COLUMN_PCI_BIPURIFICATION_DISEASE + ", "      // 28
            + COLUMN_PCI_BIPURIFICATION_DISEASE_VALUE + ", "      // 29
            + COLUMN_PCI_SYNTAX_SCORE + ", "      // 29
            + COLUMN_PCI_FINAL_IMPRESSION + ", "      // 30
            + COLUMN_PCI_DATE_OF_PROCEDURE + ", "      // 31
            + COLUMN_PCI_ADD_DETAILS + ", "      // 32
            + COLUMN_PCI_ID      // 33
            + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    public static final String REMOVE_PCI_BY_ID = "DELETE FROM " + TABLE_PCI + " WHERE " + COLUMN_PCI_ID
            + " = ?";

    public static final String UPDATE_PCI_BY_ID = "UPDATE " + TABLE_PCI + " SET "
            + COLUMN_PCI_ACCESS_ROUTE + " = ?, " // 1
            + COLUMN_PCI_ACCESS_ROUTE_VALUE + " = ?, " // 2
            + COLUMN_PCI_ACCESS_ROUTE_ADD + " = ?, " // 13
            + COLUMN_PCI_BP_1 + " = ?, " // 3
            + COLUMN_PCI_BP_2 + " = ?, " // 4
            + COLUMN_PCI_BP_3 + " = ?, " // 5
            + COLUMN_PCI_HR + " = ?, " // 6
            + COLUMN_PCI_CATHETER + " = ?, " // 7
            + COLUMN_PCI_WIRE + " = ?, " // 8
            + COLUMN_PCI_THROMBUS + " = ?, " // 9
            + COLUMN_PCI_PREDILATION + " = ?, " // 10
            + COLUMN_PCI_STENT + " = ?, " // 11
            + COLUMN_PCI_STENT_VALUE + " = ?, " // 12
            + COLUMN_PCI_STENT_ADD + " = ?, " // 13
            + COLUMN_PCI_POST_DILATION + " = ?, " // 14
            + COLUMN_PCI_TIMI_FLOW + " = ?, " // 15
            + COLUMN_PCI_TIME_FIRST_MEDICAL + " = ?, " // 16
            + COLUMN_PCI_DBT + " = ?, " // 17
            + COLUMN_PCI_COMPLICATIONS + " = ?, " // 18
            + COLUMN_PCI_BIFURCATION + " = ?, " // 19
            + COLUMN_PCI_LMCA + " = ?, " // 20
            + COLUMN_PCI_SPECIAL_BALLONS + " = ?, " // 21
            + COLUMN_PCI_GUIDE_CATHETER + " = ?, " // 22
            + COLUMN_PCI_IVUS_FINDINGS + " = ?, " // 23
            + COLUMN_PCI_OCT_FINDINGS + " = ?, " // 24
            + COLUMN_PCI_ROTABLATIOIN + " = ?, " // 25
            + COLUMN_PCI_LMCA_DISEASE + " = ?, " // 26
            + COLUMN_PCI_LMCA_DISEASE_VALUE + " = ?, " // 27
            + COLUMN_PCI_BIPURIFICATION_DISEASE + " = ?, " // 28
            + COLUMN_PCI_BIPURIFICATION_DISEASE_VALUE + " = ?, " // 29
            + COLUMN_PCI_SYNTAX_SCORE + " = ?, " // 29
            + COLUMN_PCI_FINAL_IMPRESSION + " = ?, " // 30
            + COLUMN_PCI_DATE_OF_PROCEDURE + " = ?, " // 31
            + COLUMN_PCI_ADD_DETAILS + " = ? WHERE " // 32

            + COLUMN_PCI_ID + " = ?"; // 33

}
