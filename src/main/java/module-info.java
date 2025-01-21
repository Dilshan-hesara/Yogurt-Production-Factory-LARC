module lk.edu.yogurtproduction.yogurtproductionitsolution {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires static lombok;
    requires java.sql;
    requires com.jfoenix;
    requires net.sf.jasperreports.core;
    requires java.mail;
    requires com.google.protobuf;

    opens lk.edu.yogurtproduction.yogurtproductionitsolution.controller to javafx.fxml;
    opens lk.edu.yogurtproduction.yogurtproductionitsolution.dto.TM to javafx.base;
    exports lk.edu.yogurtproduction.yogurtproductionitsolution;
}