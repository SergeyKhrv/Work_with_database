package com.company.Bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

public class UserAccounts extends BankInfo {
    private static final String DATABASE_URL =
            "jdbc:sqlite:f:\\Study\\JAVA\\Java learning\\IdeaProjects\\Bank\\Bank.db";

    public UserAccounts(String name, String address, String currency) {
        super(name, address, currency);
    }

    public void setUserCurrencyAndBalance() throws SQLException {

        Connection connection = DriverManager.getConnection(DATABASE_URL);

        //find userId
        String findUserId = "SELECT userId from Users where name = ?";
        PreparedStatement preparedStatement_UserId = connection.prepareStatement(findUserId);
        preparedStatement_UserId.setString(1, getName());
        ResultSet resultSet_UserId = preparedStatement_UserId.executeQuery();

        //Populate database with userId, balance, currency
        String setAccountCurrency = "INSERT INTO Accounts (userId, balance, currency) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement_accountCurrency = connection.prepareStatement(setAccountCurrency);
        preparedStatement_accountCurrency.setInt(1, resultSet_UserId.getInt("userId"));
        preparedStatement_accountCurrency.setInt(2, 0);
        preparedStatement_accountCurrency.setString(3, getCurrency());
        int resultSet = preparedStatement_accountCurrency.executeUpdate();

        connection.close();
        resultSet_UserId.close();
        preparedStatement_accountCurrency.close();
    }

    public ResultSet getCurrencyFromDataBase() throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL);
        String getCurrency = "SELECT currency from Accounts WHERE (SELECT userId from Users where name = ?)";
        PreparedStatement preparedStatement_getCurrency = connection.prepareStatement(getCurrency);
        preparedStatement_getCurrency.setString(1, getName());
        ResultSet resultSet_currency = preparedStatement_getCurrency.executeQuery();
        connection.close();
        resultSet_currency.close();
        preparedStatement_getCurrency.close();
        return resultSet_currency;
    }

    public boolean isCurrencyExist() throws SQLException {
        if (getCurrencyFromDataBase().equals(null)) {
            return false;
        } else {
            return true;
        }
    }
}
