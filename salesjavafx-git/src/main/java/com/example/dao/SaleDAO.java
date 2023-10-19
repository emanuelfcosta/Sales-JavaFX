package com.example.dao;

import com.example.connectionjdbc.SingleConnection;
import com.example.model.Client;
import com.example.model.ItemSale;
import com.example.model.Sale;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleDAO {

    private Connection connection ;

    public SaleDAO(){
        connection = SingleConnection.getConnection();
    }

    public void save(Sale sale){
        try {
            String sql = "insert into sale(sale_date,value,idclient) values(?,?,?) ";
            PreparedStatement insert = connection.prepareStatement(sql);

            insert.setDate(1, Date.valueOf(sale.getDateSale()) );
            insert.setDouble(2, sale.getValue());
            insert.setInt(3, sale.getClient().getId());
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

    public List<Sale> listAll() {
        List<Sale> list = new ArrayList<Sale>();

        String sql = "select * from sale";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet the_result = statement.executeQuery();

            while (the_result.next()) {
                Sale sale = new Sale();
                Client client = new Client();
                List<ItemSale> itemsSale = new ArrayList<>();

                sale.setId(the_result.getInt("id"));
                sale.setDateSale(the_result.getDate("sale_date").toLocalDate());
                sale.setValue(the_result.getDouble("value"));
                client.setId(the_result.getInt("idclient"));

                // obtaining complete customer data associated with the sale
                ClientDAO clientDAO = new ClientDAO();
                client = clientDAO.findById(client.getId());

                //obtaining data from sales idtems

                ItemSaleDAO itemSaleDAO  = new ItemSaleDAO();


                itemsSale = itemSaleDAO.listBySale(sale);

                sale.setClient(client);
                sale.setItemSale(itemsSale);


                list.add(sale);
            }


        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }

        return list;
    }

    public Sale findById(int id) {
        Sale sale = new Sale();

        String sql = "select * from sale where id = " + id ;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet the_result = statement.executeQuery();

            while (the_result.next()) {

                Client client = new Client();

                sale.setId(the_result.getInt("id"));
                sale.setDateSale(the_result.getDate("sale_data").toLocalDate());
                sale.setValue(the_result.getDouble("value"));
                client.setId(the_result.getInt("idclient"));
                sale.setClient(client);

            }

        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }

        return sale;
    }


    public Sale lastSale() {
        String sql = "select max(id) as max from sale";
        Sale sale = new Sale();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet the_result = statement.executeQuery();

            if (the_result.next()) {
                sale.setId(the_result.getInt("max"));
                return sale;
            }
        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }
        return sale;
    }

    public void delete(int id){
        try{

            String sql = "delete from sale where id = " + id;
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
