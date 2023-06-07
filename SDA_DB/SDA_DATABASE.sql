IF EXISTS (
    SELECT name
    FROM sys.databases
    WHERE name = 'SDA'
        AND state = 0
)
BEGIN
    USE master;
    ALTER DATABASE SDA SET SINGLE_USER;
    DROP DATABASE SDA;
END

-- Create the SDA database
CREATE DATABASE SDA;

