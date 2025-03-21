package loan.calculator.loans;

public class PaymentSchedule {
    private String month;
    private double principalPayment;
    private double interestPayment;
    private double totalPayment;
    private double remainingBalance;

    public PaymentSchedule(String month, double principalPayment, double interestPayment, double totalPayment, double remainingBalance) {
        this.month = month;
        this.principalPayment = principalPayment;
        this.interestPayment = interestPayment;
        this.totalPayment = totalPayment;
        this.remainingBalance = remainingBalance;
    }

    public String getMonth() {
        return month;
    }

    public double getInterestPayment() {
        return interestPayment;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

    @Override
    public String toString() {
        return String.format(
            "Month %d: Principal=%.2f, Interest=%.2f, Total=%.2f, Remaining=%.2f",
            month, principalPayment, interestPayment, totalPayment, remainingBalance
        );
    }
}

