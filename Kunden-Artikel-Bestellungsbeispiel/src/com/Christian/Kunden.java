package com.Christian;

import java.sql.ResultSet;

public class Kunden {
    public Kunden() {
        DatabaseHelper.execute("CREATE TABLE IF NOT EXISTS customers (id INT AUTO_INCREMENT, name CHAR(60), email CHAR(60), PRIMARY KEY (id));");
    }
    public void addKunde(String name, String email){
        DatabaseHelper.execute("INSERT INTO customers (name, email) VALUES (\"" + name + "\", \"" + email + "\");");
    }
    public ResultSet showKunden(){
        return DatabaseHelper.executeQuery("SELECT * FROM customers;");
    }
}
