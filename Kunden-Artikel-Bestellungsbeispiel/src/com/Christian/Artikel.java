package com.Christian;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Artikel {
    public Artikel() {
        DatabaseHelper.execute("CREATE TABLE IF NOT EXISTS arikles (id INT AUTO_INCREMENT, name CHAR(60), price decimal(10,2), PRIMARY KEY (id));");
    }
    public void addArtikel(String name, double price){
        DatabaseHelper.execute("INSERT INTO artikles (name, price) VALUES (\"" + name + "\", \"" + (double) (Math.round(price * 100) / 100) + "\");");
    }
    public ResultSet showArtikles(){
        return DatabaseHelper.executeQuery("SELECT * FROM artikles;");
    }



}
