package com.priceminister.account.implementation;

import com.priceminister.account.*;

public class CustomerAccount implements Account {

    /**
     * @param balance stored as cents of Euros. Expressed as Euros.
     */
    private long balance = 0;

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
        if (rule.withdrawPermitted(toEuros(balance), withdrawnAmount)) {
            balance -= toCents(withdrawnAmount);
            return this.getBalance();
        }
        throw new IllegalBalanceException(toEuros(balance) - withdrawnAmount);
    }

    private static long toCents(Double euros) {
        return new Double(euros * 100).intValue();
    }

    private static Double toEuros(long cents) {
        return (new Double(cents)) / 100;
    }

}
