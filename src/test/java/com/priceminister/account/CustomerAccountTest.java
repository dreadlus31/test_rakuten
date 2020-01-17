package com.priceminister.account;

import static org.junit.Assert.*;

import org.junit.*;

import com.priceminister.account.implementation.*;

/**
 * Please create the business code, starting from the unit tests below.
 * Implement the first test, the develop the code that makes it pass. Then focus
 * on the second test, and so on.
 *
 * We want to see how you "think code", and how you organize and structure a
 * simple application.
 *
 * When you are done, please zip the whole project (incl. source-code) and send
 * it to recrutement-dev@priceminister.com
 *
 */
public class CustomerAccountTest {

    Account customerAccount;
    AccountRule rule;

    public static Double randomDouble() {
        Double result = (Math.random() * 100) * (Math.random() * 100);
        return Math.round(result * 100.0) / 100.0;
    }

    public static Double estimateCumulatedBalanceError(Double check, Double against) {
        Double estimate = Math.abs(check - against);
        return Math.round(estimate * 100.0) / 100.0;
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        customerAccount = new CustomerAccount();
        rule = new CustomerAccountRule();
    }

    /**
     * Tests that an empty account always has a balance of 0.0, not a NULL.
     */
    @Test
    public void testAccountWithoutMoneyHasZeroBalance() {
        assertEquals(new Double(0), customerAccount.getBalance());
    }

    /**
     * Adds money to the account and checks that the new balance is as expected.
     */
    @Test
    public void testAddPositiveAmount() {
        Double randomDeposit = Math.abs(randomDouble());
        try {
            customerAccount.add(randomDeposit);
            assertEquals(randomDeposit, customerAccount.getBalance());
        } catch (NegativeDepositAmountException e) {
            fail("Deposit, somehow, was negative : " + randomDeposit);
        }
    }

    @Test
    public void testAddNegativeAmount() {
        Double randomDeposit = randomDouble() * -1;
        try {
            customerAccount.add(randomDeposit);
            fail("Deposit, somehow, was positive : " + randomDeposit);
        } catch (NegativeDepositAmountException e) {
            assertEquals("Deposit Amount is Negative", e.toString());
        }
    }

    public void testBalanceMatchesWithdrawnResult() {
        try {
            customerAccount.add(500.0);
            Double withdrawnResult = customerAccount.withdrawAndReportBalance(250.0, rule);
            assertEquals(customerAccount.getBalance(), withdrawnResult);
        } catch (NegativeDepositAmountException e) {
            fail("Somehow, added value was negative");
        } catch (IllegalBalanceException e) {
            fail("Somehow, withdrawn amount was too high");
        }
    }

    @Test
    public void testChainDepositsAndWithdrawalAndCheckBalanceMatchWithTotal() {
        Double total = 0.0, balance, randomAmount, estimate = 0.0;
        int numberOfChainedOperation = 100000;
        try {
            balance = customerAccount.getBalance();
            for (int i = 0; i < numberOfChainedOperation; i++) {
                randomAmount = Math.abs(randomDouble());

                if (Math.random() > 0.5) {
                    total += randomAmount;
                    customerAccount.add(randomAmount);
                    balance = customerAccount.getBalance();
                    estimate = estimateCumulatedBalanceError(total, balance);

                } else {
                    if (total * 0.2 <= randomAmount) {
                        i--;
                        continue;
                    }
                    total -= randomAmount;
                    balance = customerAccount.withdrawAndReportBalance(randomAmount, rule);
                    estimate = estimateCumulatedBalanceError(total, balance);
                }

            }
            estimate /= numberOfChainedOperation;
            assertTrue(estimate <= 0.01);

        } catch (NegativeDepositAmountException e) {
            fail("Negative deposit encountered");
        } catch (IllegalBalanceException e) {
            fail("Withdrawn Amount was too big");
        }
    }

    /**
     * Tests that an illegal withdrawal throws the expected exception. Use the logic
     * contained in CustomerAccountRule; feel free to refactor the existing code.
     */
    @Test
    public void testWithdrawAndReportBalanceIllegalBalance() {
        Double withdrawnAmount = customerAccount.getBalance();
        withdrawnAmount += randomDouble();
        try {
            customerAccount.withdrawAndReportBalance(withdrawnAmount, rule);
        } catch (IllegalBalanceException e) {
            String message = e.toString();
            assertEquals("Illegal account balance: -" + withdrawnAmount, message);
        }
    }

    // Also implement missing unit tests for the above functionalities.

}
