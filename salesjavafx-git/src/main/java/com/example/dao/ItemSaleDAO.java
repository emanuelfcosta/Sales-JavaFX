package com.example.dao;

import com.example.connectionjdbc.SingleConnection;
import com.example.model.ItemSale;
import com.example.model.Product;
import com.example.model.Sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemSaleDAO {

    private Connection connection;

    public ItemSaleDAO(){
        connection = SingleConnection.getConnection();
    }

    public void save(ItemSale itemSale){
        try {
            String sql = "insert into item_sale(quantity,value,idproduct,idsale) values(?,?,?,?) ";
            PreparedStatement insert = connection.prepareStatement(sql);

            insert.setInt(1,itemSale.getQuantity());
            insert.setDouble(2,itemSale.getValue());
            insert.setInt(3,itemSale.getProduct().getId());
            insert.setInt (4,itemSale.getSale().getId());
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

    public List<ItemSale> listAll() {

        List<ItemSale> list = new ArrayList<ItemSale>();

        String sql = "select * from item_sale";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet the_result = statement.executeQuery();

            while (the_result.next()) {
                ItemSale itemSale = new ItemSale();
                Product product = new Product();
                Sale sale = new Sale();

                itemSale.setId(the_result.getInt("id"));
                itemSale.setQuantity(the_result.getInt("quantity"));
                itemSale.setValue(the_result.getDouble("value"));

                product.setId(the_result.getInt("idproduct"));
                sale.setId(the_result.getInt("idsale"));

                //obtaining product data associated with the sales item
                ProductDAO productDAO  = new ProductDAO();
                product = productDAO.findById(product.getId());

                SaleDAO saleDAO = new SaleDAO();
                sale = saleDAO.findById(sale.getId());

                itemSale.setProduct(product);
                itemSale.setSale(sale);


                list.add(itemSale);
            }


        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }

        return list;
    }


    public List<ItemSale> listBySale(Sale theSale) {
        // list all items by sale
        List<ItemSale> list = new ArrayList<ItemSale>();

        String sql = "select * from item_sale where idsale = " + theSale.getId();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet the_result = statement.executeQuery();

            while (the_result.next()) {
                ItemSale itemSale = new ItemSale();
                Product product = new Product();
                Sale sale = new Sale();

                itemSale.setId(the_result.getInt("id"));
                itemSale.setQuantity(the_result.getInt("quantity"));
                itemSale.setValue(the_result.getDouble("value"));

                product.setId(the_result.getInt("idproduct"));
                sale.setId(the_result.getInt("idsale"));

                //obtaining product data associated with the sales item
                ProductDAO productDAO = new ProductDAO();
                product = productDAO.findById(product.getId());


                itemSale.setProduct(product);
                itemSale.setSale(theSale);


                list.add(itemSale);
            }


        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }

        return list;
    }

    public List<ItemSale> findById(int id) {

        ItemSale itemSale = new ItemSale();

        List<ItemSale> list = new ArrayList<ItemSale>();

        String sql = "select * from item_sale where id = " + id;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet the_result = statement.executeQuery();

            while (the_result.next()) {
                Product product = new Product();
                Sale sale = new Sale();

                itemSale.setId(the_result.getInt("id"));
                itemSale.setQuantity(the_result.getInt("quantity"));
                itemSale.setValue(the_result.getDouble("value"));

                product.setId(the_result.getInt("idproduct"));
                sale.setId(the_result.getInt("idsale"));

                //obtaining product data associated with the sales item
                ProductDAO productDAO = new ProductDAO();
                product = productDAO.findById(product.getId());

                SaleDAO saleDAO = new SaleDAO();
                sale = saleDAO.findById(sale.getId());

                itemSale.setProduct(product);
                itemSale.setSale(sale);


                list.add(itemSale);
            }


        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }

        return list;
    }

    public void delete(int id){
        try{

            String sql = "delete from item_sale where id = " + id;
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
