package com.example.salesjavafxgit;

import com.example.model.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClientDialogController {

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonSave;

    @FXML
    private TextField textFieldClientAddress;

    @FXML
    private TextField textFieldClientEmail;

    @FXML
    private TextField textFieldClientName;

    private Stage dialogStage;
    private boolean buttonSaveClicked = false;
    private Client client;

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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        this.textFieldClientName.setText(client.getName());
        this.textFieldClientAddress.setText(client.getAddress());
        this.textFieldClientEmail.setText(client.getEmail());
    }

    @FXML
    public void handleButtonSave() {

        if (validateInputFields()) {

            client.setName(textFieldClientName.getText());
            client.setAddress(textFieldClientAddress.getText());
            client.setEmail(textFieldClientEmail.getText());

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

        if (textFieldClientName.getText() == null || textFieldClientName.getText().length() == 0) {
            errorMessage += "Invalid name!\n";
        }
        if (textFieldClientAddress.getText() == null || textFieldClientAddress.getText().length() == 0) {
            errorMessage += "Invalid Address!\n";
        }
        if (textFieldClientEmail.getText() == null || textFieldClientEmail.getText().length() == 0) {
            errorMessage += "Invalid Email\n";
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
