package loan.calculator;

public abstract class Loan {
    protected double amount;
    protected int termYears;
    protected int termMonths;
    protected double annualRate;
    protected int delay;

    public Loan(double amount, int termYears, int termMonths, double annualRate, int delay) {
        this.amount = amount;
        this.termYears = termYears;
        this.termMonths = termMonths;
        this.annualRate = annualRate;
        this.delay = delay;
    }

    public abstract PaymentSchedule[] getPaymentSchedule();

    public int getTotalLoanTermMonths() {
        return this.termYears * 12 + this.termMonths;
    }

    protected double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}