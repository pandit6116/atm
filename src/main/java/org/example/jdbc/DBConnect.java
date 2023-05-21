package org.example.jdbc;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {

    private static Connection conn;
    static Logger logger= Logger.getLogger(DBConnect.class);


    public static Connection getConn()
    {

        try {

            if (conn==null)
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","Root");
                logger.info("DB connection successful");
            }

        } catch (Exception e) {
            logger.error("Could not connect to db" ,e);
        }

        return conn;
    }



}

