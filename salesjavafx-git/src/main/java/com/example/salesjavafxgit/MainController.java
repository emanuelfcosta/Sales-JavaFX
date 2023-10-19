package com.example.salesjavafxgit;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainController {

    @FXML
    private AnchorPane achorPane;


    @FXML
    private Button buttonClients;

    @FXML
    private Button buttonProducts;

    @FXML
    private Button buttonSales;

    @FXML
    private Menu menuItemClients;

    @FXML
    private Menu menuItemProducts;

    @FXML
    private Menu menuItemSales;



    @FXML
    public void handleClient() throws IOException {


        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("client-view.fxml") );
        achorPane.getChildren().setAll(a);

    }

    @FXML
    public void handleSales() throws IOException {


        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("sales-view.fxml") );
        achorPane.getChildren().setAll(a);

    }

    @FXML
    public void handleProducts() throws IOException {

        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("product-view.fxml") );
        achorPane.getChildren().setAll(a);

    }


}