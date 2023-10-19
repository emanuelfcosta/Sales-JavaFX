package com.example.dao;

import com.example.connectionjdbc.SingleConnection;
import com.example.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private Connection connection;

    public ProductDAO(){
        connection = SingleConnection.getConnection();
    }

    public void save(Product product){
        try {
            String sql = "insert into product(name,price, quantity) values(?,?,?) ";
            PreparedStatement insert = connection.prepareStatement(sql);

            insert.setString(1,product.getName());
            insert.setDouble (2,product.getPrice());
            insert.setInt (3,product.getQuantity());
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

    public List<Product> listAll() {
        List<Product> list = new ArrayList<Product>();

        String sql = "select * from product";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet the_result = statement.executeQuery();

            while (the_result.next()) {
                Product prod = new Product();

                prod.setId(the_result.getInt("id"));
                prod.setName(the_result.getString("name"));
                prod.setPrice(the_result.getDouble("price"));
                prod.setQuantity(the_result.getInt("quantity"));

                list.add(prod);
            }


        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }

        return list;
    }

    public Product findById(int id) {
        Product prod = new Product();

        String sql = "select * from product where id = " + id ;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet the_result = statement.executeQuery();

            while (the_result.next()) {

                prod.setId(the_result.getInt("id"));
                prod.setName(the_result.getString("name"));
                prod.setPrice(the_result.getDouble("price"));
                prod.setQuantity(the_result.getInt("quantity"));

            }

        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }

        return prod;
    }

    public void update(Product prod){
        try {

            String sql = "update product set name= ?, price= ?, quantity=? where id = " + prod.getId();

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, prod.getName());
            statement.setDouble(2, prod.getPrice());
            statement.setInt (3, prod.getQuantity());

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

            String sql = "delete from product where id = " + id;
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


