package com.project.project_v1;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    Stage stage;
    @FXML
    private Button AccManageBtn;

    @FXML
    private MenuItem closeBtn;

    @FXML
    private MenuItem logoutBtn;

    @FXML
    private Button menuManageBtn;

    @FXML
    private Button orderManageBtn;

    @FXML
    private Button reportManageBtn;

    public void close() {
        Platform.exit();
    }

    public void logout(ActionEvent event) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
        new LoginController().createLoginStage();
    }
    public void createMainStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = new Stage();
        stage.setTitle("Coffee Shop Management");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void account() throws IOException {
        Stage stage = (Stage)menuManageBtn.getScene().getWindow();
        stage.close();
        new AccountManagerController().createAccountManagerStage();
    }

    public void menu() throws IOException {
        Stage stage = (Stage)menuManageBtn.getScene().getWindow();
        stage.close();
        new MenuManagerController().createMenuManagerStage();
    }

    public void order() throws IOException {
        Stage stage = (Stage)menuManageBtn.getScene().getWindow();
        stage.close();
        new OrderManagerController().createOrderManagerStage();
    }

    public void report() throws IOException {
        Stage stage = (Stage) menuManageBtn.getScene().getWindow();
        stage.close();
        new ReportManagerController().createReportStage();
    }
}
