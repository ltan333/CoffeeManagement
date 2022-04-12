package com.project.project_v1;

import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private double price;
    private String urlImage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Product() {
    }

    public Product(String name, double price, String urlImage) {
        this.name = name;
        this.price = price;
        this.urlImage = urlImage;
    }

    public Label createItem() {
        Label l = new Label();
        l.setPrefSize(155,140);
        l.setText(this.getName());
        l.setAlignment(Pos.CENTER);
        l.setContentDisplay(ContentDisplay.TOP);
        l.setWrapText(true);
        ImageView imageView = createImageView();
        l.setGraphic(imageView);
        return l;
    }


    private ImageView createImageView() {
        Image image = new Image(urlImage);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(132);
        imageView.setFitWidth(155);
        return imageView;
    }
}
