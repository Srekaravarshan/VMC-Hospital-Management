package hospital.java.models;

import javafx.beans.property.SimpleStringProperty;

public class PatientData {
    private final SimpleStringProperty title = new SimpleStringProperty();
    private final SimpleStringProperty detail = new SimpleStringProperty();

    private final SimpleStringProperty sno = new SimpleStringProperty();
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty age = new SimpleStringProperty();
    private final SimpleStringProperty sex = new SimpleStringProperty();
    private final SimpleStringProperty UHID = new SimpleStringProperty();
    private final SimpleStringProperty operator = new SimpleStringProperty();

    public PatientData(String title, String detail, String sno, String name, String age, String sex, String UHID, String operator) {
        this.title.set(title);
        this.detail.set(detail);
        this.sno.set(sno);
        this.name.set(name);
        this.age.set(age);
        this.sex.set(sex);
        this.UHID.set(UHID);
        this.operator.set(operator);
    }

    public String getName() {
        return name.get();
    }

    public String getAge() {
        return age.get();
    }


    public String getSex() {
        return sex.get();
    }

    public String getUHID() {
        return UHID.get();
    }

    public String getDetail() {
        return detail.get();
    }

    public String getSno() {
        return sno.get();
    }

    public String getTitle() {
        return title.get();
    }

    public String getOperator() {
        return operator.get();
    }

}