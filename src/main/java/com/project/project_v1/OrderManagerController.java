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
        GridPane gridPane = new GridPane();//??i k??m c??i note :D ki
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
            Label addLabel = new MenuManagerController().createDelLabel(".\\src\\main\\resources\\com\\project\\project_v1\\icon\\add.png");
            gridPane.add(l, x, y);
            gridPane.add(priceLabel, x, y);
            gridPane.add(addLabel, x++, y);
            if(x == 3) {
                x=0;
                y++;
            }
            new MenuManagerController().effect((Node) addLabel, (Node)l);
            //Event ch???n m??n.
            addLabel.setOnMouseClicked(mouseEvent -> {
                //T???o c??i label t??n bill.
                AnchorPane a = new AnchorPane();
                a.setPrefWidth(240);
                Label billNameLabel = new Label("Bill "+bills.autoBillId());
                billNameLabel.setPrefWidth(240);
                billNameLabel.setFont(Font.font("Lucida Handwriting",18));
                billNameLabel.setAlignment(Pos.CENTER);
                billNameLabel.setStyle("-fx-cursor: hand; -fx-background-color: #ff6699;-fx-background-radius: 3");
                a.getChildren().add(billNameLabel);
                //N???u l?? m??n ?????u ti??n th?? m???i th??m t??n bill.
                if(bill.orderedItem.isEmpty())
                    v.getChildren().add(a);
                //Event nh???n ?????i t??n bill
                billNameLabel.setOnMouseClicked(mouseEvent1 -> {
                    String billNameTemp = new CreateMessBox().popupBoxGetText("Change Name Bill", "Enter new bill Name");//
                    if(checkInput.checkMaxStringLength(billNameTemp,20)){//test xem c??n j n???a
                        popup.popupBoxMess("Billname must be shorter than 20 characters!",2);
                    }else {
                        billName = billNameTemp;
                        billNameLabel.setText(billName.isEmpty()? "Bill "+bills.autoBillId():billName);
                    }



                });
                //N???u n?? l?? m??n ???? c?? trong bill r???i th?? ch??? t??ng s??? l?????ng.
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
                    //N???u n?? ch??a ??c th??m th?? th??m m???i.
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

        //Ch??? nh???p note(m??n h??nh t???o bill)
        TextField textFieldNote = new TextField();
        textFieldNote.setPromptText("Note");
        textFieldNote.setLayoutY(25);
        textFieldNote.setLayoutX(55);
        textFieldNote.setPrefWidth(185);
        //Event l???y note
        textFieldNote.setOnKeyReleased(keyEvent -> {
            bill.setNote(product,textFieldNote.getText());
        });

        //Ch??? hi???n th??? note (m??n h??nh danh s??ch bill)
        Label noteLabel = new Label();
        noteLabel.setText(getNote(bill,product));
        noteLabel.setLayoutY(25);
        noteLabel.setLayoutX(60);
        noteLabel.setPrefWidth(180);

        //Label gi??.
        Label priceLabel = new Label(product.getPrice()+"vnd");
        priceLabel.setFont(new Font("Forte",12));
        priceLabel.setTextFill(Color.BLACK);
        priceLabel.setAlignment(Pos.CENTER);
        priceLabel.setLayoutY(55);
        priceLabel.setLayoutX(60);

        //N??t + - s??? l?????ng.
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

        //Danh s??ch ch??? s??? s???n ph???m ????? l???y index s???a s??? l?????ng.
        Label index = new Label(bill.orderedItem.get(product)+"");
        index.setFont(Font.font("Century",13));
        index.setAlignment(Pos.CENTER);
        index.setPrefHeight(25);
        index.setPrefWidth(25);
        index.setLayoutY(60);
        index.setLayoutX(170);
        //N???u h??a ????n ???? thanh to??n th?? ko c?? n??t + - s??? l?????ng.
        if(bill.isPaid()){
            a.getChildren().addAll(imageView,l,priceLabel,index);
        }else {
            a.getChildren().addAll(imageView,l,priceLabel,btn1,btn2,index);
        }
        //Note s??? hi???n th??? kh??c nhau ??? 2 m??n h??nh DS bill v?? t???o bill.
        if(whatScene == 1){
            a.getChildren().add(noteLabel);
        }else {
            a.getChildren().add(textFieldNote);
        }
        //event nh???n n??t + s??? l?????ng
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
        //event nh???n n??t tr??? s??? l?????ng
        btn2.setOnMouseClicked(mouseEvent -> {
            System.out.println("btn-");
            //Tr??? b???t or x??a
            if(bill.delItemInBill(product)){
                index.setText((bill.orderedItem.get(product))+"");
            }else{
                //N???u h???t s???n ph???m lu??n th?? x??a c??? t??n bill
                if(bill.orderedItem.isEmpty())
                    v.getChildren().remove(0);
                //H???t s???n ph???m n??o th?? x??a ra kh???i bill
                v.getChildren().remove(a);
            }
            bill.setTotalPaid(bill.calculateTotal());
            System.out.println(bill.getTotalPaid());
            //L??u l???i
            try {
                bills.writeData();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //T??y ??? m??n h??nh n??o th?? x??? l?? theo m??n h??nh ????
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

        //Event nh???n bill
        a.setOnMouseClicked(mouseEvent -> {
            //T???o c??i n??t b???m
            billDetailPane.setBottom(createPaidBillTotalLabel("PAID",bill));
            ScrollPane s = new ScrollPane();
            billDetailPane.setCenter(s);
            //T???o c??i Vbox ????? ch???a m???y c??i s???n ph???m c?? trong bill
            VBox v = new VBox();
            v.setPadding(new Insets(10,10,10,10));
            v.setSpacing(10);
            //T???o c??i label t??n bill l??c in ra.
            AnchorPane billNameAnPane = new AnchorPane();
            billNameAnPane.setPrefWidth(240);
            Label billNameLabel = new Label(bill.getBillName());
            billNameLabel.setPrefWidth(240);
            billNameLabel.setFont(Font.font("Lucida Handwriting",18));
            billNameLabel.setAlignment(Pos.CENTER);
            //billNameLabel.setStyle("-fx-cursor: hand; -fx-background-color: #c172ab;-fx-background-radius: 5");
            billNameAnPane.getChildren().add(billNameLabel);

            //N???u l?? m??n ?????u ti??n th?? m???i th??m t??n bill.
            v.getChildren().add(billNameAnPane);
            //B??? VBox zo c??i pane cu???n chu???t.
            s.setContent(v);
            //?????i m??u n?? khi b??? b???m, c??i n??y fail
            a.setStyle("-fx-cursor: hand; -fx-background-color: #868282; -fx-background-radius: 5");
            for (Product element :bill.orderedItem.keySet()) {
                //T???o s???n ph???m c?? trong bill
                v.getChildren().add(createItemInBill(bill,element,v,1));
            }

        });
        return a;
    }

    private Label createIsPaidLabel(boolean isPaid) {
        Image image = new Image("file:.\\src\\main\\resources\\com\\project\\project_v1\\icon\\paid.png");
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
        //Event x??a bill
        l.setOnMouseClicked(mouseEvent -> {
            if(popup.popupChoose("Are you sure to delete it?")){
                bills.bills.remove(billToDelete);
                try {
                    bills.writeData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                billListClicked();
            }//h c??n g?? ta, b??n menu c??ng c?? x??a
        });
        return l;
    }

    private ImageView createImageView() {
        Image image = new Image("file:.\\src\\main\\resources\\com\\project\\project_v1\\icon\\del.png");
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
        //N???u thanh to??n r???i th?? k hi???n n??t thanh to??n n???a.
        if(!bill.isPaid()){
            a.getChildren().addAll(l,l2,paidBtn);
        }else {
            a.getChildren().addAll(l,l2);
        }


        //Event nh???n CREATE/PAID.
        paidBtn.setOnMouseClicked(mouseEvent -> {
            //N???u n??t n??y l?? n??t create
            if(paidBtn.getText().equals("CREATE")){
                //Th??m c??c thu???c t??nh c???a bill n??y, th?? sau khi ??ng n??y th??m xong
                bill.setBillName(billName.isEmpty()? "Bill "+bills.autoBillId():billName);//C??i n??y n?? b???t ?????u set t??n cho bill r n??
                billName="";
                bill.setBillTimeCreate(new Date());//c?? time trong bill r???i, h ph???i show n?? ra
                bill.setID(bills.autoBillId());
                bill.setTotalPaid(bill.calculateTotal());
                System.out.println(bill.getTotalPaid());
                //th??m c??i bill n??y v??o ds bill
                bills.bills.add(bill);
                //L??u l???i xong v??? c???a s??? bill list.
                try {
                    bills.writeData();
                    //createBillClicked(menu.menu);
                    billListClicked();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //N???u n??t n??y l?? n??t PAID
            }else {
                bill.setPaid(true);
                bill.setTotalPaid(bill.calculateTotal());
                //L??u l???ch s??? ????? b??o c??o.
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
        //T???o c??i th???i gian label
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
