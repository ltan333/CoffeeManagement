package com.project.project_v1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class MenuManagerController implements Initializable {
    Stage stage;

    @FXML
    private BorderPane subBorderPane;

    @FXML
    private TextField searchField;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Label menuListBtn;

    @FXML
    private Label addBtn;

    @FXML
    private Label backBtn;

    @FXML
    private Label errSearchLabel;

    @FXML
    private ScrollPane mainPane;
    Menu menu = new Menu();
    MainController mainController = new MainController();
    boolean isMenuListBtnSelected = true;
    boolean isAddBtnSelected = false;

    InputValidation checkInput = new InputValidation();
    CreateMessBox popup = new CreateMessBox();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showMenu(menu.menu);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        menuListBtn.setOnMouseClicked(mouseEvent -> {
            try {
                showMenu(menu.menu);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        backBtn.setOnMouseClicked(event -> {
            try {
                backBtnClicked();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        addBtn.setOnMouseClicked(mouseEvent -> {
            addBtnClicked();
        });

        searchField.setOnKeyReleased(keyEvent -> {
            try {
                showMenu(searchItem(searchField.getText()));
                if(searchItem(searchField.getText()).isEmpty()){
                    errSearchLabel.setText("Not found!");
                }else {
                    errSearchLabel.setText("");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        menuListBtn.setOnMouseEntered(mouseEvent -> {
            if(!isMenuListBtnSelected){
                menuListBtn.setScaleX(1.1);
                menuListBtn.setScaleZ(1.1);
            }
        });
        menuListBtn.setOnMouseExited(mouseEvent -> {
            menuListBtn.setScaleX(1);
            menuListBtn.setScaleZ(1);
        });

        addBtn.setOnMouseEntered(mouseEvent -> {
            if(!isAddBtnSelected) {
                addBtn.setScaleX(1.1);
                addBtn.setScaleZ(1.1);
            }
        });
        addBtn.setOnMouseExited(mouseEvent -> {
            addBtn.setScaleX(1);
            addBtn.setScaleZ(1);
        });
    }

    private void showMenu(ArrayList<Product> list) throws IOException, ClassNotFoundException {
        isMenuListBtnSelected = true;
        isAddBtnSelected = false;
        menuListBtn.setStyle("-fx-background-color:  #333333; ");
        addBtn.setStyle("-fx-background-color:  null;-fx-cursor: hand;");
        menu.loadData();
        System.out.println(menu.menu.size());
        mainBorderPane.setCenter(subBorderPane);
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 0, 0, 10));

        Label l,priceLabel,delLabel;
        int x=0,y=0;
        for (Product element :list) {
            l = element.createItem();
            priceLabel = createPriceLabel(element.getPrice()+"");
            delLabel = createDelLabel(".\\src\\main\\resources\\com\\project\\project_v1\\icon\\del.png");
            gridPane.add(l,x,y);
            gridPane.add(priceLabel,x,y);
            gridPane.add(delLabel,x++,y);
            effect((Node) delLabel, (Node)l);

            //Event xóa sản phẩm.
            delLabel.setOnMouseClicked(mouseEvent -> {
                if(popup.popupChoose("Are you sure to delete item ? "))
                {
                    try {
                        deleteItem(element);
                        showMenu(list);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            });
            if(x == 5) {
                x=0;
                y++;
            }
        }
        mainPane.setContent(gridPane);
        mainPane.setPannable(true);

    }

    public void showMenu2(ScrollPane mainPane,int column, ArrayList<Product> list) throws IOException, ClassNotFoundException {
        menu.loadData();
        GridPane gridPane = new GridPane();
        mainPane.setContent(gridPane);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 0, 0, 10));

    }
    private ArrayList searchItem(String key) {
        ArrayList<Product> searchedList = new ArrayList<>();
        for(Product element: menu.menu) {
            if(element.getName().toLowerCase().contains(key.toLowerCase())){
                searchedList.add(element);
            }
        }
        return searchedList;
    }
    private boolean deleteItem(Product p) throws IOException {
        for(Product element:menu.menu){
            if(element.equals(p)){
                menu.menu.remove(p);
                menu.writeData();
                return true;
            }

        }
        System.out.println("Err");
        return false;
    }

    public Label createDelLabel(String filePath) throws IOException {
        Label delLabel = new Label();
        Image image = new Image("file:"+filePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        delLabel.setCursor(Cursor.HAND);
        delLabel.setAlignment(Pos.CENTER);
        delLabel.setPrefWidth(155);
        delLabel.setPrefHeight(140);
        delLabel.setGraphic(imageView);
        delLabel.setOpacity(0);
        return delLabel;
    }

    public Label createPriceLabel(String price) {
        Label priceLabel = new Label(price+"vnd");
        priceLabel.setFont(new Font("Forte",12));
        priceLabel.setTextFill(Color.rgb(242,109,109));
        priceLabel.setAlignment(Pos.BOTTOM_CENTER);
        priceLabel.setPrefSize(155,175);
        return priceLabel;
    }

    public void effect(Node node, Node glow){
        node.setOnMouseEntered(mouseEvent -> {
            Lighting l = new Lighting();
            l.setLight(new Light.Distant(0.1,0.1,Color.rgb(166,166,166)));
            node.setOpacity(1);
            node.setScaleX(1.1);
            node.setScaleY(1.1);
            node.setScaleZ(1.1);
            glow.setEffect(new Lighting());
        });

        node.setOnMouseExited(mouseEvent -> {
            node.setOpacity(0);
            node.setScaleX(1);
            node.setScaleY(1);
            node.setScaleZ(1);
            glow.setEffect(null);
        });
    }

    private void backBtnClicked() throws IOException {
        stage = (Stage)backBtn.getScene().getWindow();
        stage.close();
        mainController.createMainStage();
    }

    public void addBtnClicked() {
        isMenuListBtnSelected = false;
        isAddBtnSelected = true;
        addBtn.setStyle("-fx-background-color:  #333333; ");
        menuListBtn.setStyle("-fx-background-color:  null;-fx-cursor: hand;");
        AnchorPane a = new AnchorPane();
        mainBorderPane.setStyle("-fx-background-color:  #f9daf1");
        FileChooser fileChooser = new FileChooser();
        Label title = new Label("Add A Item To Menu");
        title.setFont(new Font("Forte", 30));
        title.setLayoutX(283);
        title.setLayoutY(35);
        TextField name = new TextField();
        name.setPrefSize(214,26);
        name.setLayoutX(314);
        name.setLayoutY(116);
        name.setPromptText("Name");

        TextField price = new TextField();
        price.setPrefSize(214,26);
        price.setLayoutX(314);
        price.setLayoutY(167);
        price.setPromptText("Price");

        TextField url = new TextField();
        url.setPrefSize(214,26);
        url.setLayoutX(314);
        url.setLayoutY(234);
        url.setPromptText("Url");

        Button browserBtn = new Button();
        browserBtn.setText("...");
        browserBtn.setLayoutX(537);
        browserBtn.setLayoutY(234);

        Button addBtn = new Button();
        addBtn.setText("ADD");
        addBtn.setLayoutX(398);
        addBtn.setLayoutY(300);

        Stage s = new Stage();

        browserBtn.setOnMouseClicked(mouseEvent -> {
            File file = fileChooser.showOpenDialog(s);
            url.setText(file.toURI().toString());
            System.out.println(file.toURI().toString());
        });
        //Event bấm nút thêm món zo menu
        addBtn.setOnMouseClicked(mouseEvent -> {
            if(checkInput.isEmptyString(name.getText())){
                popup.popupBoxMess("Name is null !",2);//đừng viết theo ta, viết kiểu cơ bản đi,ntn nè
            }else if(checkInput.checkMaxStringLength(name.getText(),22)) {//hamf nayf đặt tên ntn sao sài ở đây đc :D,lên menu đếm xem khi nhập bao nhiu thì nó ...rồi qa kia viết hàm max mới
                popup.popupBoxMess("Name must be shorter than 20 characters!", 2);
            }else if(!checkInput.isNumber(price.getText())) {
                popup.popupBoxMess("Price must be numeric character", 2);
            }else if(checkInput.isEmptyString(url.getText())){
                popup.popupBoxMess("Url is null!",2);
            }else{
                Product p = new Product(name.getText(),Math.round(Double.parseDouble(price.getText())*100.0)/100.0,url.getText());
                menu.addItem(p);
                popup.popupBoxMess("Add success!",1);
            }

            try {
                menu.writeData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        a.getChildren().addAll(title,name,price,url,browserBtn,addBtn);
        mainBorderPane.setCenter(a);
    }

    public void createMenuManagerStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MenuManagerScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = new Stage();
        stage.setTitle("Coffee Shop Management");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


}
