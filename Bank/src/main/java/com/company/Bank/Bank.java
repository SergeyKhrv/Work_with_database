package com.company.Bank;

import java.sql.SQLException;
import java.util.Scanner;

public class Bank {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        JDBCDriver jdbcDriver = new JDBCDriver();
        System.out.println("Enter your name and surname");
        String name = scanner.nextLine();
        System.out.println("Enter your address");
        String address = scanner.nextLine();
        System.out.println("Enter currency");
        String currency = scanner.next();
        System.out.println("Do you want to replenish the balance (type '+') or withdraw cash (type '-')");
        String changeBanalce = scanner.next();
        System.out.println("Enter amount");
        Double amount = scanner.nextDouble();

        BankInfo bankInfo = new BankInfo(name, address, currency);
        Person person = new Person(bankInfo.getName(), bankInfo.getAddress());
        UserAccounts userAccounts =
                new UserAccounts(bankInfo.getName(), bankInfo.getAddress(), bankInfo.getCurrency());
        Transactions transactions =
                new Transactions(bankInfo.getName(), bankInfo.getAddress(), bankInfo.getCurrency(),
                        amount, changeBanalce);

        if (jdbcDriver.isJDBCDriverExist()) {
            if (!person.isUserExist()) {
                person.setUserInfo();
            }
            if (!userAccounts.isCurrencyExist()) {
                userAccounts.setUserCurrencyAndBalance();
            }
            transactions.checkAmountTransactionSize();
            transactions.checkTypeOfTransaction(changeBanalce);
            transactions.updateBalance();
            transactions.setTransactionAmount();
        }
    }
}

