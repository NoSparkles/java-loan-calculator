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

    public abstract PaymentSchedule[] getPaymentSchedule(int fromMonth, int toMonth);

    public int getTotalLoanTermMonths() {
        return this.termYears * 12 + this.termMonths;
    }

    protected double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    protected PaymentSchedule[] getFilteredPaymentSchedule(PaymentSchedule[] schedule, int fromMonth, int toMonth) {
        int totalMonths = toMonth - fromMonth + 1;
        PaymentSchedule[] filteredSchedule = new PaymentSchedule[totalMonths + 1];

        for (int i = 0; i < totalMonths; ++i) {
            filteredSchedule[i] = schedule[fromMonth + i];
        }
        filteredSchedule[totalMonths] = schedule[schedule.length - 1];
        return filteredSchedule;
    }
}