package com.Christian;

import java.sql.*;
import java.util.Random;

public class Main {
    private static final String URL = "jdbc:mysql://localhost/vorhofer?useTimezone=true&serverTimezone=UTC";
    private static String username = "root";
    private static String password = "root";
    private static Connection conn = null;
    private void connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, username, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
       this.conn = conn;
    }

    public boolean executeQuery(String q) {
        boolean returnValue = false;
        try (Statement stmt = conn.createStatement()) {
            returnValue = stmt.execute(q);
        }
         catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return returnValue;
    }

    public static String getValuesFromDb(String q) {
        try (Connection conn = DriverManager.getConnection(URL, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(q)) {
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void makeAndFill(Main m, String tableName){
        Random rnd = new Random();
        m.executeQuery("DROP TABLE IF EXISTS " + tableName);
        m.executeQuery("CREATE TABLE " + tableName + " (id INTEGER PRIMARY KEY AUTO_INCREMENT, value Integer, value2 Integer);");
        for (int i = 0; i < 20; i++) {
            int rndInt = rnd.nextInt(1, 11);
            m.executeQuery("INSERT INTO " + tableName + " (value, value2) VALUES (" + rndInt + ", " + rndInt%2 + ");");
        }
    }
    public static String getEven(String tablename) {
        String evenCount = getValuesFromDb("SELECT COUNT(value) FROM " + tablename + " WHERE value % 2 = 0;");
        if (evenCount != null) {
            return evenCount;
        }
        return "0";
    }
    public static String getOdd(String tablename) {
        String oddCount = getValuesFromDb("SELECT COUNT(value) FROM " + tablename + " WHERE value % 2 = 1;");
        if (oddCount != null) {
            return oddCount;
        }
        return "0";
    }


    public static void main(String[] args) throws SQLException{
        Main main = new Main();
        main.connect();
        if(args.length == 0 | args.length > 2){
            System.err.println("Illegal Argument");
            System.exit(1);
        }
        else{
            String value = "";
            try{
                if(args[1].equals("delete")){
                    main.makeAndFill(main, "randomRnd");
                    if(args[0].equals("odd")){
                        System.out.println("Odd Numbers " + getOdd("randomRnd"));
                    }
                    else if(args[0].equals("even")){
                        System.out.println("Even Numbers " + getEven("randomRnd"));
                    }
                }
                else{
                    System.out.println("Illegal Argument");
                }

            }catch (IndexOutOfBoundsException e){
                if(args[0].equals("odd")){
                    System.out.println("Odd Numbers " + getOdd("randomRnd"));
                }
                else if(args[0].equals("even")){
                    System.out.println("Even Numbers " + getEven("randomRnd"));
                }
            }
        }
        main.connect();
    }
}
