package com.Christian;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        new DatabaseHelper();
        Artikel artikel = new Artikel();
        Kunden kunden = new Kunden();
        Bestellungen bestellungen = new Bestellungen();
        Scanner scr = new Scanner(System.in);
        loop:
        while (true){
            System.out.println("####################################");
            System.out.println("add Kunde (ak Name Email), show Kunden (sk); add Artikel (aa Name Preis(1.11)), show Artikel (sa); add Bestellung (ab) (CustomerID, ArtikelID, Anzahl), show Bestellungen (sb); exit (e)");
            System.out.println("####################################");
            String eingabe = scr.nextLine();
            String[] splitted = eingabe.split(" ");
            switch (splitted[0]){
                case "ak":
                    kunden.addKunde(splitted[1], splitted[2]);
                    break;
                case "sk":
                    DatabaseHelper.printResultSet(kunden.showKunden());
                    break;
                case "aa":
                    artikel.addArtikel(splitted[1], Double.parseDouble(splitted[2]));
                    break;
                case "sa":
                    DatabaseHelper.printResultSet(artikel.showArtikles());
                    break;
                case "ab":
                    bestellungen.addBestellung(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]), Integer.parseInt(splitted[3]));
                    break;
                case "sb":
                    DatabaseHelper.printResultSet(bestellungen.showBestellungen());
                    break;
                case "e":
                    break loop;
            }
        }
        DatabaseHelper.close();
    }
}
