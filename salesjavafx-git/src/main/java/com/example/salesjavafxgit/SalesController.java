package com.example.salesjavafxgit;

import com.example.dao.ItemSaleDAO;
import com.example.dao.ProductDAO;
import com.example.dao.SaleDAO;
import com.example.model.Client;
import com.example.model.ItemSale;
import com.example.model.Product;
import com.example.model.Sale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SalesController {

    @FXML
    private Button buttonDelete;

    @FXML
    private Button buttonNew;

    @FXML
    private Label labelSaleClient;

    @FXML
    private Label labelSaleDate;

    @FXML
    private Label labelSaleId;

    @FXML
    private Label labelSaleValue;

    @FXML
    private TableColumn<Sale, Client> tableColumnSaleClient;

    @FXML
    private TableColumn<Sale, LocalDate> tableColumnSaleDate;

    @FXML
    private TableColumn<Sale, Number> tableColumnSaleId;

    @FXML
    private TableColumn<Sale, Double> tableColumnSaleValue;

    @FXML
    private TableView<Sale> tableViewSales;

    private List<Sale> listSales;
    private ObservableList<Sale> observableListSales;

    private final SaleDAO saleDAO = new SaleDAO();
    private final ItemSaleDAO itemSaleDAO = new ItemSaleDAO();
    private final ProductDAO productDAO = new ProductDAO();

    @FXML
    private void initialize() {


        loadTableViewSales();

        tableViewSales.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectItemTableViewSales(newValue));

    }

    public void loadTableViewSales() {

        try{

            tableColumnSaleId.setCellValueFactory(new PropertyValueFactory<>("id"));
            tableColumnSaleDate.setCellValueFactory(new PropertyValueFactory<>("dateSale"));
            tableColumnSaleValue.setCellValueFactory(new PropertyValueFactory<>("value"));
            tableColumnSaleClient.setCellValueFactory(new PropertyValueFactory<>("client"));


            listSales = saleDAO.listAll();

            observableListSales = FXCollections.observableArrayList(listSales);

            tableViewSales.setItems(observableListSales);

        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }

    }

    public void selectItemTableViewSales(Sale sale) {
        if (sale != null) {


            labelSaleId.setText(String.valueOf(sale.getId()));
            labelSaleDate.setText(String.valueOf(sale.getDateSale()));
            labelSaleValue.setText(String.format("%.2f", sale.getValue()));
            labelSaleClient.setText(sale.getClient().toString());
        } else {
            labelSaleId.setText("");
            labelSaleDate.setText("");
            labelSaleValue.setText("");
            labelSaleClient.setText("");
        }
    }

    @FXML
    public void handleButtonInsert() throws IOException {

        SaleDAO saleDAO = new SaleDAO();
        Sale sale = new Sale();
        List<ItemSale> listItemsSale = new ArrayList<>();
        sale.setItemSale(listItemsSale);
        boolean buttonSaveClicked =  showSaleDialog(sale);
        if (buttonSaveClicked) {
            try{
                saleDAO.save(sale);
                for (ItemSale listItemSale : sale.getItemSale()) {
                    Product product = listItemSale.getProduct();
                    listItemSale.setSale(saleDAO.lastSale());
                    itemSaleDAO.save(listItemSale);
                    product.setQuantity(product.getQuantity() - listItemSale.getQuantity());
                    productDAO.update(product);
                }
                loadTableViewSales();


            }catch (Exception e){
                System.out.println("Error: " +  e.getMessage());
            }

        }
    }

    @FXML
    public void handleButtonDelete() throws IOException, SQLException {

        //before deleting the item from sale from stock it is replenished

        Sale sale  = tableViewSales.getSelectionModel().getSelectedItem();
        if (sale != null) {

            for (ItemSale listItemSale : sale.getItemSale()) {
                Product product = listItemSale.getProduct();

                product.setQuantity(product.getQuantity()  + listItemSale.getQuantity());
                productDAO.update(product);
                itemSaleDAO.delete(listItemSale.getId());
            }

            saleDAO.delete(sale.getId());
            loadTableViewSales();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Choose a sale from the Table!");
            alert.show();
        }
    }

    public boolean showSaleDialog(Sale sale) throws IOException {

        FXMLLoader loader = new FXMLLoader(SaleDialogController.class.getResource("sale-dialog.fxml"));

        AnchorPane page = (AnchorPane) loader.load();


        Stage dialogStage = new Stage();
        dialogStage.setTitle("Sale");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Set Sale in controller

        SaleDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setSale(sale);

        // waint until close
        dialogStage.showAndWait();

        return controller.isButtonSaveClicked();

    }















}
