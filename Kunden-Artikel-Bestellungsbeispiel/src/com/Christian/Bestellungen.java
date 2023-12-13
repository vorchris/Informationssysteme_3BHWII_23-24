package com.Christian;

import java.sql.ResultSet;

public class Bestellungen {
    public Bestellungen(){
        DatabaseHelper.execute("CREATE TABLE IF NOT EXISTS orders (orderId INT AUTO_INCREMENT, customerId int NOT NULL, artikelId int NOT NULL, amount int NOT NULL, PRIMARY KEY (orderId), FOREIGN KEY(customerId) REFERENCES customers(customerId), FOREIGN KEY(artikelId) REFERENCES artikles(artikelId));");
    }
    public void addBestellung(int customerId, int artikelId, int amount){
        DatabaseHelper.execute("INSERT INTO orders (customerId, artikelId, amount) VALUES (\"" + customerId + "\", \"" + artikelId + "\", \"" + amount + "\");");
    }
    public ResultSet showBestellungen(){
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
}
