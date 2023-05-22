package com.example.DBHandler;

import com.example.BackEnd.DBHandler;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL extends DBHandler{

    public void saveAppointment(String date, String time, String problem, int patId)
    {
        System.out.println(date+" "+time+" "+problem+" "+patId);
    }

    // public static void main(String[] args) throws Exception {
    //     String connectionUrl = "jdbc:sqlserver://BOREDAF\\SQLEXPRESS;" +
    //                 "databaseName=DB_Lab13;" +
    //                 "IntegratedSecurity=true" + ";encrypt=true;trustServerCertificate=true";
    //         try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement()) {
    //             String SQL = "SELECT * FROM tb1Employee";
    //             ResultSet rs = stmt.executeQuery(SQL);
    //             //print all columns and rows in resultset


    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //         }
    //   }
}
