package com.Christian;

import java.sql.*;

public class DatabaseHelper{
    private final String URL = "jdbc:mysql://localhost/bestellsystem?useTimezone=true&serverTimezone=UTC";
    private static Connection connection;
    private String username = "root";
    private String password = "root";

    public DatabaseHelper(){
        try {
            connection = DriverManager.getConnection(URL, username, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static ResultSet executeQuery(String query){
        try{
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean execute(String query){
        try{
            Statement statement = connection.createStatement();
            return statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void printResultSet(ResultSet rs) {
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection(){
        return connection;
    }
    public static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
