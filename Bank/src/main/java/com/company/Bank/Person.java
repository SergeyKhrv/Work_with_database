package com.company.Bank;

import java.sql.*;

public class Person {
    private static final String DATABASE_URL =
            "jdbc:sqlite:f:\\Study\\JAVA\\Java learning\\IdeaProjects\\Bank\\Bank.db";
    private String name;
    private String address;

    public Person(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public boolean isUserExist() throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL);
        String getUsers = "SELECT name from Users";
        Statement statement = connection.createStatement();
        ResultSet resultSet_users = statement.executeQuery(getUsers);
        if (resultSet_users.equals(null)) {
            return false;
        } else {
            return true;
        }
    }

    public void setUserInfo() throws SQLException {

        //Add new user in Users table of database
        Connection connection = DriverManager.getConnection(DATABASE_URL);
        String setNewUser = "INSERT INTO Users (name, address) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(setNewUser);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, address);
        int resultSet = preparedStatement.executeUpdate();
        connection.close();
    }
}
