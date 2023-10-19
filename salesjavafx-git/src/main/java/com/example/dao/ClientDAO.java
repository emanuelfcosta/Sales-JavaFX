package com.example.dao;

import com.example.connectionjdbc.SingleConnection;
import com.example.model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    private Connection connection;

    public ClientDAO(){
        connection = SingleConnection.getConnection();
    }

    public void save(Client client){
        try {
            String sql = "insert into client(name,address,email) values(?,?,?) ";
            PreparedStatement insert = connection.prepareStatement(sql);
            insert.setString(1,client.getName());
            insert.setString(2,client.getAddress());
            insert.setString(3,client.getEmail());
            insert.execute();
            connection.commit();


        }catch (Exception e){
            try{
                connection.rollback();
            } catch (SQLException e1){

                System.out.println("error: " + e1.getMessage());
            }
            System.out.println("error: " + e.getMessage());

        }

    }

    public List<Client> listAll() {
        List<Client> list = new ArrayList<Client>();

        String sql = "select * from client";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet theResult = statement.executeQuery();

            while (theResult.next()) {
                Client client = new Client();
                client.setId(theResult.getInt("id"));
                client.setName(theResult.getString("name"));
                client.setAddress(theResult.getString("address"));
                client.setEmail(theResult.getString("email"));

                list.add(client);
            }


        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }

        return list;
    }

    public Client findById(int id) {
        Client theClient = new Client();

        String sql = "select * from client where id = " + id ;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet theResult = statement.executeQuery();

            while (theResult.next()) {

                theClient.setId(theResult.getInt("id"));
                theClient.setName(theResult.getString("name"));
                theClient.setAddress (theResult.getString("address"));
                theClient.setEmail (theResult.getString("email"));

            }

        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }

        return theClient;
    }

    public void update(Client theClient){
        try {

            String sql = "update client set name= ?, address=?, email=? where id = " + theClient.getId();

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, theClient.getName());
            statement.setString(2, theClient.getAddress());
            statement.setString(3, theClient.getEmail());

            statement.execute();
            connection.commit();
        }catch (Exception e){
            try {
                connection.rollback();
            } catch(SQLException e1){
                System.out.println("error: " + e.getMessage());
            }
            System.out.println("error: " +  e.getMessage());
        }

    }

    public void delete(int id){
        try{

            String sql = "delete from client where id = " + id;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
            connection.commit();
        }catch (Exception e){
            try{
                connection.rollback();
            }catch (SQLException e1){
                System.out.println("Error: " + e1.getMessage());
            }

            System.out.println("Error: " + e.getMessage());
        }

    }











}
