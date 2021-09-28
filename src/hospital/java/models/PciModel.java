package hospital.java.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import static hospital.java.helpers.HelperMethods.getKeyValueString;
import static hospital.java.helpers.HelperMethods.getKeysValues;
import static hospital.java.models.Patient.getValueString;

public class PciModel {
    private String accessRoute;
    private String accessRouteField;
    private HashMap<String, String> accessRouteAdditional;
    private String bp_1;
    private String bp_2;
    private String bp_3;
    private String hr;
    private String catheter;
    private String wire;
    private String thrombus;
    private String predilation;
    private String stent;
    private String stentField;
    private HashMap<String, String> stentAdditional;
    private String postDilation;
    private String timiFlow;
    private String timeFirstMedical;
    private String dbt;
    private String complications;
    private String bifurcation;
    private String lmca;
    private String specialBalloons;
    private String guideCatheter;
    private String ivusFindings;
    private String octFindings;
    private String rotablation;
    private String lmcaDisease;
    private String lmcaDiseaseField;
    private String bipurificationDisease;
    private String bipurificationDiseaseField;
    private String syntaxScore;
    private String finalImpression;
    private HashMap<String, String> additionalDetails;
    private int id;
    private LocalDate dateOfProcedure;

    @Override
    public String toString() {
        return accessRoute +
                " " + accessRouteField +
                " " + accessRouteAdditional +
                " " + catheter +
                " " + wire +
                " " + thrombus +
                " " + predilation +
                " " + stent +
                " " + stentField +
                " " + stentAdditional +
                " " + postDilation +
                " " + timiFlow +
                " " + timeFirstMedical +
                " " + dbt +
                " " + complications +
                " " + bifurcation +
                " " + lmca +
                " " + specialBalloons +
                " " + guideCatheter +
                " " + ivusFindings +
                " " + octFindings +
                " " + rotablation +
                " " + lmcaDisease +
                " " + lmcaDiseaseField +
                " " + bipurificationDisease +
                " " + bipurificationDiseaseField +
                " " + syntaxScore +
                " " + finalImpression +
                " " + additionalDetails +
                " " + dateOfProcedure;
    }

    public PciModel() {
        additionalDetails = new HashMap<>();
        stentAdditional = new HashMap<>();
        accessRouteAdditional = new HashMap<>();
    }

    public HashMap<String, String> getAdditionalDetails() {
        return additionalDetails;
    }

    public void setAdditionalDetails(HashMap<String, String> additionalDetails) {
        this.additionalDetails = additionalDetails;
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


//        keys.add("Blood pressure");
        keys.add("Guiding catheter");
        values.add(catheter == null ? "" : catheter);
        keys.add("Wire");
        values.add(wire == null ? "" : wire);
        keys.add("Thrombus aspiration");
        values.add(thrombus == null ? "" : thrombus);
        keys.add("Predilation");
        values.add(predilation == null ? "" : predilation);

        keys.add(stent);
        this.stentAdditional.forEach((k, v) -> keys.add(k));
        values.addAll(getValueString(stentField, this.stentAdditional));

        keys.add("Post dilation");
        values.add(postDilation == null ? "" : postDilation);
        keys.add("TIMI III flow");
        values.add(timiFlow == null ? "" : timiFlow);
        keys.add("Time to first medical contact");
        values.add(timeFirstMedical == null ? "" : timeFirstMedical);
        keys.add("DBT");
        values.add(dbt == null ? "" : dbt);
        keys.add("Complications");
        values.add(complications == null ? "" : complications);
        keys.add("Bifurcation PCI");
        values.add(bifurcation == null ? "" : bifurcation);
        keys.add("LMCA PCI");
        values.add(lmca == null ? "" : lmca);
        keys.add("Special balloons- Cutting/DEB/OPN");
        values.add(specialBalloons == null ? "" : specialBalloons);
        keys.add("Guide extension catheter");
        values.add(guideCatheter == null ? "" : guideCatheter);
        keys.add("IVUS findings");
        values.add(ivusFindings == null ? "" : ivusFindings);
        keys.add("OCT findings");
        values.add(octFindings == null ? "" : octFindings);
        keys.add("Rotablation");
        values.add(rotablation == null ? "" : rotablation);


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

        keys.add("SYNTAX score");
        values.add(syntaxScore == null ? "" : syntaxScore);

        keys.add("Final impression");
        values.add(finalImpression == null ? "" : finalImpression);


        this.additionalDetails.forEach((k, v) -> keys.add(k));
        this.additionalDetails.forEach((k, v) -> values.add(v));

        if (!forPrint)
            keys.add("Date of PCI procedure");
        if ( !forPrint)
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

    public HashMap<String, String> getStentAdditional() {
        return stentAdditional;
    }

    public void setStentAdditional(HashMap<String, String> stentAdditional) {
        this.stentAdditional = stentAdditional;
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

    public String getStentField() {
        return stentField;
    }

    public void setStentField(String stentField) {
        this.stentField = stentField;
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

    public String getPostDilation() {
        return postDilation == null ? "" : postDilation;
    }

    public void setPostDilation(String postDilation) {
        this.postDilation = postDilation;
    }

    public String getTimiFlow() {
        return timiFlow == null ? "" : timiFlow;
    }

    public void setTimiFlow(String timiFlow) {
        this.timiFlow = timiFlow;
    }

    public String getTimeFirstMedical() {
        return timeFirstMedical == null ? "" : timeFirstMedical;
    }

    public void setTimeFirstMedical(String timeFirstMedical) {
        this.timeFirstMedical = timeFirstMedical;
    }

    public String getSpecialBalloons() {
        return specialBalloons == null ? "" : specialBalloons;
    }

    public void setSpecialBalloons(String specialBalloons) {
        this.specialBalloons = specialBalloons;
    }

    public String getGuideCatheter() {
        return guideCatheter == null ? "" : guideCatheter;
    }

    public void setGuideCatheter(String guideCatheter) {
        this.guideCatheter = guideCatheter;
    }

    public String getIvusFindings() {
        return ivusFindings == null ? "" : ivusFindings;
    }

    public void setIvusFindings(String ivusFindings) {
        this.ivusFindings = ivusFindings;
    }

    public String getOctFindings() {
        return octFindings == null ? "" : octFindings;
    }

    public void setOctFindings(String octFindings) {
        this.octFindings = octFindings;
    }

    public String getRotablation() {
        return rotablation;
    }

    public void setRotablation(String rotablation) {
        this.rotablation = rotablation;
    }

    public String getLmcaDisease() {
        return lmcaDisease;
    }

    public void setLmcaDisease(String lmcaDisease) {
        this.lmcaDisease = lmcaDisease;
    }

    public String getLmcaDiseaseField() {
        return lmcaDiseaseField;
    }

    public void setLmcaDiseaseField(String lmcaDiseaseField) {
        this.lmcaDiseaseField = lmcaDiseaseField;
    }

    public String getBipurificationDisease() {
        return bipurificationDisease;
    }

    public void setBipurificationDisease(String bipurificationDisease) {
        this.bipurificationDisease = bipurificationDisease;
    }

    public String getBipurificationDiseaseField() {
        return bipurificationDiseaseField;
    }

    public void setBipurificationDiseaseField(String bipurificationDiseaseField) {
        this.bipurificationDiseaseField = bipurificationDiseaseField;
    }

    public String getFinalImpression() {
        return finalImpression;
    }

    public void setFinalImpression(String finalImpression) {
        this.finalImpression = finalImpression;
    }

    public String getCatheter() {
        return catheter == null ? "" : catheter;
    }

    public void setCatheter(String catheter) {
        this.catheter = catheter;
    }

    public String getWire() {
        return wire == null ? "" : wire;
    }

    public void setWire(String wire) {
        this.wire = wire;
    }

    public String getThrombus() {
        return thrombus == null ? "" : thrombus;
    }

    public void setThrombus(String thrombus) {
        this.thrombus = thrombus;
    }

    public String getPredilation() {
        return predilation == null ? "" : predilation;
    }

    public void setPredilation(String predilation) {
        this.predilation = predilation;
    }

    public String getStent() {
        return stent == null ? "" : stent;
    }

    public void setStent(String stent) {
        this.stent = stent;
    }


    public String getDbt() {
        return dbt == null ? "" : dbt;
    }

    public void setDbt(String dbt) {
        this.dbt = dbt;
    }

    public String getComplications() {
        return complications == null ? "" : complications;
    }

    public void setComplications(String complications) {
        this.complications = complications;
    }

    public String getBifurcation() {
        return bifurcation == null ? "" : bifurcation;
    }

    public void setBifurcation(String bifurcation) {
        this.bifurcation = bifurcation;
    }

    public String getLmca() {
        return lmca == null ? "" : lmca;
    }

    public void setLmca(String lmca) {
        this.lmca = lmca;
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

    public String getSyntaxScore() {
        return syntaxScore;
    }

    public void setSyntaxScore(String syntaxScore) {
        this.syntaxScore = syntaxScore;
    }

    public HashMap<String, String> getAccessRouteAdditional() {
        return accessRouteAdditional;
    }

    public void setAccessRouteAdditional(HashMap<String, String> accessRouteAdditional) {
        this.accessRouteAdditional = accessRouteAdditional;
    }
}
