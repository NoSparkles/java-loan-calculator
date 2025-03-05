package loan.calculator;

public class Loan {
    private double amount;
    private int termYears;
    private int termMonths;
    private double annualRate;
    private double remainingAmount;

    public Loan(double amount, int termYears, int termMonths, double annualRate) {
        this.amount = amount;
        this.termYears = termYears;
        this.termMonths = termMonths;
        this.annualRate = annualRate;
        this.remainingAmount = amount;
    }

    // Getter and Setter methods

    public double getMonthlyPayment() {
        // Implement monthly payment calculation
        return 0;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    // Override methods
    @Override
    public String toString() {
        return "Loan [amount=" + amount + ", termYears=" + termYears + ", termMonths=" + termMonths + ", annualRate="
                + annualRate + ", remainingAmount=" + remainingAmount + "]";
    }
}

