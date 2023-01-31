module hr.reversi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;


    opens hr.reversi to javafx.fxml;
    exports hr.reversi;
    exports hr.reversi.controller;
    opens hr.reversi.controller to javafx.fxml;
}