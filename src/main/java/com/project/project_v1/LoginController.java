package com.project.project_v1;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    Stage stage;
    Account acc = new Account();
    @FXML
    private TextField userNameTextField;

    @FXML
    //Stage để nhận event bàn phím.
    private BorderPane stageForKey;

    @FXML
    private PasswordField passwordField;


    @FXML
    private Button loginBtn;

    @FXML
    private CheckBox rememberLoginCheckbox;

    @FXML
    private Button forgotPassBtn;

    InputValidation checkInput = new InputValidation();
    CreateMessBox popup = new CreateMessBox();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stageForKey.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent key) {
                if(key.getCode().equals(KeyCode.ENTER)) {
                    try {
                        login();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
            }
        });



    }

    public void forgotPassBtnClicked() {
        popup.popupBoxMess("Please Contact To Me!",1);
    }



    public void login() throws IOException, NoSuchAlgorithmException {
        //System.out.println(((Node)event.getSource()).getScene().getWindow());
        //System.out.println(loginBtn.getScene().getWindow());
        stage = (Stage) loginBtn.getScene().getWindow();

        if (checkInput.isEmptyString(userNameTextField.getText())) {
            popup.popupBoxMess("Username must be not empty!",2);//xoa comment
            return;
        }
        if(checkInput.isEmptyString(passwordField.getText())){
            popup.popupBoxMess("Password must be not empty!",2);
            return;
        }
        if(!userNameTextField.getText().equalsIgnoreCase("admin")) {
            popup.popupBoxMess("Wrong username or password!",2);
            return;
        }

        if(!acc.verifyPass(passwordField.getText())){//này nè vút lên chung hàm luôn, ko check riêng
            popup.popupBoxMess("Wrong username or password!",2);//ok r ừa
            return;
        }
        stage.close();
        new MainController().createMainStage();
    }

    public void createLoginStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loginScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }






}