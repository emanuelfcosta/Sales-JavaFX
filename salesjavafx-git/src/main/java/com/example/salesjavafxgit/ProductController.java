package com.example.salesjavafxgit;

import com.example.dao.ProductDAO;
import com.example.model.Product;
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
import java.util.List;

public class ProductController {

    @FXML
    private Button buttonDelete;

    @FXML
    private Button buttonNew;

    @FXML
    private Button buttonUpdate;

    @FXML
    private Label labelProductId;

    @FXML
    private Label labelProductName;

    @FXML
    private Label labelProductPrice;

    @FXML
    private Label labelProductQuantity;

    @FXML
    private TableColumn<Product, Number> tableColumnProductId;

    @FXML
    private TableColumn<Product, String> tableColumnProductName;

    @FXML
    private TableColumn<Product, Double> tableColumnProductPrice;

    @FXML
    private TableColumn<Product, Number> tableColumnProductQuantity;

    @FXML
    private TableView<Product> tableViewProducts;

    private List<Product> listProduct;
    private ObservableList<Product> observableListProduct;

    @FXML
    private void initialize(){

        loadTableViewProduct();

        tableViewProducts.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) ->selectItemTableViewProduct(newValue));
    }

    public void loadTableViewProduct(){

        ProductDAO dao = new ProductDAO();

        try{


            tableColumnProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
            tableColumnProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tableColumnProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            tableColumnProductQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));


            listProduct = dao.listAll();


            observableListProduct = FXCollections.observableArrayList(listProduct);


            tableViewProducts.setItems(observableListProduct);


        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void selectItemTableViewProduct(Product product){
        if (product != null) {


            labelProductId.setText(String.valueOf(product.getId()));
            labelProductName.setText(product.getName());

            labelProductPrice.setText(String.valueOf(product.getPrice()));
            labelProductQuantity.setText(String.valueOf(product.getQuantity()));
        } else {
            labelProductId.setText("");
            labelProductName.setText("");
            labelProductPrice.setText("");
            labelProductQuantity.setText("");

        }

    }

    @FXML
    public void handleButtonNew() throws IOException {
        ProductDAO productDAO = new ProductDAO();
        Product product = new Product();
        boolean buttonSaveClicked =  showProductDialog(product);
        if (buttonSaveClicked) {
            productDAO.save(product);
            loadTableViewProduct();

        }
    }

    @FXML
    public void handleButtonUpdate() throws IOException {

        ProductDAO productDAO = new ProductDAO();

        Product product = tableViewProducts.getSelectionModel().getSelectedItem();
        if (product != null) {
            boolean buttonSaveClicked = showProductDialog(product);
            if (buttonSaveClicked) {
                productDAO.update(product);
                loadTableViewProduct();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Choose a product from the table!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonDelete() throws IOException {
        ProductDAO productDAO = new ProductDAO();
        Product product = tableViewProducts.getSelectionModel().getSelectedItem();
        if (product != null) {
            productDAO.delete(product.getId());
            loadTableViewProduct();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Choose a product from the table!");
            alert.show();
        }
    }

    public boolean showProductDialog(Product product) throws IOException {

        FXMLLoader loader = new FXMLLoader(ClientDialogController.class.getResource("product-dialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();


        Stage dialogStage = new Stage();
        dialogStage.setTitle("Product");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // set product to controller
        ProductDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setProduct(product);

        // show and wait until user close the dialog
        dialogStage.showAndWait();

        return controller.isButtonSaveClicked();

    }









}
