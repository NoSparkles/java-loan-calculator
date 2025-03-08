package loan.calculator;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainViewController {
    @FXML private TextField loanAmount;
    @FXML private TextField termYears;
    @FXML private TextField termMonths;
    @FXML private TextField annualRate;
    @FXML private ChoiceBox<String> scheduleType;
    @FXML private TextField fromDelay;
    @FXML private TextField delay;
    @FXML private TextField fromMonth;
    @FXML private TextField toMonth;
    @FXML private Button showGraphButton;

    @FXML private TableView<PaymentSchedule> scheduleTable;
    @FXML private TableColumn<PaymentSchedule, Integer> monthColumn;
    @FXML private TableColumn<PaymentSchedule, Double> interestColumn;
    @FXML private TableColumn<PaymentSchedule, Double> paymentColumn;
    @FXML private TableColumn<PaymentSchedule, Double> balanceColumn;

    PaymentSchedule[] schedule = null;

    @FXML
    public void initialize() {
        scheduleType.setItems(FXCollections.observableArrayList("Anuitetas", "Linijinis"));
        scheduleType.setValue("Anuitetas");

        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        interestColumn.setCellValueFactory(new PropertyValueFactory<>("interestPayment"));
        paymentColumn.setCellValueFactory(new PropertyValueFactory<>("totalPayment"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("remainingBalance"));
    }

    public void restoreState(LoanState state) {
        this.loanAmount.setText(state.getLoanAmount());
        this.termYears.setText(state.getTermYears());
        this.termMonths.setText(state.getTermMonths());
        this.annualRate.setText(state.getAnnualRate());
        this.fromDelay.setText(state.getFromDelay());
        this.delay.setText(state.getDelay());
        this.fromMonth.setText(state.getFromMonth());
        this.toMonth.setText(state.getToMonth());
        this.scheduleType.setValue(state.getScheduleType());
        this.schedule = state.getPaymentSchedule();
    }

    @FXML
    public void setSchedule(PaymentSchedule[] schedule) {
        this.schedule = schedule;
    }

    @FXML
    public void loanAmountOnTyped() {
        if (this.loanAmount.getText().length() == 0) {
            return;
        }
        try {
            int amount = Integer.parseInt(this.loanAmount.getText());
            if (amount < 0 || amount > 1000000) {
                this.loanAmount.setText("1000000");
            }
        } 
        catch (NumberFormatException exception) {
            this.loanAmount.setText("");
        }
    }

    @FXML
    public void termYearsOnTyped() {
        if (this.termYears.getText().length() == 0) {
            return;
        }
        try {
            int years = Integer.parseInt(this.termYears.getText());
            if (years < 0) {
                this.termYears.setText("0");
            }
            else if (years > 30) {
                this.termYears.setText("30");
            }
        } 
        catch (NumberFormatException exception) {
            this.termYears.setText("");
        }
    }

    @FXML
    public void termMonthsOnTyped() {
        if (this.termMonths.getText().length() == 0) {
            return;
        }
        try {
            int months = Integer.parseInt(this.termMonths.getText());
            if (months < 0) {
                this.termMonths.setText("0");
            }
            else if (months > 11) {
                this.termMonths.setText("11");
            }
        } 
        catch (NumberFormatException exception) {
            this.termMonths.setText("");
        }
    }

    @FXML
    public void annualRateOnTyped() {
        if (this.annualRate.getText().length() == 0) {
            return;
        }
        try {
            int rate = Integer.parseInt(this.annualRate.getText());
            if (rate < 0) {
                this.annualRate.setText("0");
            }
            else if (rate > 200) {
                this.annualRate.setText("200");
            }
        } 
        catch (NumberFormatException exception) {
            this.annualRate.setText("");
        }
    }

    @FXML
    public void fromDelayOnTyped() {
        if (this.fromDelay.getText().length() == 0) {
            return;
        }
        try {
            int months = Integer.parseInt(this.fromDelay.getText());
            if (months < 1) {
                this.fromDelay.setText("1");
            }
        } 
        catch (NumberFormatException exception) {
            this.fromDelay.setText("");
        }
    }

    @FXML
    public void delayOnTyped() {
        if (this.delay.getText().length() == 0) {
            return;
        }
        try {
            int months = Integer.parseInt(this.delay.getText());
            if (months < 0) {
                this.delay.setText("0");
            }
            else if (months > Integer.parseInt(this.termYears.getText()) * 12 + Integer.parseInt(this.termMonths.getText())) {
                this.delay.setText(String.valueOf(Integer.parseInt(this.termYears.getText()) * 12 + Integer.parseInt(this.termMonths.getText()) - 1));
            }
        } 
        catch (NumberFormatException exception) {
            this.delay.setText("");
        }
    }

    @FXML
    public void fromMonthOnTyped() {
        if (this.fromMonth.getText().length() == 0) {
            return;
        }
        try {
            int months = Integer.parseInt(this.fromMonth.getText());
            if (months < 1) {
                this.fromMonth.setText("1");
            }
        } 
        catch (NumberFormatException exception) {
            this.fromMonth.setText("");
        }
    }

    @FXML
    public void toMonthOnTyped() {
        if (this.toMonth.getText().length() == 0) {
            return;
        }
        try {
            int months = Integer.parseInt(this.toMonth.getText());
            if (months < 1) {
                this.toMonth.setText("1");
            }
        } 
        catch (NumberFormatException exception) {
            this.fromMonth.setText("");
        }
    }

    @FXML
    public void calculate() {
        if (this.loanAmount.getText().length() == 0) {
            this.loanAmount.setText("0");
        }
        if (this.termYears.getText().length() == 0) {
            this.termYears.setText("0");
        }
        if (this.termMonths.getText().length() == 0) {
            this.termMonths.setText("0");
        }
        if (this.termYears.getText().equals("0") && this.termMonths.getText().equals("0")) {
            this.termMonths.setText("1");
        }
        if (this.annualRate.getText().length() == 0) {
            this.annualRate.setText("0");
        }
        if (this.fromDelay.getText().length() == 0) {
            this.fromDelay.setText("1");
        }
        if (Integer.parseInt(this.fromDelay.getText()) > Integer.parseInt(this.termYears.getText()) * 12 + Integer.parseInt(this.termMonths.getText())) {
            this.fromDelay.setText(String.valueOf(Integer.parseInt(this.termYears.getText()) * 12 + Integer.parseInt(this.termMonths.getText())));
        }
        if (this.delay.getText().length() == 0) {
            this.delay.setText("0");
        }
        if (Integer.parseInt(this.delay.getText()) + Integer.parseInt(this.fromDelay.getText()) > Integer.parseInt(this.termYears.getText()) * 12 + Integer.parseInt(this.termMonths.getText())) {
            this.delay.setText(String.valueOf(Integer.parseInt(this.termYears.getText()) * 12 + Integer.parseInt(this.termMonths.getText()) - Integer.parseInt(this.fromDelay.getText())));
        }
        if (this.fromMonth.getText().length() == 0) {
            this.fromMonth.setText("1");
        }
        if (Integer.parseInt(this.fromMonth.getText()) > Integer.parseInt(this.termYears.getText()) * 12 + Integer.parseInt(this.termMonths.getText())) {
            this.fromMonth.setText(String.valueOf(Integer.parseInt(this.termYears.getText()) * 12 + Integer.parseInt(this.termMonths.getText())));
        }
        if (this.toMonth.getText().length() == 0) {
            this.toMonth.setText(String.valueOf(Integer.parseInt(this.termYears.getText()) * 12 + Integer.parseInt(this.termMonths.getText())));
        }
        if (Integer.parseInt(this.toMonth.getText()) < Integer.parseInt(this.fromMonth.getText())) {
            this.toMonth.setText(String.valueOf(Integer.parseInt(this.fromMonth.getText())));
        }
        if (Integer.parseInt(this.toMonth.getText()) > Integer.parseInt(this.termYears.getText()) * 12 + Integer.parseInt(this.termMonths.getText())) {
            this.toMonth.setText(String.valueOf(Integer.parseInt(this.termYears.getText()) * 12 + Integer.parseInt(this.termMonths.getText())));
        }

        try {
            double amount = Double.parseDouble(this.loanAmount.getText());
            int years = Integer.parseInt(this.termYears.getText());
            int months = Integer.parseInt(this.termMonths.getText());
            double rate = Double.parseDouble(this.annualRate.getText());
            String scheduleType = this.scheduleType.getValue();
            int fromDelay = Integer.parseInt(this.fromDelay.getText()) - 1;
            int delay = Integer.parseInt(this.delay.getText());
            int fromMonth = Integer.parseInt(this.fromMonth.getText()) - 1;
            int toMonth = Integer.parseInt(this.toMonth.getText()) - 1;

            Loan loan;
            if ("Anuitetas".equals(scheduleType)) {
                loan = new AnnuityLoan(amount, years, months, rate, fromDelay, delay);
            }
            else {
                loan = new LinearLoan(amount, years, months, rate, fromDelay, delay);
            }
            this.schedule = loan.getPaymentSchedule(fromMonth, toMonth);
            
            ObservableList<PaymentSchedule> data = FXCollections.observableArrayList(this.schedule);
            scheduleTable.getItems().clear();
            scheduleTable.setItems(data);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid input: " + ex.getMessage());
        }
    }

    @FXML
    public void showGraph() throws IOException {
        if (this.schedule == null) {
            return;
        }

        LoanState state = LoanState.getInstance();
        state.setLoanAmount(this.loanAmount.getText());
        state.setTermYears(this.termYears.getText());
        state.setTermMonths(this.termMonths.getText());
        state.setAnnualRate(this.annualRate.getText());
        state.setFromDelay(this.fromDelay.getText());
        state.setDelay(this.delay.getText());
        state.setFromMonth(this.fromMonth.getText());
        state.setToMonth(this.toMonth.getText());
        state.setScheduleType(this.scheduleType.getValue());
        state.setPaymentSchedule(this.schedule);

        // Load the GraphView scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GraphView.fxml"));
        Parent root = loader.load();

        GraphViewController controller = loader.getController();
        controller.initializeLineChart();

        // Set the new scene
        Stage stage = (Stage) showGraphButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void saveToFile() {
        if (this.schedule == null || this.schedule.length == 0) {
            System.out.println("No data to save!");
            return;
        }

        // Open file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(scheduleTable.getScene().getWindow());

        if (file != null) {
            try (PDDocument document = new PDDocument()) {
                // Create a new page
                PDPage page = new PDPage();
                document.addPage(page);

                // Initialize content stream
                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, 750);
                    contentStream.showText("Loan Payment Schedule");
                    contentStream.endText();

                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, 730);

                    // Table headers
                    String header = String.format("%-10s %-15s %-15s %-15s",
                        "Month", "Interest", "Total Payment", "Remaining Balance");
                    contentStream.showText(header);
                    contentStream.newLineAtOffset(0, -20);

                    // Table content
                    for (PaymentSchedule ps : this.schedule) {
                        String row = String.format("%-10s %-15.2f %-15.2f %-15.2f",
                                ps.getMonth(),
                                ps.getInterestPayment(),
                                ps.getTotalPayment(),
                                ps.getRemainingBalance());
                        contentStream.showText(row);
                        contentStream.newLineAtOffset(0, -15);
                    }
                    contentStream.endText();
                }

                // Save the document
                document.save(file);
                System.out.println("PDF saved: " + file.getAbsolutePath());
            } catch (IOException ex) {
                System.out.println("Error saving PDF: " + ex.getMessage());
            }
        }
    }
}
