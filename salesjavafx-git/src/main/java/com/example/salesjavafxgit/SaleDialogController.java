package com.example.salesjavafxgit;

import com.example.dao.ClientDAO;
import com.example.dao.ProductDAO;
import com.example.model.Client;
import com.example.model.ItemSale;
import com.example.model.Product;
import com.example.model.Sale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class SaleDialogController {

    @FXML
    private Button buttonAdd;

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonSave;

    @FXML
    private ComboBox<Client> comboboxSaleClient;

    @FXML
    private ComboBox<Product> comboboxSaleProduct;

    @FXML
    private DatePicker datePickerSaleDate;

    @FXML
    private TableColumn<ItemSale, Number> tableColumnItemSaleQuanity;

    @FXML
    private TableColumn<ItemSale, Number> tableColumnItemSaleValue;

    @FXML
    private TableColumn<ItemSale, String> tableColunItemSaleProduct;

    @FXML
    private TableView<ItemSale> tableViewSaleItemSale;

    @FXML
    private TextField textFieldSaleItemSaleQuantity;

    @FXML
    private TextField textFieldSaleValue;


    private List<Client> listClients;
    private List<Product> listProducts;
    private ObservableList<Client> observableListClients;
    private ObservableList<Product> observableListProducts;
    private ObservableList<ItemSale> observableListItemSale;

    private final ClientDAO clientDAO = new ClientDAO();
    private final ProductDAO productDAO = new ProductDAO();

    private Stage dialogStage;
    private boolean buttonSaveClicked = false;
    private Sale sale;

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isButtonSaveClicked() {
        return buttonSaveClicked;
    }

    public void setButtonSaveClicked(boolean buttonSaveClicked) {
        this.buttonSaveClicked = buttonSaveClicked;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }


    @FXML
    private void initialize() {

        loadComboBoxClients();
        loadComboBoxProducts();


        tableColunItemSaleProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
        tableColumnItemSaleQuanity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tableColumnItemSaleValue.setCellValueFactory(new PropertyValueFactory<>("value"));

    }

    public void loadComboBoxClients() {

        listClients = clientDAO.listAll();
        observableListClients = FXCollections.observableArrayList(listClients);
        comboboxSaleClient.setItems(observableListClients);
    }

    public void loadComboBoxProducts() {

        listProducts = productDAO.listAll();
        observableListProducts = FXCollections.observableArrayList(listProducts);
        comboboxSaleProduct.setItems(observableListProducts);
    }

    @FXML
    public void handleButtonAdd() {
        Product product;

        ItemSale itemSale = new ItemSale();
        if (comboboxSaleProduct.getSelectionModel().getSelectedItem() != null) {
            product = (Product) comboboxSaleProduct.getSelectionModel().getSelectedItem();
            if (product.getQuantity() >= Integer.parseInt(textFieldSaleItemSaleQuantity.getText())) {
                itemSale.setProduct((Product) comboboxSaleProduct.getSelectionModel().getSelectedItem());
                itemSale.setQuantity(Integer.parseInt(textFieldSaleItemSaleQuantity.getText()));
                itemSale.setValue(itemSale.getProduct().getPrice() * itemSale.getQuantity());
                sale.getItemSale().add(itemSale);
                sale.setValue(sale.getValue() + itemSale.getValue());
                observableListItemSale = FXCollections.observableArrayList(sale.getItemSale());


                tableViewSaleItemSale.setItems(observableListItemSale);
                textFieldSaleValue.setText(String.format("%.2f", sale.getValue()));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Problems in choosing the product!");
                alert.setContentText("There is no quantity of products available in stock!");
                alert.show();
            }
        }
    }

    @FXML
    public void handleButtonSave() {
        if (validateInputFields()) {
            sale.setClient((Client) comboboxSaleClient.getSelectionModel().getSelectedItem());
            sale.setDateSale(datePickerSaleDate.getValue());
            buttonSaveClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    public void handleButtonCancel() {
        getDialogStage().close();
    }

    private boolean validateInputFields() {
        String errorMessage = "";
        if (comboboxSaleClient.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Invalid client!\n";
        }
        if (datePickerSaleDate.getValue() == null) {
            errorMessage += "Invalid date!\n";
        }
        if (observableListItemSale == null) {
            errorMessage += "Invalid Sale Items!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            //error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Fields");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }












}
