package com.example.salesjavafxgit;

import com.example.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProductDialogController {

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonSave;

    @FXML
    private TextField textFieldProductName;

    @FXML
    private TextField textFieldProductPrice;

    @FXML
    private TextField textFieldProductQuantity;

    private Stage dialogStage;
    private boolean buttonSaveClicked = false;
    private Product product;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.textFieldProductName.setText(product.getName());
        this.textFieldProductPrice.setText(String.valueOf(product.getPrice()));
        this.textFieldProductQuantity.setText(String.valueOf(product.getQuantity()));
    }

    public void handleButtonSave() {

        if (validateInputFields()) {

            product.setName(textFieldProductName.getText());
            product.setPrice(Double.parseDouble(textFieldProductPrice.getText()));
            product.setQuantity(Integer.parseInt(textFieldProductQuantity.getText()));

            buttonSaveClicked = true;
            dialogStage.close();

        }
    }

    @FXML
    public void handleButtonCancel() {
        dialogStage.close();
    }

    //validade fields

    private boolean validateInputFields() {
        String errorMessage = "";

        if (textFieldProductName.getText() == null || textFieldProductName.getText().length() == 0) {
            errorMessage += "Invalid name!\n";
        }
        if ( textFieldProductQuantity.getText() == null || textFieldProductQuantity.getText().length() == 0) {
            errorMessage += "Invalid Quantity!\n";
        }
        if (textFieldProductPrice.getText() == null || textFieldProductPrice.getText().length() == 0) {
            errorMessage += "Invalid Price\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Error Message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input fields");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }






}
