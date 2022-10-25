module hr.reversi {
    requires javafx.controls;
    requires javafx.fxml;


    opens hr.reversi to javafx.fxml;
    exports hr.reversi;
}