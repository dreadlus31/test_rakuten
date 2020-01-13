package com.priceminister.account.implementation;

import com.priceminister.account.*;

public class CustomerAccount implements Account {

    /**
     * @param balance stored as cents of Euros. Expressed as Euros.
     */
    private int balance = 0;

    public void add(Double addedAmount) throws NegativeDepositAmountException {
        if (addedAmount < 0) {
            throw new NegativeDepositAmountException();
        }
        this.balance += toCents(addedAmount);
    }

    public Double getBalance() {
        return toEuros(this.balance);
    }

    public Double withdrawAndReportBalance(Double withdrawnAmount, AccountRule rule) throws IllegalBalanceException {
        int remainingBalance = balance - toCents(withdrawnAmount);
        Double results = toEuros(remainingBalance);
        if (rule.withdrawPermitted(results)) {
            balance = remainingBalance;
            return results;
        }
        throw new IllegalBalanceException(results);
    }

    private static int toCents(Double euros) {
        return new Double(euros * 100).intValue();
    }

    private static Double toEuros(int cents) {
        return (new Double(cents)) / 100;
    }

}
