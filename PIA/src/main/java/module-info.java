// module-info.java
module com.gympos {
    requires transitive javafx.graphics;
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires itextpdf;

    exports com.gympos.app;
    exports com.gympos.controller;
    exports com.gympos.model;
    exports com.gympos.service;
    exports com.gympos.util;

    opens com.gympos.controller to javafx.fxml;
}
