package com.example.BackEnd;

import com.example.DBHandler.*;

public class DBFactory {

    private static DBFactory instance = null;
    private DBHandler dbHandler;

    private DBFactory()
    {
        dbHandler = null;
    }

    public static synchronized DBFactory getInstance()
    {
        if(instance == null)
        {
            instance = new DBFactory();
        }

        return instance;
    }

    public DBHandler createHandler(String type)
    {
        if(type.equals("SQL") && dbHandler == null)
        {
            dbHandler = new SQL();
        }

        return dbHandler;
    }
    
}