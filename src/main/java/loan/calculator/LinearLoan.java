package loan.calculator;

public class LinearLoan extends Loan {

    public LinearLoan(double amount, int termYears, int termMonths, double annualRate, int delay) {
        super(amount, termYears, termMonths, annualRate, delay);
    }

    @Override
    public PaymentSchedule[] getPaymentSchedule() {
        int totalMonths = getTotalLoanTermMonths();
        PaymentSchedule[] schedule = new PaymentSchedule[totalMonths + 1]; // +1 for the summary row

        double remainingBalance = amount;
        double principalPayment = amount / (totalMonths - delay); // Do not round here
        double totalInterestPayment = 0.0;

        // Interest-only payments during the delay period
        for (int i = 0; i < delay; i++) {
            double interestPayment = (annualRate / 100 / 12) * remainingBalance;
            totalInterestPayment += interestPayment;
            schedule[i] = new PaymentSchedule(
                Integer.toString(i + 1),
                0.0,
                roundToTwoDecimals(interestPayment), // Round only for display
                roundToTwoDecimals(interestPayment),
                roundToTwoDecimals(remainingBalance)
            );
        }

        // Linear payments after the delay period
        for (int i = delay; i < totalMonths; i++) {
            double interestPayment = (annualRate / 100 / 12) * remainingBalance;
            totalInterestPayment += interestPayment;
            double totalPayment = principalPayment + interestPayment;

            schedule[i] = new PaymentSchedule(
                Integer.toString(i + 1),
                roundToTwoDecimals(principalPayment), // Round only for display
                roundToTwoDecimals(interestPayment),
                roundToTwoDecimals(totalPayment),
                roundToTwoDecimals(remainingBalance)
            );

            remainingBalance -= principalPayment; // Use full precision here
        }

        // Add the summary row
        schedule[totalMonths] = new PaymentSchedule(
            "Iš viso:",
            roundToTwoDecimals(amount),
            roundToTwoDecimals(totalInterestPayment),
            roundToTwoDecimals(amount + totalInterestPayment),
            0.00
        );

        return schedule;
    }
}
