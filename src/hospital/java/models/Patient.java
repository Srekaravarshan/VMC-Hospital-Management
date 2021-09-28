package hospital.java.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static hospital.java.helpers.HelperMethods.getKeyValueString;
import static hospital.java.helpers.HelperMethods.getKeysValues;

public class Patient {

    private String sno;
    private int id;
    private String name;
    private String UHID;
    private int age;
    private String sex;
    private String complaints;
    private String riskFactors;
    private String otherComorbidities;
    private String cad;
    private String treatmentForPastCad;
    private String echo;
    private String currentDiagnosis;
    private String complicationsInHospitalPredischarge;
    private String operator;

    private String complaintsField;
    private String riskFactorsField;
    private HashMap<String, String> complaintsAdditional;
    private HashMap<String, String> riskFactorsAdditional;
    private String otherComorbiditiesField;
    private HashMap<String, String> otherComorbiditiesAdditional;
    private String pastCadField;
    private HashMap<String, String> pastCadAdditional;
    private String treatmentPastCadField;
    private HashMap<String, String> treatmentPastCadAdditional;
    private String echoField;
    private HashMap<String, String> echoAdditional;
    private String currentDiagnosisField;
    private HashMap<String, String> currentDiagnosisAdditional;
    private String complicationsField;
    private HashMap<String, String> complicationsAdditional;

    private HashMap<String, String> additionalDetails;

    private LocalDate date;
    private LocalDate dopCag;
    private LocalDate dopPci;

    private boolean hasCag;
    private boolean hasPci;
    private boolean hasLmca;
    private boolean hasBifurication;
    private boolean hasStentLad;
    private boolean hasStentLcx;
    private boolean hasStentRca;
    private boolean hasStentLmca;
    private boolean hasStentDiagonal;
    private boolean hasStentOm;

    public Patient() {
        complaintsAdditional = new HashMap<>();
        riskFactorsAdditional = new HashMap<>();
        otherComorbiditiesAdditional = new HashMap<>();
        pastCadAdditional = new HashMap<>();
        treatmentPastCadAdditional = new HashMap<>();
        echoAdditional = new HashMap<>();
        currentDiagnosisAdditional = new HashMap<>();
        complicationsAdditional = new HashMap<>();
        additionalDetails = new HashMap<>();
    }

    public static ArrayList<String> getValueString(String text2, HashMap<String, String> map) {

        ArrayList<String> strings = new ArrayList<>();
        strings.add((text2 == null || text2.trim().equals("")) ? "" : (text2));

        for (Map.Entry<String, String> entry : map.entrySet()) {
            strings.add(entry.getValue());
        }
        return strings;
    }

//    getKeysValues(keys, values, patient.getComplaints(), patient.getComplaintsAdditional(), patient.getComplaintsField());
//    getKeysValues(keys, values, patient.getRiskFactors(), patient.getRiskFactorsAdditional(), patient.getRiskFactorsField());
//    getKeysValues(keys, values, patient.getOtherComorbidities(), patient.getOtherComorbiditiesAdditional(), patient.getOtherComorbiditiesField());
//    getKeysValues(keys, values, patient.getCad(), patient.getPastCadAdditional(), patient.getPastCadField());
//    getKeysValues(keys, values, patient.getTreatmentForPastCad(), patient.getTreatmentPastCadAdditional(), patient.getTreatmentPastCadField());
//    getKeysValues(keys, values, patient.getEcho(), patient.getEchoAdditional(), patient.getEchoField());
//    getKeysValues(keys, values, patient.getCurrentDiagnosis(), patient.getCurrentDiagnosisAdditional(), patient.getCurrentDiagnosisField());
//    getKeysValues(keys, values, patient.getComplicationsInHospitalPredischarge(), patient.getComplicationsAdditional(), patient.getComplicationsField());

    @Override
    public String toString() {
        return name +
                " " + UHID +
                " " + age +
                " " + sex +
                " " + complaints +
                " " + riskFactors +
                " " + otherComorbidities +
                " " + cad +
                " " + treatmentForPastCad +
                " " + echo +
                " " + currentDiagnosis +
                " " + complicationsInHospitalPredischarge +
                " " + operator +
                " " + complaintsField +
                " " + riskFactorsField +
                " " + complaintsAdditional +
                " " + riskFactorsAdditional +
                " " + otherComorbiditiesField +
                " " + otherComorbiditiesAdditional +
                " " + pastCadField +
                " " + pastCadAdditional +
                " " + treatmentPastCadField +
                " " + treatmentPastCadAdditional +
                " " + echoField +
                " " + echoAdditional +
                " " + currentDiagnosisField +
                " " + currentDiagnosisAdditional +
                " " + complicationsField +
                " " + complicationsAdditional +
                " " + additionalDetails +
                " " + date +
                " " + (hasCag ? "cag" : "") +
                " " + (hasPci ? "pci" : "") +
                " " + (hasLmca ? "lmca" : "") +
                " " + (hasBifurication ? "bifurication" : "") +
                " " + (hasStentLad ? "lad" : "") +
                " " + (hasStentLcx ? "lcx" : "") +
                " " + (hasStentRca ? "rca" : "") +
                " " + (hasStentLmca ? "lmca" : "") +
                " " + (hasStentDiagonal ? "diagonal" : "") +
                " " + (hasStentOm ? "dm" : "");
    }

    public HashMap<String, ArrayList<String>> getsKeys(boolean forPrint) {
        ArrayList<String> dkeys = new ArrayList<>();
        ArrayList<String> dvalues = new ArrayList<>();

        if (!forPrint) {
            dkeys.add("Name");
            dvalues.add(name == null ? "" : name);

            dkeys.add("UHID");
            dvalues.add(UHID == null ? "" : UHID);

            dkeys.add("Age");
            dvalues.add(String.valueOf(age));

            dkeys.add("Sex");
            dvalues.add(sex == null ? "" : sex);
        }

        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();

        getKeysValues(keys, values, this.getComplaints(), this.getComplaintsAdditional(), this.getComplaintsField());
        String string = getKeyValueString(keys, values);
        if (!string.trim().equals("")) {
            dkeys.add("Complaints");
            dvalues.add(string);
        }

        getKeysValues(keys, values, this.getRiskFactors(), this.getRiskFactorsAdditional(), this.getRiskFactorsField());
        string = getKeyValueString(keys, values);
        if (!string.trim().equals("")) {
            dkeys.add("Risk factors");
            dvalues.add(string);
        }

        getKeysValues(keys, values, this.getOtherComorbidities(), this.getOtherComorbiditiesAdditional(), this.getOtherComorbiditiesField());
        string = getKeyValueString(keys, values);
        if (!string.trim().equals("")) {
            dkeys.add("Other comorbidities");
            dvalues.add(string);
        }

        getKeysValues(keys, values, this.getCad(), this.getPastCadAdditional(), this.getPastCadField());
        string = getKeyValueString(keys, values);
        if (!string.trim().equals("")) {
            dkeys.add("Past h/o CAD");
            dvalues.add(string);
        }

        getKeysValues(keys, values, this.getTreatmentForPastCad(), this.getTreatmentPastCadAdditional(), this.getTreatmentPastCadField());
        string = getKeyValueString(keys, values);
        if (!string.trim().equals("")) {
            dkeys.add("Treatment for past CAD");
            dvalues.add(string);
        }

        getKeysValues(keys, values, this.getEcho(), this.getEchoAdditional(), this.getEchoField());
        string = getKeyValueString(keys, values);
        if (!string.trim().equals("")) {
            dkeys.add("Echo");
            dvalues.add(string);
        }

        getKeysValues(keys, values, this.getCurrentDiagnosis(), this.getCurrentDiagnosisAdditional(), this.getCurrentDiagnosisField());
        string = getKeyValueString(keys, values);
        if (!string.trim().equals("")) {
            dkeys.add("Current diagnosis");
            dvalues.add(string);
        }

        getKeysValues(keys, values, this.getComplicationsInHospitalPredischarge(), this.getComplicationsAdditional(), this.getComplicationsField());
        string = getKeyValueString(keys, values);
        if (!string.trim().equals("")) {
            dkeys.add("Complications in hospital\npredischarge");
            dvalues.add(string);
        }

        this.additionalDetails.forEach((k, v) -> dkeys.add(k));
        this.additionalDetails.forEach((k, v) -> dvalues.add(v));

        dkeys.add("Operator");
        dvalues.add(operator == null ? "" : operator);

        if (!forPrint) {
            dkeys.add("Date of admission");
            dvalues.add(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
        return new HashMap<>() {{
            put("keys", dkeys);
            put("values", dvalues);
        }};
    }

    public ArrayList<String> getKeys() {
        return getsKeys(false).get("keys");
    }

    public ArrayList<String> getPrintKeys() {
        return getsKeys(true).get("keys");
    }

    public ArrayList<String> getValues() {
        return getsKeys(false).get("values");
    }

    public ArrayList<String> getPrintValues() {
        return getsKeys(true).get("values");
    }

    public HashMap<String, String> getAdditionalDetails() {
        return additionalDetails;
    }

    public void setAdditionalDetails(HashMap<String, String> additionalDetails) {
        this.additionalDetails = additionalDetails;
    }

    public String getComplaints() {
        return complaints;
    }

    public void setComplaints(String complaints) {
        this.complaints = complaints;
    }

    public String getComplaintsField() {
        return complaintsField;
    }

    public void setComplaintsField(String complaintsField) {
        this.complaintsField = complaintsField;
    }

    public HashMap<String, String> getComplaintsAdditional() {
        return complaintsAdditional;
    }

    public void setComplaintsAdditional(HashMap<String, String> complaintsAdditional) {
        this.complaintsAdditional = complaintsAdditional;
    }

    public HashMap<String, String> getRiskFactorsAdditional() {
        return riskFactorsAdditional;
    }

    public void setRiskFactorsAdditional(HashMap<String, String> riskFactorsAdditional) {
        this.riskFactorsAdditional = riskFactorsAdditional;
    }

    public HashMap<String, String> getOtherComorbiditiesAdditional() {
        return otherComorbiditiesAdditional;
    }

    public void setOtherComorbiditiesAdditional(HashMap<String, String> otherComorbiditiesAdditional) {
        this.otherComorbiditiesAdditional = otherComorbiditiesAdditional;
    }

    public HashMap<String, String> getPastCadAdditional() {
        return pastCadAdditional;
    }

    public void setPastCadAdditional(HashMap<String, String> pastCadAdditional) {
        this.pastCadAdditional = pastCadAdditional;
    }

    public HashMap<String, String> getTreatmentPastCadAdditional() {
        return treatmentPastCadAdditional;
    }

    public void setTreatmentPastCadAdditional(HashMap<String, String> treatmentPastCadAdditional) {
        this.treatmentPastCadAdditional = treatmentPastCadAdditional;
    }

    public HashMap<String, String> getEchoAdditional() {
        return echoAdditional;
    }

    public void setEchoAdditional(HashMap<String, String> echoAdditional) {
        this.echoAdditional = echoAdditional;
    }

    public HashMap<String, String> getCurrentDiagnosisAdditional() {
        return currentDiagnosisAdditional;
    }

    public void setCurrentDiagnosisAdditional(HashMap<String, String> currentDiagnosisAdditional) {
        this.currentDiagnosisAdditional = currentDiagnosisAdditional;
    }

    public HashMap<String, String> getComplicationsAdditional() {
        return complicationsAdditional;
    }

    public void setComplicationsAdditional(HashMap<String, String> complicationsAdditional) {
        this.complicationsAdditional = complicationsAdditional;
    }

    public String getRiskFactorsField() {
        return riskFactorsField == null ? "" : riskFactorsField;
    }

    public void setRiskFactorsField(String riskFactorsField) {
        this.riskFactorsField = riskFactorsField;
    }

    public String getOtherComorbiditiesField() {
        return otherComorbiditiesField == null ? "" : otherComorbiditiesField;
    }

    public void setOtherComorbiditiesField(String otherComorbiditiesField) {
        this.otherComorbiditiesField = otherComorbiditiesField;
    }

    public String getPastCadField() {
        return pastCadField == null ? "" : pastCadField;
    }

    public void setPastCadField(String pastCadField) {
        this.pastCadField = pastCadField;
    }

    public String getTreatmentPastCadField() {
        return treatmentPastCadField == null ? "" : treatmentPastCadField;
    }

    public void setTreatmentPastCadField(String treatmentPastCadField) {
        this.treatmentPastCadField = treatmentPastCadField;
    }

    public String getEchoField() {
        return echoField == null ? "" : echoField;
    }

    public void setEchoField(String echoField) {
        this.echoField = echoField;
    }

    public String getCurrentDiagnosisField() {
        return currentDiagnosisField == null ? "" : currentDiagnosisField;
    }

    public void setCurrentDiagnosisField(String currentDiagnosisField) {
        this.currentDiagnosisField = currentDiagnosisField;
    }


    public String getComplicationsField() {
        return complicationsField == null ? "" : complicationsField;
    }

    public void setComplicationsField(String complicationsField) {
        this.complicationsField = complicationsField;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUHID() {
        return UHID == null ? "" : UHID;
    }

    public void setUHID(String UHID) {
        this.UHID = UHID;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex == null ? "" : sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getOperator() {
        return operator == null ? "" : operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRiskFactors() {
        return riskFactors == null ? "" : riskFactors;
    }

    public void setRiskFactors(String riskFactors) {
        this.riskFactors = riskFactors;
    }

    public String getOtherComorbidities() {
        return otherComorbidities == null ? "" : otherComorbidities;
    }

    public void setOtherComorbidities(String otherComorbidities) {
        this.otherComorbidities = otherComorbidities;
    }

    public String getCad() {
        return cad == null ? "" : cad;
    }

    public void setCad(String cad) {
        this.cad = cad;
    }

    public String getTreatmentForPastCad() {
        return treatmentForPastCad == null ? "" : treatmentForPastCad;
    }

    public void setTreatmentForPastCad(String treatmentForPastCad) {
        this.treatmentForPastCad = treatmentForPastCad;
    }

    public String getEcho() {
        return echo == null ? "" : echo;
    }

    public void setEcho(String echo) {
        this.echo = echo;
    }

    public String getCurrentDiagnosis() {
        return currentDiagnosis == null ? "" : currentDiagnosis;
    }

    public void setCurrentDiagnosis(String currentDiagnosis) {
        this.currentDiagnosis = currentDiagnosis;
    }

    public String getComplicationsInHospitalPredischarge() {
        return complicationsInHospitalPredischarge == null ? "" : complicationsInHospitalPredischarge;
    }

    public void setComplicationsInHospitalPredischarge(String complicationsInHospitalPredischarge) {
        this.complicationsInHospitalPredischarge = complicationsInHospitalPredischarge;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isHasCag() {
        return hasCag;
    }

    public void setHasCag(boolean hasCag) {
        this.hasCag = hasCag;
    }

    public boolean isHasPci() {
        return hasPci;
    }

    public void setHasPci(boolean hasPci) {
        this.hasPci = hasPci;
    }

    public boolean isHasLmca() {
        return hasLmca;
    }

    public void setHasLmca(boolean hasLmca) {
        this.hasLmca = hasLmca;
    }

    public boolean isHasBifurication() {
        return hasBifurication;
    }

    public void setHasBifurication(boolean hasBifurication) {
        this.hasBifurication = hasBifurication;
    }

    public boolean isHasStentLad() {
        return hasStentLad;
    }

    public void setHasStentLad(boolean hasStentLad) {
        this.hasStentLad = hasStentLad;
    }

    public boolean isHasStentLcx() {
        return hasStentLcx;
    }

    public void setHasStentLcx(boolean hasStentLcx) {
        this.hasStentLcx = hasStentLcx;
    }

    public boolean isHasStentRca() {
        return hasStentRca;
    }

    public void setHasStentRca(boolean hasStentRca) {
        this.hasStentRca = hasStentRca;
    }

    public boolean isHasStentLmca() {
        return hasStentLmca;
    }

    public void setHasStentLmca(boolean hasStentLmca) {
        this.hasStentLmca = hasStentLmca;
    }

    public boolean isHasStentDiagonal() {
        return hasStentDiagonal;
    }

    public void setHasStentDiagonal(boolean hasStentDiagonal) {
        this.hasStentDiagonal = hasStentDiagonal;
    }

    public boolean isHasStentOm() {
        return hasStentOm;
    }

    public void setHasStentOm(boolean hasStentOm) {

        this.hasStentOm = hasStentOm;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public LocalDate getDopCag() {
        return dopCag;
    }

    public void setDopCag(LocalDate dopCag) {
        this.dopCag = dopCag;
    }

    public LocalDate getDopPci() {
        return dopPci;
    }

    public void setDopPci(LocalDate dopPci) {
        this.dopPci = dopPci;
    }
}
