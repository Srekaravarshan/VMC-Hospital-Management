package hospital.java.models;

import javafx.beans.property.SimpleStringProperty;

public class PatientData {
    private final SimpleStringProperty title = new SimpleStringProperty();
    private final SimpleStringProperty detail = new SimpleStringProperty();

    public PatientData(String title, String detail) {
        this.title.set(title);
        this.detail.set(detail);
    }

    public String getTitle() {
        return title.get();
    }

    public String getDetail() {
        return detail.get();
    }

}