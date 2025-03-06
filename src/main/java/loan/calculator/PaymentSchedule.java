package loan.calculator;

public class PaymentSchedule {
    private int month;
    private double principalPayment;
    private double interestPayment;
    private double totalPayment;
    private double remainingBalance;

    public PaymentSchedule(int month, double principalPayment, double interestPayment, double totalPayment, double remainingBalance) {
        this.month = month;
        this.principalPayment = principalPayment;
        this.interestPayment = interestPayment;
        this.totalPayment = totalPayment;
        this.remainingBalance = remainingBalance;
    }

    public int getMonth() {
        return month;
    }

    public double getPrincipalPayment() {
        return principalPayment;
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

