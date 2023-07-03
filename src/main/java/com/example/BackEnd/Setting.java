package com.example.BackEnd;

public class Setting {

    public void setServer(String server) {
        try
        {
            DBFactory.getInstance().createHandler("SQL").setServer(server);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public Boolean createDatabaseAndTables() 
    {
        
        return DBFactory.getInstance().createHandler("SQL").createDatabaseAndTables("SDA_DB/SDA.sql");
    }
    
    public Boolean testConnection() {
        return DBFactory.getInstance().createHandler("SQL").testConnection();
    }
}
