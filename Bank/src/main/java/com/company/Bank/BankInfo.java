package com.company.Bank;

import java.sql.*;

public class BankInfo {
    private static final String DATABASE_URL =
            "jdbc:sqlite:f:\\Study\\JAVA\\Java learning\\IdeaProjects\\Bank\\Bank.db";
    private String name;
    private String address;
    private String currency;

    public BankInfo(String name, String address, String currency) {
        this.name = name;
        this.address = address;
        this.currency = currency;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCurrency() {
        return currency;
    }

}
