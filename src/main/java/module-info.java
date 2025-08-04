module it.r2u.animar2u {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.web;
    requires transitive javafx.graphics;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens it.r2u.animar2u to javafx.fxml;
    opens it.r2u.animar2u.ui.controllers to javafx.fxml;
    
    exports it.r2u.animar2u;
    exports it.r2u.animar2u.ui.controllers;
    exports it.r2u.animar2u.core.tab_management;
    exports it.r2u.animar2u.core.storage;
    exports it.r2u.animar2u.core.navigation;
    exports it.r2u.animar2u.core.config;
    exports it.r2u.animar2u.core.initialization;
    exports it.r2u.animar2u.media;
}