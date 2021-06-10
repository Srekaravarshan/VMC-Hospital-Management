package hospital.java.sources;

import java.util.ArrayList;
import java.util.Arrays;

public class DropdownData {

    public static ArrayList<String> titles = new ArrayList<>(Arrays.asList(
            "Name", "UHID", "Age", "Sex", "Risk Factors", "Other Comorbidities",
            "Past h/o CAD", "Treatment for past CAD", "Echo", "Current Diagnosis", "Coronary Angiography",
            "PCI", "Complications In hospital Predischarge", "Post PCI"
    ));

    public static ArrayList<String> riskFactorsList = new ArrayList<>(Arrays.asList("Hypertension", "Diabetes Mellitus",
            "Dyslipidemia", "Smoking", "Family h/o of premature CAD"));

    public static ArrayList<String> otherComorbiditiesList = new ArrayList<>(
            Arrays.asList("CKD", "PAD", "CVA/Carotid artery stenosis"));

    public static ArrayList<String> pastCadList = new ArrayList<>(
            Arrays.asList("CSA", "USA", "NSTEMI", "STEAWMI", "STEIWMI", "Duration"));

    public static ArrayList<String> treatmentForPastCadList = new ArrayList<>(
            Arrays.asList("Medical Management", "PCI", "CABG", "DATE"));

    public static ArrayList<String> echoList = new ArrayList<>(Arrays.asList("EF", "Valve findings", "RWMA"));

    public static ArrayList<String> currentDiagnosisList = new ArrayList<>(
            Arrays.asList("CSA", "USA", "NSTEMI", "STEAWMI", "STEIWMI", "Duration"));

    public static ArrayList<String> coronaryAngiographyList = new ArrayList<>(
            Arrays.asList("Access Route- Radial/femoral", "Vitals", "LMCA", "LAD", "D1", "LCX", "OM", "RCA",
                    "Diagnosis", "BIFURCATION", "Calcification", "In-stent Restenosis", "Recommendation"));

    public static ArrayList<String> pciList = new ArrayList<>(Arrays.asList("Access Route- Radial/femoral", "Vitals",
            "Guiding catheter", "Wire", "Thrombus aspiration", "Predilation", "Stent", "Post dilation", "TIMI III flow",
            "Time to First medical contact", "DBT", "Complications", "Bifurcation PCI", "LMCA PCI",
            "Special balloons- Cutting/DEB/OPN", "Guide extension catheter", "IVUS Findings", "OCT findings"));

    public static ArrayList<String> complicationsPredischargeList = new ArrayList<>(
            Arrays.asList("Death", "Arrhythmias", "Stent thrombosis", "CCF/ Pulmonary edema", "Mechanical ventillation",
                    "AKI/CIN", "Bleeding complications"));

    public static ArrayList<String> postPsiList = new ArrayList<>(Arrays.asList("EF", "Trop I/CKMB"));

    private DropdownData() {}

}
