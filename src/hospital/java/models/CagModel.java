package hospital.java.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import static hospital.java.helpers.HelperMethods.getKeyValueString;
import static hospital.java.helpers.HelperMethods.getKeysValues;

public class CagModel {

private String accessRoute;
    private String accessRouteField;
    private HashMap<String, String> accessRouteAdditional;
    private String bp_1;
    private String bp_2;
    private String bp_3;
    private String hr;
    private String lmca;
    private String lad;
    private String d1;
    private String lcx;
    private String om;
    private String rca;
    private String diagnosis;
    private String bifurcation;
    private String calcification;
    private String restenosis;
    private String lmcaDisease;
    private String lmcaDiseaseField;
    private String bipurificationDisease;
    private String bipurificationDiseaseField;
    private String recommendation;
    private String miscellaneous;
    private String finalImpression;
    private HashMap<String, String> additionalDetails;
    private int id;

    private LocalDate dateOfProcedure;

    @Override
    public String toString() {
        return accessRoute +
                " " + accessRouteField +
                " " + accessRouteAdditional +
                " " + lmca +
                " " + lad +
                " " + d1 +
                " " + lcx +
                " " + om +
                " " + rca +
                " " + diagnosis +
                " " + bifurcation +
                " " + calcification +
                " " + restenosis +
                " " + lmcaDisease +
                " " + lmcaDiseaseField +
                " " + bipurificationDisease +
                " " + bipurificationDiseaseField +
                " " + recommendation +
                " " + miscellaneous +
                " " + finalImpression +
                " " + additionalDetails +
                " " + dateOfProcedure +
                '}';
    }

    public HashMap<String, ArrayList<String>> getsKeys (boolean forPrint) {
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();

        ArrayList<String> accessKeys = new ArrayList<>();
        ArrayList<String> accessValues = new ArrayList<>();

        getKeysValues(accessKeys, accessValues, this.getAccessRoute(), this.getAccessRouteAdditional(), this.getAccessRouteField());
        String string = getKeyValueString(accessKeys, accessValues);
        if (!string.trim().equals("")) {
            keys.add("Access route");
            values.add(getKeyValueString(accessKeys, accessValues));
        }

        keys.add("Heart rate & BP");
        if (bp_3 == null || bp_3.trim().equals(""))
            values.add((hr == null || hr.trim().equals("") ? "" :( hr + " bpm. ")) + ((bp_1 == null || bp_2 == null || bp_1.trim().equals("") || bp_2.trim().equals("")) ? "" : ("& " + bp_1 + " / " + bp_2 + " mmHg")));
        else
            values.add((hr == null || hr.trim().equals("") ? "" :( hr + " bpm. ")) + ((bp_1 == null || bp_2 == null || bp_1.trim().equals("") || bp_2.trim().equals("")) ? "" : ("& " + bp_1 + " / " + bp_2 + " mmHg; mean: " + bp_3)));

        keys.add("LMCA");
        values.add(lmca == null ? "" : lmca);

        keys.add("LAD");
        values.add(lad == null ? "" : lad);

        keys.add("D1");
        values.add(d1 == null ? "" : d1);

        keys.add("LCX");
        values.add(lcx == null ? "" : lcx);

        keys.add("OM");
        values.add(om == null ? "" : om);

        keys.add("RCA");
        values.add(rca == null ? "" : rca);

        keys.add("Diagnosis");
        values.add(diagnosis == null ? "" : diagnosis);

        keys.add("Thrombus");
        values.add(bifurcation == null ? "" : bifurcation);

        keys.add("Calcification");
        values.add(calcification == null ? "" : calcification);

        keys.add("In-stent restenosis");
        values.add(restenosis == null ? "" : restenosis);

        keys.add("LMCA disease");
        if (lmcaDiseaseField == null || lmcaDiseaseField.trim().equals("") )
            values.add(lmcaDisease == null ? "" : lmcaDisease);
        else
            values.add(lmcaDisease == null ? "" : lmcaDisease + " :  " + lmcaDiseaseField);

        keys.add("Bifurcation disease");
        if (bipurificationDiseaseField == null || bipurificationDiseaseField.trim().equals("") )
            values.add(bipurificationDisease == null ? "" : bipurificationDisease);
        else
            values.add(bipurificationDisease == null ? "" : bipurificationDisease + " :  " + bipurificationDiseaseField);

        keys.add("Miscellaneous");
        values.add(miscellaneous == null ? "" : miscellaneous);

        keys.add("Final impression");
        values.add(finalImpression == null ? "" : finalImpression);

        keys.add("Recommendation");
        values.add(recommendation == null ? "" : recommendation);

        this.additionalDetails.forEach((k, v) -> keys.add(k));
        this.additionalDetails.forEach((k, v) -> values.add(v));
        if ( !forPrint)
            keys.add("Date of CAG procedure");

        if (!forPrint)
            values.add(dateOfProcedure == null ? "" : dateOfProcedure.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        return new HashMap<>() {{
            put("keys", keys);
            put("values", values);
        }};
    }

    public ArrayList<String> getKeys (boolean forPrint) {
        return getsKeys(forPrint).get("keys");
    }
    public ArrayList<String> getValues (boolean forPrint) {
        return getsKeys(forPrint).get("values");
    }

    public CagModel() {
        additionalDetails = new HashMap<>();
        accessRouteAdditional = new HashMap<>();
    }

    public HashMap<String, String> getAdditionalDetails() {
        return additionalDetails;
    }

    public void setAdditionalDetails(HashMap<String, String> additionalDetails) {
        this.additionalDetails = additionalDetails;
    }

    public String getFinalImpression() {
        return finalImpression;
    }

    public void setFinalImpression(String finalImpression) {
        this.finalImpression = finalImpression;
    }

    public String getLmcaDisease() {
        return lmcaDisease;
    }

    public void setLmcaDisease(String lmcaDisease) {
        this.lmcaDisease = lmcaDisease;
    }

    public String getBipurificationDisease() {
        return bipurificationDisease;
    }

    public void setBipurificationDisease(String bipurificationDisease) {
        this.bipurificationDisease = bipurificationDisease;
    }

    public String getLmcaDiseaseField() {
        return lmcaDiseaseField;
    }

    public void setLmcaDiseaseField(String lmcaDiseaseField) {
        this.lmcaDiseaseField = lmcaDiseaseField;
    }

    public String getBipurificationDiseaseField() {
        return bipurificationDiseaseField;
    }

    public void setBipurificationDiseaseField(String bipurificationDiseaseField) {
        this.bipurificationDiseaseField = bipurificationDiseaseField;
    }

    public String getMiscellaneous() {
        return miscellaneous;
    }

    public void setMiscellaneous(String miscellaneous) {
        this.miscellaneous = miscellaneous;
    }

    public String getBp_1() {
        return bp_1;
    }

    public void setBp_1(String bp_1) {
        this.bp_1 = bp_1;
    }

    public String getBp_2() {
        return bp_2;
    }

    public void setBp_2(String bp_2) {
        this.bp_2 = bp_2;
    }

    public String getBp_3() {
        return bp_3;
    }

    public void setBp_3(String bp_3) {
        this.bp_3 = bp_3;
    }

    public String getHr() {
        return hr;
    }

    public void setHr(String hr) {
        this.hr = hr;
    }

    public String getAccessRoute() {
        return accessRoute;
    }

    public void setAccessRoute(String accessRoute) {
        this.accessRoute = accessRoute;
    }

    public String getAccessRouteField() {
        return accessRouteField;
    }

    public void setAccessRouteField(String accessRouteField) {
        this.accessRouteField = accessRouteField;
    }

    public String getLmca() {
        return lmca == null ? "" : lmca;
    }

    public void setLmca(String lmca) {
        this.lmca = lmca;
    }

    public String getLad() {
        return lad == null ? "" : lad;
    }

    public void setLad(String lad) {
        this.lad = lad;
    }

    public String getD1() {
        return d1 == null ? "" : d1;
    }

    public void setD1(String d1) {
        this.d1 = d1;
    }

    public String getLcx() {
        return lcx == null ? "" : lcx;
    }

    public void setLcx(String lcx) {
        this.lcx = lcx;
    }

    public String getOm() {
        return om == null ? "" : om;
    }

    public void setOm(String om) {
        this.om = om;
    }

    public String getRca() {
        return rca == null ? "" : rca;
    }

    public void setRca(String rca) {
        this.rca = rca;
    }

    public String getDiagnosis() {
        return diagnosis == null ? "" : diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getBifurcation() {
        return bifurcation == null ? "" : bifurcation;
    }

    public void setBifurcation(String bifurcation) {
        this.bifurcation = bifurcation;
    }

    public String getCalcification() {
        return calcification == null ? "" : calcification;
    }

    public void setCalcification(String calcification) {
        this.calcification = calcification;
    }

    public String getRestenosis() {
        return restenosis == null ? "" : restenosis;
    }

    public void setRestenosis(String restenosis) {
        this.restenosis = restenosis;
    }

    public String getRecommendation() {
        return recommendation == null ? "" : recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateOfProcedure() {
        return dateOfProcedure;
    }

    public void setDateOfProcedure(LocalDate dateOfProcedure) {
        this.dateOfProcedure = dateOfProcedure;
    }

    public HashMap<String, String> getAccessRouteAdditional() {
        return accessRouteAdditional;
    }

    public void setAccessRouteAdditional(HashMap<String, String> accessRouteAdditional) {
        this.accessRouteAdditional = accessRouteAdditional;
    }
}
