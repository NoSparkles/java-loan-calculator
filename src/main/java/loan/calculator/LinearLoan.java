package loan.calculator;

public class LinearLoan extends Loan {

    public LinearLoan(double amount, int termYears, int termMonths, double annualRate, int fromDelay, int delay) {
        super(amount, termYears, termMonths, annualRate, fromDelay, delay);
    }

    @Override
    public PaymentSchedule[] getPaymentSchedule(int fromMonth, int toMonth) {
        int totalMonths = getTotalLoanTermMonths();
        PaymentSchedule[] schedule = new PaymentSchedule[totalMonths + 1]; // +1 for the summary row
    
        double remainingBalance = amount;
        double principalPayment = amount / (totalMonths - delay); // Spread principal over non-deferred months
        double totalInterestPayment = 0.0;
    
        // Regular payments before the deferment period
        for (int i = 0; i < fromDelay; i++) {
            double interestPayment = (annualRate / 100 / 12) * remainingBalance;
            double totalPayment = principalPayment + interestPayment;
            totalInterestPayment += interestPayment;
    
            schedule[i] = new PaymentSchedule(
                Integer.toString(i + 1),
                roundToTwoDecimals(principalPayment), // Regular principal payment
                roundToTwoDecimals(interestPayment),
                roundToTwoDecimals(totalPayment),
                roundToTwoDecimals(remainingBalance)
            );
    
            remainingBalance -= principalPayment; // Reduce balance by principal payment
        }
    
        // Interest-only payments during the deferment period
        for (int i = fromDelay; i < fromDelay + delay; i++) {
            double interestPayment = (annualRate / 100 / 12) * remainingBalance;
            totalInterestPayment += interestPayment;
    
            schedule[i] = new PaymentSchedule(
                Integer.toString(i + 1),
                0.0, // No principal payment during deferment
                roundToTwoDecimals(interestPayment),
                roundToTwoDecimals(interestPayment), // Total payment is just the interest
                roundToTwoDecimals(remainingBalance)
            );
        }
    
        // Linear payments after the deferment period
        for (int i = fromDelay + delay; i < totalMonths; i++) {
            double interestPayment = (annualRate / 100 / 12) * remainingBalance;
            totalInterestPayment += interestPayment;
            double totalPayment = principalPayment + interestPayment;
    
            schedule[i] = new PaymentSchedule(
                Integer.toString(i + 1),
                roundToTwoDecimals(principalPayment), // Regular principal payment resumes
                roundToTwoDecimals(interestPayment),
                roundToTwoDecimals(totalPayment),
                roundToTwoDecimals(remainingBalance)
            );
    
            remainingBalance -= principalPayment; // Reduce balance by principal payment
        }
    
        // Add the summary row
        schedule[totalMonths] = new PaymentSchedule(
            "Iš viso:",
            roundToTwoDecimals(amount), // Total principal
            roundToTwoDecimals(totalInterestPayment), // Total interest
            roundToTwoDecimals(amount + totalInterestPayment), // Total cost
            0.00
        );
    
        return this.getFilteredPaymentSchedule(schedule, fromMonth, toMonth);
    }
}
