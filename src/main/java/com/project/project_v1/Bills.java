package com.project.project_v1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.OptionalInt;

public class Bills {
    public ArrayList<Bill> bills = new ArrayList<>();
    private String filePath = ".\\src\\main\\java\\com\\project\\project_v1\\data\\bills.txt";

    public void loadData() throws IOException, ClassNotFoundException {
        File file = new File(filePath);
        if(!file.exists()) file.createNewFile();
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream os = new ObjectInputStream(fis);
        bills.clear();
        while (fis.available()>0) {
            Bill p = (Bill) os.readObject();
            bills.add(p);
        }
        os.close();
        fis.close();
    }

    public void writeData() throws IOException {
        File file = new File(filePath);
        if(!file.exists()) file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        if(bills.size() == 0) return;
        for (Bill element : bills) {
            os.writeObject(element);
            os.flush();
        }
        os.close();
        fos.close();
    }

    public int autoBillId() {
        if (!bills.isEmpty()) {
        int[] idList = new int[bills.size()];
        for (int i = 0; i < bills.size(); i++) {
            idList[i] = bills.get(i).getID();
        }
        int maxId = Arrays.stream(idList).max().getAsInt();
        return maxId + 1;
        }else {
            return 1;
        }
    }
}
