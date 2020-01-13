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
        assertEquals(customerAccount.getBalance(), new Double(0));
    }

    /**
     * Adds money to the account and checks that the new balance is as expected.
     */
    @Test
    public void testAddPositiveAmount() {
        Double randomDeposit = randomDouble();
        customerAccount.add(randomDeposit);
        assertEquals(randomDeposit, customerAccount.getBalance());
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
