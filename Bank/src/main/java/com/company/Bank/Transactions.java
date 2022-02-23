package com.company.Bank;

import java.sql.*;

public class Transactions extends BankInfo {
    private static final String DATABASE_URL =
            "jdbc:sqlite:f:\\Study\\JAVA\\Java learning\\IdeaProjects\\Bank\\Bank.db";
    private double ccy_amount;
    private String change_Balance;
    private static final int max_transaction = 100000000;
    private static final int max_balance = 2000000000;

    public Transactions(String name, String address, String currency, double ccy_amount, String change_Balance) {
        super(name, address, currency);
        this.ccy_amount = ccy_amount;
        this.change_Balance = change_Balance;
    }

    public void checkAmountTransactionSize() {
        if (ccy_amount >= max_transaction) {
            System.out.println("Transaction size exceeds maximum value of " + max_transaction);
            System.exit(0);
        }
    }

    public void setTransactionAmount() throws SQLException {

        Connection connection = DriverManager.getConnection(DATABASE_URL);

        //Find accountId
        String find_accountId = "SELECT accountId FROM Accounts " +
                "WHERE (SELECT userId from Users where name = ?) AND currency = ?";
        PreparedStatement preparedStatement_accountId = connection.prepareStatement(find_accountId);
        preparedStatement_accountId.setString(1, getName());
        preparedStatement_accountId.setString(2, getCurrency());
        ResultSet result_accountId = preparedStatement_accountId.executeQuery();

        //Add transaction id, accountId, amount in Transactions table of database
        String setCcyAmount = "INSERT INTO Transactions (accountId, amount) VALUES (?, ?)";
        PreparedStatement preparedStatement_setCcyAmount = connection.prepareStatement(setCcyAmount);
        preparedStatement_setCcyAmount.setInt(1, result_accountId.getInt("accountId"));
        preparedStatement_setCcyAmount.setDouble(2, ccy_amount);
        int resultSet = preparedStatement_setCcyAmount.executeUpdate();

        connection.close();
        result_accountId.close();
    }

    public double checkTypeOfTransaction(String change_Balance) {
        if (change_Balance.equals("-")) {
            return ccy_amount = ccy_amount * (-1);
        } else {
            return ccy_amount;
        }
    }

    public int getExistingBalance() throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL);

        //find existing balance
        String current_balance = "SELECT balance FROM Accounts " +
                "WHERE (SELECT userId from Users where name = ?) AND currency = ?";
        PreparedStatement preparedStatement_current_balance = connection.prepareStatement(current_balance);
        preparedStatement_current_balance.setString(1, getName());
        preparedStatement_current_balance.setString(2, getCurrency());
        ResultSet result_current_balance = preparedStatement_current_balance.executeQuery();
        int existingBalance = result_current_balance.getInt(1);
        connection.close();
        result_current_balance.close();
        return existingBalance;
    }

    public void updateBalance() throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL);
        if (getExistingBalance() + checkTypeOfTransaction(change_Balance) >= max_balance) {
            System.out.println("Balance exceeds maximum value of " + max_balance);
            System.exit(0);
        } else {
            String new_balance = "UPDATE Accounts SET balance = ? " +
                    "WHERE (SELECT userId from Users where name = ?) AND currency = ?";
            PreparedStatement preparedStatement_new_balance = connection.prepareStatement(new_balance);
            preparedStatement_new_balance.setInt(1, (int) (getExistingBalance()
                    + checkTypeOfTransaction(change_Balance)));
            preparedStatement_new_balance.setString(2, getName());
            preparedStatement_new_balance.setString(3, getCurrency());
            int result_new_balance = preparedStatement_new_balance.executeUpdate();
        }
    }
}
