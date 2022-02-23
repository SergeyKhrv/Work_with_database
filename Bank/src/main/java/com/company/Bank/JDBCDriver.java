package com.company.Bank;

public class JDBCDriver {
    public static final String JDBC_DRIVER = "org.sqlite.JDBC";

    public boolean isJDBCDriverExist () {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
            return false;
        }
        return true;
    }
}
