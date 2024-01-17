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
    static class Lager {
        static{
            DatabaseHelper.execute("CREATE TABLE IF NOT EXISTS lager (lagerId INT AUTO_INCREMENT, artikelId int NOT NULL, amount int NOT NULL, PRIMARY KEY (lagerId), FOREIGN KEY(artikelId) REFERENCES artikles(artikelId));");
        }
        public static void addLager(int artikelId, int amount){
            DatabaseHelper.execute("INSERT INTO lager (artikelId, amount) VALUES (\"" + artikelId + "\", \"" + amount + "\");");
        }
        public static ResultSet showLager(){
            return DatabaseHelper.executeQuery("SELECT * FROM lager;");
        }
        public static void restock(String Artikelname, int amount){
            DatabaseHelper.execute("UPDATE lager SET amount = amount + " + amount + " WHERE artikelId = (SELECT artikelId FROM artikles WHERE name = \"" + Artikelname + "\");");
        }
        public static void updateAmount(int artikelId, int amount){
            DatabaseHelper.execute("UPDATE lager SET amount = " + amount + " WHERE artikelId = " + artikelId + ";");
        }
        public static int getLagerbestand(int artikelId){
            try (ResultSet rs = DatabaseHelper.executeQuery("SELECT amount FROM lager WHERE artikelId = " + artikelId + ";")){
                if (rs.next()) {  // Move the cursor to the first row
                    return rs.getInt("amount"); // Use column name or 1 instead of 0
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
        }
    }

    static class Bestellungen {
        static {
            DatabaseHelper.execute("CREATE TABLE IF NOT EXISTS orders (orderId INT AUTO_INCREMENT, customerId int NOT NULL, artikelId int NOT NULL, amount int NOT NULL, PRIMARY KEY (orderId), FOREIGN KEY(customerId) REFERENCES customers(customerId), FOREIGN KEY(artikelId) REFERENCES artikles(artikelId));");
        }
        public static void addBestellung(int customerId, int artikelId, int amount){
            if(DatabaseHelper.Lager.getLagerbestand(artikelId) >= amount) {
                DatabaseHelper.execute("INSERT INTO orders (customerId, artikelId, amount) VALUES (\"" + customerId + "\", \"" + artikelId + "\", \"" + amount + "\");");
                DatabaseHelper.Lager.updateAmount(artikelId, DatabaseHelper.Lager.getLagerbestand(artikelId) - amount);
            }
            else
                System.out.println("Nicht genug Artikel auf Lager! (Lagerbestand: " + DatabaseHelper.Lager.getLagerbestand(artikelId) + ")");
        }
        public static ResultSet showBestellungen(){
            return DatabaseHelper.executeQuery("SELECT \n" +
                    "    o.orderId,\n" +
                    "    c.name AS customerName,\n" +
                    "    c.email,\n" +
                    "    a.name AS artikelName,\n" +
                    "    a.price,\n" +
                    "    o.amount\n" +
                    "FROM \n" +
                    "    orders o\n" +
                    "    JOIN customers c ON o.customerId = c.customerId\n" +
                    "    JOIN artikles a ON o.artikelId = a.artikelId;\n");
        }
        public static void deleteBestellung(int id){
            DatabaseHelper.execute("DELETE FROM orders WHERE orderId = " + id + ";");
        }
        public static void updateBestellung(int id, int customerId, int artikelId, int amount){
            DatabaseHelper.execute("UPDATE orders SET customerId = \"" + customerId + "\", artikelId = \"" + artikelId + "\", amount = \"" + amount + "\" WHERE orderId = " + id + ";");
        }
    }
    static class Artikel {
        static {
            DatabaseHelper.execute("CREATE TABLE IF NOT EXISTS arikles (id INT AUTO_INCREMENT, name CHAR(60), price decimal(10,2), PRIMARY KEY (id));");
        }

        public static void addArtikel(String name, double price, int amount) {
            DatabaseHelper.execute("INSERT INTO artikles (name, price) VALUES (\"" + name + "\", \"" + (double) (Math.round(price * 100) / 100) + "\");");
            try {
                DatabaseHelper.Lager.addLager(DatabaseHelper.executeQuery("SELECT artikelId FROM artikles WHERE name = \"" + name + "\";").getInt(0), amount);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        public static ResultSet showArtikles() {
            return DatabaseHelper.executeQuery("SELECT * FROM artikles;");
        }

        public static void deleteArtikel(int id) {
            DatabaseHelper.execute("DELETE FROM artikles WHERE artikelId = " + id + ";");
        }

        public static void updateArtikel(int id, String name, double price) {
            DatabaseHelper.execute("UPDATE artikles SET name = \"" + name + "\", price = \"" + (double) (Math.round(price * 100) / 100) + "\" WHERE artikelId = " + id + ";");
        }
    }

    static class Kunden {
        static{
            DatabaseHelper.execute("CREATE TABLE IF NOT EXISTS customers (id INT AUTO_INCREMENT, name CHAR(60), email CHAR(60), PRIMARY KEY (id));");
        }
        public static void addKunde(String name, String email){
            DatabaseHelper.execute("INSERT INTO customers (name, email) VALUES (\"" + name + "\", \"" + email + "\");");
        }
        public static ResultSet showKunden(){
            return DatabaseHelper.executeQuery("SELECT * FROM customers;");
        }
        public static void deleteKunde(int id){
            DatabaseHelper.execute("DELETE FROM customers WHERE customerId = " + id + ";");
        }
        public static void updateKunde(int id, String name, String email){
            DatabaseHelper.execute("UPDATE customers SET name = \"" + name + "\", email = \"" + email + "\" WHERE customerId = " + id + ";");
        }
    }


}
