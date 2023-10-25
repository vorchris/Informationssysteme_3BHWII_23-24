package com.Christian;

import java.sql.*;
import java.util.Random;
import java.util.StringJoiner;

public class Main {
    private static final String URL ="jdbc:sqlite:C:\\Users\\chris\\Desktop\\sqlite-tools-win32-x86-3430100\\sqlite-tools-win32-x86-3430100\\vorhofer.db";
    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public boolean executeQuery(String q) {
        boolean returnValue = false;
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            returnValue = stmt.execute(q);
        }
         catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return returnValue;
    }

    public ResultSet getValuesFromDb(String q){
        ResultSet rs = null;
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    public void makeAndFill(Main m, String tableName){
        Random rnd = new Random();
        m.executeQuery("DROP TABLE IF EXISTS " + tableName);
        m.executeQuery("CREATE TABLE " + tableName + " (id INTEGER PRIMARY KEY AUTOINCREMENT, value Integer, value2 Integer);");
        for (int i = 0; i < 20; i++) {
            int rndInt = rnd.nextInt(1, 11);
            m.executeQuery("INSERT INTO " + tableName + " (value, value2) VALUES (" + rndInt + ", " + rndInt%2 + ");");
        }
    }
    public void getOutputs(String tablename) throws SQLException {
        ResultSet gerandeZahlen = getValuesFromDb("SELECT count(value) FROM " + tablename + " WHERE value%2 == 0;");
        ResultSet zahlen = getValuesFromDb("SELECT count(*) FROM " + tablename + ";");

        System.out.println("Gerade Zahlen: " + gerandeZahlen.getString(1) + " Ungerade Zahlen: " + (zahlen.getInt(1) - gerandeZahlen.getInt(1)));
    }

    public static void main(String[] args) throws SQLException{
        Main main = new Main();
        main.makeAndFill(main, "randomRnd");
        main.getOutputs("randomRnd");
        main.connect();
    }
}
