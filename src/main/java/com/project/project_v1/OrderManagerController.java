package com.project.project_v1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class OrderManagerController implements Initializable {

    @FXML
    private Label backBtn;

    @FXML
    private Label billListBtn;

    @FXML
    private Label createBillBtn;

    @FXML
    private Label titleLabel;

    @FXML
    private BorderPane billDetailPane;

    @FXML
    private ScrollPane billPane;

    MainController mainController = new MainController();
    Menu menu = new Menu();
    Bills bills = new Bills();
    String billName="";
    Stage stage;
    boolean isBillListBtnSelected;
    boolean isCreateBillBtnSelected;
    InputValidation checkInput = new InputValidation();
    CreateMessBox popup = new CreateMessBox();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        billPane.setStyle("-fx-background : #f9daf1; -fx-border-color: #f9daf1");
        billListClicked();
        backBtn.setOnMouseClicked(mouseEvent -> {
            try {
                backBtnClicked();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        billListBtn.setOnMouseClicked(mouseEvent -> {
            billListClicked();
        });

        createBillBtn.setOnMouseClicked(mouseEvent -> {
            try {
                createBillClicked(menu.menu);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        createBillBtn.setOnMouseClicked(mouseEvent -> {
            try {
                createBillClicked(menu.menu);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        billListBtn.setOnMouseEntered(mouseEvent -> {
            if(!isBillListBtnSelected){
                billListBtn.setScaleX(1.1);
                billListBtn.setScaleZ(1.1);
            }
        });
        billListBtn.setOnMouseExited(mouseEvent -> {
            billListBtn.setScaleX(1);
            billListBtn.setScaleZ(1);
        });

        createBillBtn.setOnMouseEntered(mouseEvent -> {
            if(!isCreateBillBtnSelected) {
                createBillBtn.setScaleX(1.1);
                createBillBtn.setScaleZ(1.1);
            }
        });
        createBillBtn.setOnMouseExited(mouseEvent -> {
            createBillBtn.setScaleX(1);
            createBillBtn.setScaleZ(1);
        });
    }


    private void billListClicked() {
        isBillListBtnSelected = true;
        isCreateBillBtnSelected = false;
        billListBtn.setStyle("-fx-background-color:  #333333; ");
        createBillBtn.setStyle("-fx-background-color:  null;-fx-cursor: hand;");
        titleLabel.setText("All Bill");
        billDetailPane.setBottom(createPaidBillTotalLabel("PAID",new Bill()));
        billDetailPane.setCenter(new ScrollPane());
        //Load data
        try {
            bills.loadData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ScrollPane s = new ScrollPane();
        VBox v = new VBox();
        v.setPadding(new Insets(10,0,0,10));
        v.setSpacing(5);
        s.setContent(v);
        for (Bill element :bills.bills) {
            v.getChildren().addAll(createItemLabel(element));
        }
        billPane.setContent(v);
    }

    private void createBillClicked(ArrayList<Product> list) throws IOException, ClassNotFoundException {
        isBillListBtnSelected = false;
        isCreateBillBtnSelected = true;
        createBillBtn.setStyle("-fx-background-color:  #333333; ");
        billListBtn.setStyle("-fx-background-color:  null;-fx-cursor: hand;");
        Bill bill = new Bill();
        billDetailPane.setBottom(createPaidBillTotalLabel("CREATE",bill));
        ArrayList<Product> indexList = new ArrayList<>();
        titleLabel.setText("Create Bill");
        menu.loadData();
        GridPane gridPane = new GridPane();//đi kím cái note :D ki
        billPane.setContent(gridPane);

        //Bill chi tiet
        ScrollPane s = new ScrollPane();
        billDetailPane.setCenter(s);
        VBox v = new VBox();
        v.setPadding(new Insets(10,10,10,10));
        v.setSpacing(10);
        s.setContent(v);

        gridPane.setHgap(20);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 0, 0, 10));
        int x = 0, y = 0;
        for (Product element : list) {
            Label l = element.createItem();
            Label priceLabel = new MenuManagerController().createPriceLabel(element.getPrice() + "");
            Label addLabel = new MenuManagerController().createDelLabel(".\\src\\main\\resources\\com\\project\\project_v1\\add.png");
            gridPane.add(l, x, y);
            gridPane.add(priceLabel, x, y);
            gridPane.add(addLabel, x++, y);
            if(x == 3) {
                x=0;
                y++;
            }
            new MenuManagerController().effect((Node) addLabel, (Node)l);
            //Event chọn món.
            addLabel.setOnMouseClicked(mouseEvent -> {
                //Tạo cái label tên bill.
                AnchorPane a = new AnchorPane();
                a.setPrefWidth(240);
                Label billNameLabel = new Label("Bill "+bills.autoBillId());
                billNameLabel.setPrefWidth(240);
                billNameLabel.setFont(Font.font("Lucida Handwriting",18));
                billNameLabel.setAlignment(Pos.CENTER);
                billNameLabel.setStyle("-fx-cursor: hand; -fx-background-color: #ff6699;-fx-background-radius: 3");
                a.getChildren().add(billNameLabel);
                //Nếu là món đầu tiên thì mới thêm tên bill.
                if(bill.orderedItem.isEmpty())
                    v.getChildren().add(a);
                //Event nhấn đổi tên bill
                billNameLabel.setOnMouseClicked(mouseEvent1 -> {
                    String billNameTemp = new CreateMessBox().popupBoxGetText("Change Name Bill", "Enter new bill Name");//
                    if(checkInput.checkMaxStringLength(billNameTemp,20)){//test xem còn j nữa
                        popup.popupBoxMess("Billname must be shorter than 20 characters!",2);
                    }else {
                        billName = billNameTemp;
                        billNameLabel.setText(billName.isEmpty()? "Bill "+bills.autoBillId():billName);
                    }



                });
                //Nếu nó là món đã có trong bill rồi thì chỉ tăng số lượng.
                if(bill.orderedItem.containsKey(element)){
                    bill.addItemInBill(element);
                    for (int i = 0; i < indexList.size(); i++) {
                        if(indexList.get(i).equals(element)){
                            AnchorPane item = (AnchorPane) v.getChildren().get(i+1);
                            Label label = (Label)item.getChildren().get(5);
                            label.setText(bill.orderedItem.get(element)+"");
                            System.out.println(label.getText());
                        }
                    }
                    //Nếu nó chưa đc thêm thì thêm mới.
                }else {
                    bill.addItemInBill(element);
                    v.getChildren().addAll(createItemInBill(bill,element,v,2));
                    indexList.add(element);
                }
                billDetailPane.setBottom(createPaidBillTotalLabel("CREATE",bill));
            });
        }
    }

    private AnchorPane createItemInBill(Bill bill, Product product, VBox v, int whatScene){
        AnchorPane a = new AnchorPane();
        a.setPrefWidth(240);

        ImageView imageView = new ImageView(product.getUrlImage());
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setLayoutY(10);
        imageView.setLayoutX(3);

        Label l = new Label(product.getName());
        l.setLayoutX(60);
        l.setLayoutY(5);
        a.setStyle("-fx-background-color: #ff6699; -fx-background-radius: 5px;");
        //a.setBorder(new Border(new BorderStroke(Color.valueOf("#868282"),BorderStrokeStyle.SOLID, CornerRadii.EMPTY,new BorderWidths(1))));
        //a.setStyle("-fx-border-radius: 30;");

        //Chỗ nhập note(màn hình tạo bill)
        TextField textFieldNote = new TextField();
        textFieldNote.setPromptText("Note");
        textFieldNote.setLayoutY(25);
        textFieldNote.setLayoutX(55);
        textFieldNote.setPrefWidth(185);
        //Event lấy note
        textFieldNote.setOnKeyReleased(keyEvent -> {
            bill.setNote(product,textFieldNote.getText());
        });

        //Chỗ hiển thị note (màn hình danh sách bill)
        Label noteLabel = new Label();
        noteLabel.setText(getNote(bill,product));
        noteLabel.setLayoutY(25);
        noteLabel.setLayoutX(60);
        noteLabel.setPrefWidth(180);

        //Label giá.
        Label priceLabel = new Label(product.getPrice()+"vnd");
        priceLabel.setFont(new Font("Forte",12));
        priceLabel.setTextFill(Color.BLACK);
        priceLabel.setAlignment(Pos.CENTER);
        priceLabel.setLayoutY(55);
        priceLabel.setLayoutX(60);

        //Nút + - số lượng.
        Button btn1 = new Button("+");
        Button btn2 = new Button("-");
        btn1.setPrefHeight(10);
        btn1.setPrefWidth(25);
        btn2.setPrefHeight(10);
        btn2.setPrefWidth(25);
        btn1.setLayoutY(60);
        btn2.setLayoutX(145);
        btn2.setLayoutY(60);
        btn1.setLayoutX(195);

        //Danh sách chỉ số sản phẩm để lấy index sửa số lượng.
        Label index = new Label(bill.orderedItem.get(product)+"");
        index.setFont(Font.font("Century",13));
        index.setAlignment(Pos.CENTER);
        index.setPrefHeight(25);
        index.setPrefWidth(25);
        index.setLayoutY(60);
        index.setLayoutX(170);
        //Nếu hóa đơn đã thanh toán thì ko có nút + - số lượng.
        if(bill.isPaid()){
            a.getChildren().addAll(imageView,l,priceLabel,index);
        }else {
            a.getChildren().addAll(imageView,l,priceLabel,btn1,btn2,index);
        }
        //Note sẽ hiển thị khác nhau ở 2 màn hình DS bill và tạo bill.
        if(whatScene == 1){
            a.getChildren().add(noteLabel);
        }else {
            a.getChildren().add(textFieldNote);
        }
        //event nhấn nút + số lượng
        btn1.setOnMouseClicked(mouseEvent -> {
            System.out.println("btn+");
            bill.addItemInBill(product);
            index.setText(bill.orderedItem.get(product)+"");
            bill.setTotalPaid(bill.calculateTotal());
            System.out.println(bill.getTotalPaid());
            try {
                bills.writeData();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(titleLabel.getText().equalsIgnoreCase("create bill")){
                billDetailPane.setBottom(createPaidBillTotalLabel("CREATE",bill));
            }else {
                billDetailPane.setBottom(createPaidBillTotalLabel("PAID",bill));
            }

        });
        //event nhấn nút trừ số lượng
        btn2.setOnMouseClicked(mouseEvent -> {
            System.out.println("btn-");
            //Trừ bớt or xóa
            if(bill.delItemInBill(product)){
                index.setText((bill.orderedItem.get(product))+"");
            }else{
                //Nếu hết sản phẩm luôn thì xóa cả tên bill
                if(bill.orderedItem.isEmpty())
                    v.getChildren().remove(0);
                //Hết sản phầm nào thì xóa ra khỏi bill
                v.getChildren().remove(a);
            }
            bill.setTotalPaid(bill.calculateTotal());
            System.out.println(bill.getTotalPaid());
            //Lưu lại
            try {
                bills.writeData();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Tùy ở màn hình nào thì xử lý theo màn hình đó
            if(titleLabel.getText().equalsIgnoreCase("create bill")){
                billDetailPane.setBottom(createPaidBillTotalLabel("CREATE",bill));
            }else {
                billDetailPane.setBottom(createPaidBillTotalLabel("PAID",bill));
            }

        });
        return a;
    }

    public AnchorPane createItemLabel(Bill bill) {
        AnchorPane a = new AnchorPane();
        a.setPrefHeight(80);
        a.setPrefWidth(550);
        a.setStyle("-fx-background-color:  #c1c1c1; -fx-cursor: hand;-fx-background-radius: 5");
        a.getChildren().addAll(createDelLabel(bill),createIsPaidLabel(bill.isPaid()),createBillNameLabel(bill.getBillName()),createBillIdLabel(bill.getID()+""),createBillTimeLabel(bill.getBillTimeCreate()));
        effect2(a);

        //Event nhấn bill
        a.setOnMouseClicked(mouseEvent -> {
            //Tạo cái nút bấm
            billDetailPane.setBottom(createPaidBillTotalLabel("PAID",bill));
            ScrollPane s = new ScrollPane();
            billDetailPane.setCenter(s);
            //Tạo cái Vbox để chứa mấy cái sản phẩm có trong bill
            VBox v = new VBox();
            v.setPadding(new Insets(10,10,10,10));
            v.setSpacing(10);
            //Tạo cái label tên bill lúc in ra.
            AnchorPane billNameAnPane = new AnchorPane();
            billNameAnPane.setPrefWidth(240);
            Label billNameLabel = new Label(bill.getBillName());
            billNameLabel.setPrefWidth(240);
            billNameLabel.setFont(Font.font("Lucida Handwriting",18));
            billNameLabel.setAlignment(Pos.CENTER);
            //billNameLabel.setStyle("-fx-cursor: hand; -fx-background-color: #c172ab;-fx-background-radius: 5");
            billNameAnPane.getChildren().add(billNameLabel);

            //Nếu là món đầu tiên thì mới thêm tên bill.
            v.getChildren().add(billNameAnPane);
            //Bỏ VBox zo cái pane cuộn chuột.
            s.setContent(v);
            //Đổi màu nó khi bị bấm, cái này fail
            a.setStyle("-fx-cursor: hand; -fx-background-color: #868282; -fx-background-radius: 5");
            for (Product element :bill.orderedItem.keySet()) {
                //Tạo sản phẩm có trong bill
                v.getChildren().add(createItemInBill(bill,element,v,1));
            }

        });
        return a;
    }

    private Label createIsPaidLabel(boolean isPaid) {
        Image image = new Image("file:.\\src\\main\\resources\\com\\project\\project_v1\\paid.png");
        Label l = new Label();
        l.setLayoutX(485);
        l.setAlignment(Pos.CENTER);
        l.setPrefWidth(60);
        l.setPrefHeight(80);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(35);
        imageView.setFitWidth(35);
        if(isPaid)
            l.setGraphic(imageView);
        return l;
    }

    private Label createDelLabel(Bill billToDelete) {
        Label l = new Label();
        l.setLayoutX(420);
        l.setAlignment(Pos.CENTER);
        l.setPrefWidth(60);
        l.setPrefHeight(80);
        l.setStyle("-fx-background-color: #e44a4a;");
        l.setGraphic(createImageView());
        effect1(l);
        //Event xóa bill
        l.setOnMouseClicked(mouseEvent -> {
            if(popup.popupChoose("Are you sure to delete it?")){
                bills.bills.remove(billToDelete);
                try {
                    bills.writeData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                billListClicked();
            }//h còn gì ta, bên menu cũng có xóa
        });
        return l;
    }

    private ImageView createImageView() {
        Image image = new Image("file:.\\src\\main\\resources\\com\\project\\project_v1\\del.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(35);
        imageView.setFitWidth(35);
        return imageView;
    }

    private Label createBillNameLabel(String name) {
        Label l = new Label(name);
        l.setLayoutX(14);
        l.setLayoutY(10);
        l.setFont(new Font("system",16));
        return l;
    }

    private Label createBillIdLabel(String id) {
        Label l = new Label("ID: "+id);
        l.setLayoutX(14);
        l.setLayoutY(32);
        l.setFont(new Font("system",12));
        return l;
    }

    private void backBtnClicked() throws IOException {
        stage = (Stage)backBtn.getScene().getWindow();
        stage.close();
        mainController.createMainStage();
    }

    public void createOrderManagerStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("OrderManagerScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = new Stage();
        stage.setTitle("Coffee Shop Management");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public VBox createPaidBillTotalLabel(String btnName,Bill bill) {
        VBox a = new VBox();
        a.setPadding(new Insets(10,10,10,10));
        Label l = new Label("Total: ");
        l.setLayoutX(14);
        l.setLayoutY(14);
        l.setFont(Font.font("system",15));
        Label l2 = new Label(bill.calculateTotal()+" VND");
        l2.setPrefWidth(254);
        l2.setLayoutX(60);
        l2.setLayoutY(14);
        l2.setAlignment(Pos.CENTER_RIGHT);
        l2.setFont(Font.font("system",15));

        Button paidBtn = new Button(btnName);
        paidBtn.setStyle("-fx-cursor: hand;");
        paidBtn.setPrefWidth(296);
        paidBtn.setPrefHeight(50);
        //Nếu thanh toán rồi thì k hiện nút thanh toán nữa.
        if(!bill.isPaid()){
            a.getChildren().addAll(l,l2,paidBtn);
        }else {
            a.getChildren().addAll(l,l2);
        }


        //Event nhấn CREATE/PAID.
        paidBtn.setOnMouseClicked(mouseEvent -> {
            //Nếu nút này là nút create
            if(paidBtn.getText().equals("CREATE")){
                //Thêm các thuộc tính của bill này, thì sau khi ông này thêm xong
                bill.setBillName(billName.isEmpty()? "Bill "+bills.autoBillId():billName);//Cái này nó bắt đầu set tên cho bill r nè
                billName="";
                bill.setBillTimeCreate(new Date());//có time trong bill rồi, h phải show nó ra
                bill.setID(bills.autoBillId());
                bill.setTotalPaid(bill.calculateTotal());
                System.out.println(bill.getTotalPaid());
                //thêm cái bill này vào ds bill
                bills.bills.add(bill);
                //Lưu lại xong về cửa sổ bill list.
                try {
                    bills.writeData();
                    //createBillClicked(menu.menu);
                    billListClicked();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Nếu nút này là nút PAID
            }else {
                bill.setPaid(true);
                bill.setTotalPaid(bill.calculateTotal());
                //Lưu lịch sử để báo cáo.
                new History().writeFile(bill);
                try {
                    bills.writeData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                billListClicked();
            }
        });
        return a;
    }

    private Label createBillTimeLabel(Date time) {
        //Tạo cái thời gian label
        Label timeLabel = new Label(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(time));
        timeLabel.setLayoutX(310);
        timeLabel.setLayoutY(60);
        return timeLabel;
    }

    private String getNote(Bill bill,Product product) {
        String note="Note: ";
        for (Product element :bill.noteList.keySet()) {
            if(element.equals(product))
                note+=bill.noteList.get(element);
        }

        return note;
    }

    private void effect1(Node node){
        node.setOnMouseEntered(mouseEvent -> {
            node.setScaleX(1.05);
            node.setScaleZ(1.05);
        });
        node.setOnMouseExited(mouseEvent -> {
            node.setScaleX(1);
            node.setScaleZ(1);
        });
    }

    private void effect2(Node node){
        node.setOnMouseEntered(mouseEvent -> {
            node.setStyle("-fx-cursor: hand; -fx-background-color: #868282; -fx-background-radius: 5");
        });
        node.setOnMouseExited(mouseEvent -> {
            node.setStyle("-fx-background-color:  #c1c1c1; -fx-cursor: hand;-fx-background-radius: 5");
        });
    }
}
