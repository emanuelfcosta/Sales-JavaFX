package com.example.salesjavafxgit;

import com.example.dao.ClientDAO;
import com.example.model.Client;
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

public class ClientController {

    @FXML
    private Label labelClientAddress;

    @FXML
    private Label labelClientEmail;

    @FXML
    private Label labelClientId;

    @FXML
    private Label labelClientName;

    @FXML
    private TableColumn<Client, String> tableViewColumnClientAddress;

    @FXML
    private TableColumn<Client, String> tableViewColumnClientEmail;

    @FXML
    private TableColumn<Client, Number> tableViewColumnClientId;

    @FXML
    private TableColumn<Client, String> tableViewColumnClientName;


    @FXML
    private TableView<Client> tableViewClients;
    @FXML
    private Button buttonNew;

    @FXML
    private Button buttonUpdate;

    @FXML
    private Button buttonDelete;

    private List<Client> listClient;
    private ObservableList<Client> observableListClient;



    @FXML
    private void initialize(){

        loadTableViewClient();

        tableViewClients.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) ->selectItemTableViewClient(newValue));
    }

    public void loadTableViewClient(){

        ClientDAO dao = new ClientDAO();

        try{

            tableViewColumnClientId.setCellValueFactory(new PropertyValueFactory<>("id"));
            tableViewColumnClientName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tableViewColumnClientEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            tableViewColumnClientAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

            listClient = dao.listAll();

            observableListClient = FXCollections.observableArrayList(listClient);

            tableViewClients.setItems(observableListClient);


        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void selectItemTableViewClient(Client client){
        if (client != null) {
            labelClientId.setText(String.valueOf(client.getId()));
            labelClientName.setText(client.getName());
            labelClientAddress.setText(client.getAddress());
            labelClientEmail.setText(client.getEmail());
        } else {
            labelClientId.setText("");
            labelClientName.setText("");
            labelClientAddress.setText("");
            labelClientEmail.setText("");

        }

    }

    @FXML
    public void handleButtonNew() throws IOException {
        ClientDAO clientDAO = new ClientDAO();
        Client client = new Client();
        boolean buttonSaveClicked =  showClientDialog(client);
        if (buttonSaveClicked) {
            clientDAO.save(client);
            loadTableViewClient();

        }
    }
    @FXML
    public void handleButtonUpdate() throws IOException {

        ClientDAO clientDAO = new ClientDAO();

        Client client = tableViewClients.getSelectionModel().getSelectedItem();
        if (client != null) {
            boolean buttonSaveClicked = showClientDialog(client);
            if (buttonSaveClicked) {
                clientDAO.update(client);
                loadTableViewClient();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Choose a client from the table!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonDelete() throws IOException {
        ClientDAO clientDAO = new ClientDAO();
        Client client = tableViewClients.getSelectionModel().getSelectedItem();
        if (client != null) {
            clientDAO.delete(client.getId());
            loadTableViewClient();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Choose a client from the table!");
            alert.show();
        }
    }

    public boolean showClientDialog(Client client) throws IOException {

        FXMLLoader loader = new FXMLLoader(ClientDialogController.class.getResource("client-dialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();


        Stage dialogStage = new Stage();
        dialogStage.setTitle("Client");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // set client to controller
        ClientDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setClient(client);

        // show and wait until user close the dialog
        dialogStage.showAndWait();

        return controller.isButtonSaveClicked();

    }





}
