package com.project.project_v1;

import javafx.scene.control.Label;

import java.io.*;
import java.util.ArrayList;

public class Menu {
    private String filePath = ".\\src\\main\\java\\com\\project\\project_v1\\data\\menu.txt";
    public ArrayList<Product> menu = new ArrayList<>();
    public ArrayList<Label> labelList = new ArrayList<>();

    public void writeData() throws IOException {
        File file = new File(filePath);
        if(!file.exists()) file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        if(menu.size() == 0) return;
        for (Product element : menu) {
            os.writeObject(element);
            os.flush();
        }
        os.close();
        fos.close();
    }
    public void loadData() throws IOException, ClassNotFoundException {
        File file = new File(filePath);
        if(!file.exists()) file.createNewFile();
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream os = new ObjectInputStream(fis);
        menu.clear();
        while (fis.available()>0) {
            Product p = (Product) os.readObject();
            menu.add(p);
        }
        os.close();
        fis.close();
    }

    public void addItem(Product p) {
        menu.add(p);//hàm này ko sửa đc đúng hong
    }
}
