module battleship.battleshipfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires eu.hansolo.tilesfx;

    opens battleship.battleshipfx to javafx.fxml;
    exports battleship.battleshipfx;
}