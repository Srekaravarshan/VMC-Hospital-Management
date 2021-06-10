module Hospital.Management {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.base;
    requires java.prefs;
    requires java.sql;
    requires java.desktop;
    requires javafx.web;
    requires com.mysql.jdbc;

    exports hospital.java.models;
    exports hospital.java.controllers;

    opens hospital.java.controllers;
    opens hospital;
}