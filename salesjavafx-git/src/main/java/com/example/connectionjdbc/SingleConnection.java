package com.example.connectionjdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {
    private static String url = "jdbc:mysql://localhost:3306/sales_javafx_git?autoReconnect=true";
    private static String password = "";
    private static String user = "root";

    private static Connection connection = null;

    static{
        myConect();
    }

    public SingleConnection() {
        myConect();
    }

    private static void myConect(){

        try {
            if(connection == null){
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, password);
                connection.setAutoCommit(false);
                System.out.println("successfully connected");
            }

        }catch(Exception e){
          //  e.printStackTrace();
            System.out.println("error: " + e.getMessage());

        }

    }

    public static Connection getConnection(){

        return connection;

    }

}
