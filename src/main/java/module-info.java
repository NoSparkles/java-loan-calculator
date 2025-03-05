module loan.calculator {
    requires javafx.controls;
    requires javafx.fxml;

    opens loan.calculator to javafx.fxml;
    exports loan.calculator;
}
