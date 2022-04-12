package com.project.project_v1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class AccountManagerController implements Initializable {

    Stage stage;

    @FXML
    private Button OkBtn;

    @FXML
    private Label adminInforTitle;

    @FXML
    private Line adminInforLine;

    @FXML
    private Label backBtn;

    @FXML
    private Button changeEmailBtn;

    @FXML
    private Button changeNameBtn;

    @FXML
    private Label changePassBtn;

    @FXML
    private AnchorPane changePassScene;

    @FXML
    private Button changePhoneNumberBtn;

    @FXML
    private Line emailLine;

    @FXML
    private Label errMatchLabel;

    @FXML
    private Button forgotPassBtn;

    @FXML
    private Line fullNameLine;

    @FXML
    private Label fullNamelabel;

    @FXML
    private Label infoBtn;

    @FXML
    private AnchorPane infoScene;

    @FXML
    private Label logoutBtn;

    @FXML
    private Label meowLabel;

    @FXML
    private Label nameCoffeeShop;

    @FXML
    private PasswordField newPassField;

    @FXML
    private PasswordField oldPassField;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private Line phoneNumberLine;

    @FXML
    private PasswordField verifyPassField;

    private boolean isChangePassBtnSelected = false;
    private boolean isInfoBtnSelected = true;
    private String oldPass;
    private String newPass;
    Account account = new Account();
    InputValidation checkInput = new InputValidation();
    CreateMessBox popup = new CreateMessBox();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            infoBtnClicked();
        } catch (IOException e) {
            e.printStackTrace();
        }

        verifyPassField.setOnKeyReleased(keyEvent -> {
            if(verifyPassField.getText().equals(newPassField.getText())) {
                errMatchLabel.setText("");
            }else {
                errMatchLabel.setText("Not Match!");
            }
        });//

        newPassField.setOnKeyReleased(keyEvent -> {
            if(verifyPassField.getText().equals(newPassField.getText())) {
                errMatchLabel.setText("");
            }else {
                errMatchLabel.setText("Not Match!");//v là xong, bên menu, bên bill chưa làm kìa trống chơn z
            }
        });

        OkBtn.setOnMouseClicked(mouseEvent -> {
            try {
                if(okBtnClicked()){
                    System.out.println("Susscess");
                    logout();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        });
        logoutBtn.setOnMouseClicked(mouseEvent -> {
            try {
                logout();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        changePassBtn.setOnMouseClicked(mouseEvent -> {

            changePassBtnClicked();
        });
        infoBtn.setOnMouseClicked(mouseEvent -> {

            try {
                infoBtnClicked();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        backBtn.setOnMouseClicked(mouseEvent -> {
            try {
                back();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        changePassBtn.setOnMouseEntered(mouseEvent -> {
            if(!isChangePassBtnSelected){
                changePassBtn.setScaleX(1.1);
                changePassBtn.setScaleZ(1.1);
            }
        });
        changePassBtn.setOnMouseExited(mouseEvent -> {
            changePassBtn.setScaleX(1);
            changePassBtn.setScaleY(1);
            changePassBtn.setScaleZ(1);
        });

        infoBtn.setOnMouseEntered(mouseEvent -> {
            if(!isInfoBtnSelected) {
                infoBtn.setScaleX(1.1);
                infoBtn.setScaleZ(1.1);
            }
        });
        infoBtn.setOnMouseExited(mouseEvent -> {
            infoBtn.setScaleX(1);
            infoBtn.setScaleZ(1);
        });

        logoutBtn.setOnMouseEntered(mouseEvent -> {
            logoutBtn.setScaleX(1.1);
            logoutBtn.setScaleZ(1.1);
        });
        logoutBtn.setOnMouseExited(mouseEvent -> {
            logoutBtn.setScaleX(1);
            logoutBtn.setScaleZ(1);
        });

    }

    private void changePassBtnClicked() {
        isInfoBtnSelected = false;
        isChangePassBtnSelected = true;
        changePassScene.setVisible(true);
        infoScene.setVisible(false);
        changePassBtn.setStyle("-fx-background-color:  #333333; ");
        infoBtn.setStyle("-fx-background-color:  #5b5b5b;-fx-cursor: hand;");
    }

    private void infoBtnClicked() throws IOException {
        account.readFile(account.getFilePath());
        isInfoBtnSelected = true;
        isChangePassBtnSelected = false;
        changePassScene.setVisible(false);
        infoScene.setVisible(true);
        infoBtn.setStyle("-fx-background-color:  #333333; ");
        changePassBtn.setStyle("-fx-background-color:  #5b5b5b; -fx-cursor: hand;");
        setInfo();
    }

    private void setInfo() {
        fullNamelabel.setText("Full Name: "+account.getName());
        //Event đổi tên.
        changeNameBtn.setOnMouseClicked(mouseEvent -> {
            String newName = new CreateMessBox().popupBoxGetText("Change Name","Enter new full name");
            fullNamelabel.setText(newName.isEmpty()?account.getName():"Full Name: "+newName);
            account.setName(newName.isEmpty()?account.getName():newName);
            try {
                account.writeFile(account.getFilePath(), account.getPass());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        meowLabel.setText("Email: "+account.getMeow());
        //Event đổi meow.
        changeEmailBtn.setOnMouseClicked(mouseEvent -> {
            String newMeow = new CreateMessBox().popupBoxGetText("Change Email","Enter new email");
            meowLabel.setText(newMeow.isEmpty()?account.getMeow():"Email: "+newMeow);
            account.setMeow(newMeow.isEmpty()?account.getMeow():newMeow);
            try {
                account.writeFile(account.getFilePath(), account.getPass());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        phoneNumberLabel.setText("Phone Number: "+account.getPhoneNumber());
        //Event đổi sđt.
        changePhoneNumberBtn.setOnMouseClicked(mouseEvent -> {
            String newPhoneNumber = new CreateMessBox().popupBoxGetText("Change Phone Number","Enter new phone number");
            phoneNumberLabel.setText(newPhoneNumber.isEmpty()?account.getPhoneNumber():"Phone Number: "+newPhoneNumber);
            account.setPhoneNumber(newPhoneNumber.isEmpty()?account.getPhoneNumber():newPhoneNumber);
            try {
                account.writeFile(account.getFilePath(), account.getPass());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void logout() throws IOException {
        Stage stage = (Stage)backBtn.getScene().getWindow();
        stage.close();
        new LoginController().createLoginStage();
    }

    private void back() throws IOException {
        Stage stage = (Stage)backBtn.getScene().getWindow();
        stage.close();
        new MainController().createMainStage();
    }

    private boolean okBtnClicked() throws IOException, NoSuchAlgorithmException {
        /*if(oldPassField.getText().isEmpty() || newPassField.getText().isEmpty())
        {
            popup.popupBoxMess("Password must be not empty!",2);
            return false;//xóa cái này hả
        }*/

        if(!newPassField.getText().equals(verifyPassField.getText())) {
            errMatchLabel.setText("Not match!");
            return false;
        }
        if(checkInput.isEmptyString(oldPassField.getText()))
        {
            popup.popupBoxMess("Old password is empty!",2);//cái này set 1 cái new là đc, cái old rỗng nó sẽ sai, cái verify rỗng nó sẽ not match
            return false;
        }
        //thêm trường hợp trống
        if(checkInput.isEmptyString(newPassField.getText())){
            popup.popupBoxMess("New password is empty!",2);//qa class popup sửa lại chỗ loại popup đi, có 3 loại
            return false;
        }
        if(checkInput.checkMinStringLength(newPassField.getText(),5))
        {
            popup.popupBoxMess("Password must be longer than 5 characters! ",2);
            return false;//
        }
        if(checkInput.checkMaxStringLength(newPassField.getText(),20))
        {
            popup.popupBoxMess("Password must be shorter than 20 characters! ",2);//Mà đã không phân biệt pass mới hay cũ thì xóa luôn đi nhìn nó kì kì lắm
            return false;//ủa sửa r để luôn vậy hả
        }
        //Event so sánh pass mới vs nhập lại pass mới
        //Event này nó diễn ra ở đâu, khi nào ở change pass, ko nó diễn ra ở verifyPassField, khi người dùng gõ cái j đó vào, v thì
        //Viết ở đây là sai r, cái hàm này chỉ đc kích hoạt khi ngta bấm nút OK

        oldPass = oldPassField.getText();
        newPass = newPassField.getText();
        if(!account.changePass(oldPass,newPass)){
            popup.popupBoxMess("Password is wrong!",2);
            return false;
        }
        //
        //popup success
        popup.popupBoxMess("Success!",1);
        return true;
    }

    public void createAccountManagerStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AccountManagerScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = new Stage();
        stage.setTitle("Coffee Shop Management");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
