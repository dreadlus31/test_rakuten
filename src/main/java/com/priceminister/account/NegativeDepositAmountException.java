package com.priceminister.account;

public class NegativeDepositAmountException extends Exception {

    private Double balance;

    public String toString() {
        return "Deposit Amount is Negative";
    }
}
