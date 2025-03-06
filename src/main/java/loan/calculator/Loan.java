package loan.calculator;

public abstract class Loan {
    protected double amount;
    protected int termYears;
    protected int termMonths;
    protected double annualRate;

    public Loan(double amount, int termYears, int termMonths, double annualRate) {
        this.amount = amount;
        this.termYears = termYears;
        this.termMonths = termMonths;
        this.annualRate = annualRate;
    }

    public abstract PaymentSchedule[] getPaymentSchedule();

    public int getTotalLoanTermMonths() {
        return this.termYears * 12 + this.termMonths;
    }
}