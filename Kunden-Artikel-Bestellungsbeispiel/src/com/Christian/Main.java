package com.Christian;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        Scanner scr = new Scanner(System.in);

        while (true) {
            displayMenu();
            String eingabe = scr.nextLine();
            String[] splitted = eingabe.split(" ");

            try {
                switch (splitted[0]) {
                    case "ak": // Add Kunde
                        DatabaseHelper.Kunden.addKunde(splitted[1], splitted[2]);
                        break;
                    case "uk": // Update Kunde
                        DatabaseHelper.Kunden.updateKunde(Integer.parseInt(splitted[1]), splitted[2], splitted[3]);
                        break;
                    case "sk": // Show Kunden
                        printResultSet(DatabaseHelper.Kunden.showKunden());
                        break;
                    case "dk": // Delete Kunde
                        DatabaseHelper.Kunden.deleteKunde(Integer.parseInt(splitted[1]));
                        break;
                    case "ua": // Update Artikel
                        DatabaseHelper.Artikel.updateArtikel(Integer.parseInt(splitted[1]), splitted[2], Double.parseDouble(splitted[3]));
                        break;
                    case "aa": // Add Artikel
                        DatabaseHelper.Artikel.addArtikel(splitted[1], Double.parseDouble(splitted[2]), Integer.parseInt(splitted[3]));
                        break;
                    case "sa": // Show Artikel
                        printResultSet(DatabaseHelper.Artikel.showArtikles());
                        break;
                    case "da": // Delete Artikel
                        DatabaseHelper.Artikel.deleteArtikel(Integer.parseInt(splitted[1]));
                        break;
                    case "ab": // Add Bestellung
                        DatabaseHelper.Bestellungen.addBestellung(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]), Integer.parseInt(splitted[3]));
                        break;
                    case "sb": // Show Bestellungen
                        printResultSet(DatabaseHelper.Bestellungen.showBestellungen());
                        break;
                    case "al": // Add Lager
                        DatabaseHelper.Lager.addLager(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]));
                        break;
                    case "e": // Exit
                        System.out.println("Exiting program.");
                        return;
                    default:
                        System.out.println("Invalid command. Please try again.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error occurred: " + e.getMessage());
            }
        }
    }

    private static void displayMenu() {
        System.out.println("####################################");
        System.out.println("Commands:");
        System.out.println("Add Customer (ak Name Email)");
        System.out.println("Show Customers (sk)");
        System.out.println("Update Customer (uk id Name Email)");
        System.out.println("Delete Customer (dk id)");
        System.out.println("Update Artikel (ua id Name Preis)");
        System.out.println("Add Artikel (aa Name Preis Menge)");
        System.out.println("Show Artikel (sa)");
        System.out.println("Delete Artikel (da id)");
        System.out.println("Add Bestellung (ab CustomerID ArtikelID Anzahl)");
        System.out.println("Show Bestellungen (sb)");
        System.out.println("Add to Lager (al ArtikelID Menge)");
        System.out.println("Exit (e)");
        System.out.println("####################################");
    }

    private static void printResultSet(ResultSet rs) throws SQLException {
        while (rs != null && rs.next()) {
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                System.out.print(rs.getMetaData().getColumnName(i) + ": " + rs.getString(i) + " ");
            }
            System.out.println();
        }
    }
}
