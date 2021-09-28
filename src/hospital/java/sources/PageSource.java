package hospital.java.sources;

import hospital.java.controllers.Page2Controller;
import hospital.java.controllers.Page3Controller;
import hospital.java.controllers.PageController;
import hospital.java.models.CagModel;
import hospital.java.models.Patient;
import hospital.java.models.PciModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.util.ArrayList;

public class PageSource {

    private boolean editing = false;
    private ArrayList<FXMLLoader> fxmlLoaders = new ArrayList<>();
    private ArrayList<javafx.scene.Node> pages = new ArrayList<>();

    private PageController page1Controller;
    private Page2Controller page2Controller;
    private Page3Controller page3Controller;

    private Patient patient;
    private CagModel cag;
    private PciModel pci;

    private int pageIndex = 0;

    public static PageSource form = new PageSource();
    public static PageSource editForm = new PageSource();

    private PageSource() {}

    public void reset () {
        page1Controller = null;
        page2Controller = null;
        page3Controller = null;
        patient = null;
        cag = null;
        pci = null;
        editing = false;
        fxmlLoaders = new ArrayList<>();
        pages = new ArrayList<>();
    }

    public ArrayList<FXMLLoader> fxmlLoaders() {
        return fxmlLoaders;
    }

    public void setFxmlLoaders(ArrayList<FXMLLoader> fxmlLoaders) {
        this.fxmlLoaders = fxmlLoaders;
    }

    public ArrayList<Node> pages() {
        return pages;
    }

    public CagModel getCag() {
        return cag;
    }

    public void setCag(CagModel cag) {
        this.cag = cag;
    }

    public PciModel getPci() {
        return pci;
    }

    public void setPci(PciModel pci) {
        this.pci = pci;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPages(ArrayList<Node> pages) {
        this.pages = pages;
    }

    public PageController page1Controller() {
        return page1Controller;
    }

    public void setPage1Controller(PageController page1Controller) {
        this.page1Controller = page1Controller;
    }

    public Page2Controller page2Controller() {
        return page2Controller;
    }

    public void setPage2Controller(Page2Controller page2Controller) {
        this.page2Controller = page2Controller;
    }

    public Page3Controller page3Controller() {
        return page3Controller;
    }

    public void setPage3Controller(Page3Controller page3Controller) {
        this.page3Controller = page3Controller;
    }

    public int pageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public static PageSource form() {
        return form;
    }

    public static void setForm(PageSource form) {
        PageSource.form = form;
    }

    public static PageSource editForm() {
        return editForm;
    }

    public static void setEditForm(PageSource editForm) {
        PageSource.editForm = editForm;
    }

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
//        if ( !editing ) {
//            fxmlLoaders.clear();
//            pages.clear();
//            page1Controller = null;
//            page2Controller = null;
//            page3Controller = null;
//            patient = null;
//            cag = null;
//            pci = null;
//        }
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
