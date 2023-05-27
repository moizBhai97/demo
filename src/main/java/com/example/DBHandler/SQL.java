package com.example.DBHandler;

import com.example.BackEnd.DBHandler;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

public class SQL extends DBHandler{

    public void saveAppointment(String info, int patId)
    {
        try
        {
            JSONObject obj = new JSONObject(info);
            System.out.println(patId + " " + obj.getString("date") + " " + obj.getString("time") + " " + obj.getString("problem") + " " + obj.getString("docId") + " " + "Booked");
        }
        catch(Exception e)
        {
            System.out.println(e + " " + getClass().getName());
        }
    }

    public void updateAppointment(int appId, String Reason, int value)
    {
        try
        {
            if(value == 1)
            {
                System.out.println(appId+ " " + Reason + " " + "Canceled");
            }
        }
        catch(Exception e)
        {
            System.out.println(e + " " + getClass().getName());
        }
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
