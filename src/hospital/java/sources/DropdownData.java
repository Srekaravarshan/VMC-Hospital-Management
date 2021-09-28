package hospital.java.sources;

import hospital.java.models.DoctorModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class DropdownData {

    public static ArrayList<String> procedureList= new ArrayList<>(Arrays.asList(
            "CAG", "PCI", "CAG + PCI", "LMCA", "Bifurcation"
    ));

    public static ArrayList<String> ageList = new ArrayList<>(Arrays.asList(
            "< 40", "40 - 70", "> 70"
    ));

    public static ArrayList<String> getOperatorList () {

        ArrayList<String> operatorList = new ArrayList<>();
        try {
            Datasource.instance.queryDoctors();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for (DoctorModel doctor : Datasource.getDoctors()) {
            operatorList.add(doctor.getName());
        }
        return operatorList;
    }

    public static ArrayList<String> complaintsList = new ArrayList<>(Arrays.asList(
            "Chest pain", "Dyspnea", "Palpitation", "Syncope", "Others"
    ));

    public static ArrayList<String> accessRouteList = new ArrayList<>(Arrays.asList(
            "Right radial", "Right femoral", "Left radial", "Left femoral"
    ));
    public static ArrayList<String> lmcaDiseaseList = new ArrayList<>(Arrays.asList(
            "Yes", "No"
    ));
    public static ArrayList<String> bipurificationDiseaseList = new ArrayList<>(Arrays.asList(
            "Yes", "No"
    ));

    public static ArrayList<String> stentList = new ArrayList<>(Arrays.asList(
            "LAD", "LCX", "RCA", "LMCA", "Diagonal", "OM"
    ));


    public static ArrayList<String> titles = new ArrayList<>(Arrays.asList(
            "Name", "UHID", "Age", "Sex",
            "Complaints",
            "Risk factors",
            "Other Comorbidities",
            "Past h/o CAD",
            "Treatment for past CAD",
            "Echo",
            "Current diagnosis",
            "Complications in hospital predischarge",
            "Date"
    ));

    public static ArrayList<String> sexList = new ArrayList<>(Arrays.asList("Male", "Female",
            "Transgender"));

    public static ArrayList<String> riskFactorsList = new ArrayList<>(Arrays.asList("Hypertension", "Diabetes Mellitus",
            "Dyslipidemia", "Smoking", "Family h/o of premature CAD"));

    public static ArrayList<String> otherComorbiditiesList = new ArrayList<>(
            Arrays.asList("CKD", "PAD", "CVA/Carotid artery stenosis"));

    public static ArrayList<String> pastCadList = new ArrayList<>(
            Arrays.asList("Yes", "No"));

    public static ArrayList<String> treatmentForPastCadList = new ArrayList<>(
            Arrays.asList("Medical management", "PCI", "CABG", "DATE"));

    public static ArrayList<String> echoList = new ArrayList<>(Arrays.asList("EF", "Valve findings", "RWMA"));

    public static ArrayList<String> currentDiagnosisList = new ArrayList<>(
            Arrays.asList("CSA", "USA", "NSTEMI", "STEAWMI", "STEIWMI", "Duration"));

    public static ArrayList<String> coronaryAngiographyList = new ArrayList<>(
            Arrays.asList("Access route",
//                    "Vitals",
                    "Blood pressure",
                    "Heart rate",
                    "LMCA",
                    "LAD",
                    "D1",
                    "LCX",
                    "OM",
                    "RCA",
                    "Diagnosis",
                    "Thrombus",
                    "Calcification",
                    "In-stent restenosis",
                    "LMCA disease",
                    "Bifurcation disease",
                    "Miscellaneous",
                    "Final impression",
                    "Recommendation"));

    public static ArrayList<String> pciList = new ArrayList<>(Arrays.asList("Access Route",
            "Blood pressure",
            "Heart rate",
            "Guiding catheter", "Wire", "Thrombus aspiration", "Predilation", "Stent", "Post dilation", "TIMI III flow",
            "Time to first medical contact", "DBT", "Complications", "Bifurcation PCI", "LMCA PCI",
            "Special balloons- Cutting/DEB/OPN", "Guide extension catheter", "IVUS findings", "OCT findings", "Rotablation | IVL | Laser", "Final impression"));

    public static ArrayList<String> complicationsPredischargeList = new ArrayList<>(
            Arrays.asList("Death", "Arrhythmias", "Stent thrombosis", "CCF/ Pulmonary edema", "Mechanical ventillation",
                    "AKI/CIN", "Bleeding complications"));

    public static ArrayList<String> postPsiList = new ArrayList<>(Arrays.asList("EF", "Trop I/CKMB"));

    private DropdownData() {}

}
