package hospital.java.models;

public class Patient {
    private int id;
    private String name;
    private String UHID;
    private int age;
    private String sex;
    private String riskFactors;
    private String otherComorbidities;
    private String cad;
    private String treatmentForPastCad;
    private String echo;
    private String currentDiagnosis;
    private String coronaryAngiography;
    private String pci;
    private String complicationsInHospitalPredischarge;
    private String postPci;
<<<<<<< HEAD

    public static String getDetailsString(Patient patient) {
        return "<h1>" + patient.getName() + "</h1>"
                + "<br><b>UHID: </b>" + patient.getUHID()
                + "<br><b>Age: </b>" + patient.getAge()
                + "<br><b>Sex: </b>" + patient.getSex()
                + "<br><b>Risk Factors: </b>" + patient.getRiskFactors()
                + "<br><b>Other Comorbidities: </b>" + patient.getOtherComorbidities()
                + "<br><b>Past h/o CAD: </b>" + patient.getCad()
                + "<br><b>Treatment For Past CAD: </b>" + patient.getTreatmentForPastCad()
                + "<br><b>Echo: </b>" + patient.getEcho()
                + "<br><b>Current Diagnosis: </b>" + patient.getCurrentDiagnosis()
                + "<br><b>Coronary Angiography: </b>" + patient.getCoronaryAngiography()
                + "<br><b>PCI: </b>" + patient.getPci()
                + "<br><b>Complications In Hospital Predischarge: </b>" + patient.getComplicationsInHospitalPredischarge()
                + "<br><b>Post PCI: </b>" + patient.getPostPci();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
=======
    
    public static String getDetailsString(Patient patient) {
        return patient.getName()
            + "\n\nUHID: " + patient.getUHID()
            + "\n\nAge: " + patient.getAge()
            + "\n\nSex: " + patient.getSex()
            + "\n\nRisk Factors: " + patient.getRiskFactors()
            + "\n\nOther Comorbidities: " + patient.getOtherComorbidities()
            + "\n\nPast h/o CAD: " + patient.getCad()
            + "\n\nTreatment For Past CAD: " + patient.getTreatmentForPastCad()
            + "\n\nEcho: " + patient.getEcho()
            + "\n\nCurrent Diagnosis: " + patient.getCurrentDiagnosis()
            + "\n\nCoronary Angiography: " + patient.getCoronaryAngiography()
            + "\n\nPCI: " + patient.getPci()
            + "\n\nComplications In Hospital Predischarge: " + patient.getComplicationsInHospitalPredischarge()
            + "\n\nPost PCI: " + patient.getPostPci();
            
    }

    public String getPostPci() {
        return postPci;
    }
    public String getEcho() {
        return echo;
    }
    public void setEcho(String echo) {
        this.echo = echo;
    }
    public String getCad() {
        return cad;
    }
    public void setCad(String cad) {
        this.cad = cad;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
>>>>>>> 67117836d2a97fe10036dc06ddffb442394d1e9d
    }

    public String getUHID() {
        return UHID;
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
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRiskFactors() {
        return riskFactors;
    }

    public void setRiskFactors(String riskFactors) {
        this.riskFactors = riskFactors;
    }

    public String getOtherComorbidities() {
        return otherComorbidities;
    }
<<<<<<< HEAD

    public void setOtherComorbidities(String otherComorbidities) {
        this.otherComorbidities = otherComorbidities;
    }

    public String getCad() {
        return cad;
    }

    public void setCad(String cad) {
        this.cad = cad;
    }

    public String getTreatmentForPastCad() {
        return treatmentForPastCad;
    }

    public void setTreatmentForPastCad(String treatmentForPastCad) {
        this.treatmentForPastCad = treatmentForPastCad;
    }

    public String getEcho() {
        return echo;
    }

    public void setEcho(String echo) {
        this.echo = echo;
    }

    public String getCurrentDiagnosis() {
        return currentDiagnosis;
    }

    public void setCurrentDiagnosis(String currentDiagnosis) {
        this.currentDiagnosis = currentDiagnosis;
    }

    public String getCoronaryAngiography() {
        return coronaryAngiography;
    }

    public void setCoronaryAngiography(String coronaryAngiography) {
        this.coronaryAngiography = coronaryAngiography;
    }

    public String getPci() {
        return pci;
    }

    public void setPci(String pci) {
        this.pci = pci;
    }

    public String getComplicationsInHospitalPredischarge() {
        return complicationsInHospitalPredischarge;
    }

    public void setComplicationsInHospitalPredischarge(String complicationsInHospitalPredischarge) {
        this.complicationsInHospitalPredischarge = complicationsInHospitalPredischarge;
    }

    public String getPostPci() {
        return postPci;
    }

=======
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUHID() {
        return UHID;
    }
    public void setUHID(String uHID) {
        this.UHID = uHID;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getRiskFactors() {
        return riskFactors;
    }
    public void setRiskFactors(String riskFactors) {
        this.riskFactors = riskFactors;
    }
    public String getOtherComorbidities() {
        return otherComorbidities;
    }
    public void setOtherComorbidities(String otherComorbidities) {
        this.otherComorbidities = otherComorbidities;
    }
    public String getTreatmentForPastCad() {
        return treatmentForPastCad;
    }
    public void setTreatmentForPastCad(String treatmentForPastCad) {
        this.treatmentForPastCad = treatmentForPastCad;
    }
    public String getCurrentDiagnosis() {
        return currentDiagnosis;
    }
    public void setCurrentDiagnosis(String currentDiagnosis) {
        this.currentDiagnosis = currentDiagnosis;
    }
    public String getCoronaryAngiography() {
        return coronaryAngiography;
    }
    public void setCoronaryAngiography(String coronaryAngiography) {
        this.coronaryAngiography = coronaryAngiography;
    }
    public String getPci() {
        return pci;
    }
    public void setPci(String pci) {
        this.pci = pci;
    }
    public String getComplicationsInHospitalPredischarge() {
        return complicationsInHospitalPredischarge;
    }
    public void setComplicationsInHospitalPredischarge(String complicationsInHospitalPredischarge) {
        this.complicationsInHospitalPredischarge = complicationsInHospitalPredischarge;
    }
>>>>>>> 67117836d2a97fe10036dc06ddffb442394d1e9d
    public void setPostPci(String postPci) {
        this.postPci = postPci;
    }
}
