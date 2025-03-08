module loan.calculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.ooxml;

    opens loan.calculator to javafx.fxml;
    exports loan.calculator;
}
