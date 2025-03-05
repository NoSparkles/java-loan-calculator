package loan.calculator;

public class PaymentSchedule {
    private double monthlyPayment;
    private double interestPart;
    private double principalPart;

    public PaymentSchedule(double monthlyPayment, double interestPart, double principalPart) {
        this.monthlyPayment = monthlyPayment;
        this.interestPart = interestPart;
        this.principalPart = principalPart;
    }

    // Getter and Setter methods

    // Override methods
    @Override
    public String toString() {
        return "PaymentSchedule [monthlyPayment=" + monthlyPayment + ", interestPart=" + interestPart
                + ", principalPart=" + principalPart + "]";
    }
}
