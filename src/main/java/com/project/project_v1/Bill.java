package com.project.project_v1;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Bill implements Serializable {
    private String billName="Unnamed Bill";
    private int ID;
    private boolean isPaid = false;
    private Date billTimeCreate;
    private Double totalPaid;
    public HashMap<Product,Integer> orderedItem = new HashMap<>();
    public HashMap<Product,String> noteList = new HashMap<>();

    public Double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(Double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public Date getBillTimeCreate() {
        return billTimeCreate;
    }



    public void setBillTimeCreate(Date billTimeCreate) {
        this.billTimeCreate = billTimeCreate;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public Bill(String billName, int ID, HashMap<Product, Integer> orderedItem) {
        this.billName = billName;
        this.ID = ID;
        this.orderedItem = orderedItem;
    }

    public Bill() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public boolean addItemInBill(Product p) {
        if(orderedItem.containsKey(p)) {
            orderedItem.put(p,orderedItem.get(p)+1);
            return true;
        }else {
            orderedItem.put(p,1);
            //noteList.put(p,note);
            return false;
        }
    }

    public boolean delItemInBill(Product p) {
        if(orderedItem.containsKey(p) && orderedItem.get(p) > 1){
            orderedItem.put(p,orderedItem.get(p)-1);
            return true;
        }else{
            orderedItem.remove(p);
            noteList.remove(p);
            return false;
        }
    }

    public void setNote(Product product, String note){
        noteList.put(product,note);
    }


    //---------------------------------------------//


    public double calculateTotal(){
        Double total=0.0;
        for(Product i: orderedItem.keySet()){
            total+=(orderedItem.get(i)*i.getPrice());
        }
        return total;
    }


    //----------------------------------------------//
    private VBox creatBillDetail() {
        VBox v = new VBox();

        AnchorPane a = new AnchorPane();
        v.getChildren().addAll(a);
        Label l = new Label("Bill Name");
        a.getChildren().addAll(l);
        return v;
    }


}
