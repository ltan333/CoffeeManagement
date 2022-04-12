package com.project.project_v1;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class History {

    private String filePath = ".\\src\\main\\java\\com\\project\\project_v1\\data\\history.txt";

    HashMap<Integer,HashMap<Integer,HashMap<Integer,HashMap<Integer,ArrayList<Bill>>>>> caiqqjdaykbiet = new HashMap<>();


    public Double totalInDay(int inDay, int inMonth, int inYear) {
        Double total = 0.0;
        String date="";
        if(inDay<10)
            date+="0"+inDay;
        else
            date+=inDay;
        if (inMonth<10)
            date+="/0"+inMonth;
        else
            date+="/"+inMonth;
        date+="/"+inYear;

        File file = new File(filePath);
        FileInputStream fis;
        ObjectInputStream ois;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null){
                String[] splited = line.split("\\+");
                if(splited[0].split(" ")[0].equals(date))
                    total+=Double.parseDouble(splited[1]);
                line = br.readLine();
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total;
    }

    public Double totalInMonth( int inMonth, int inYear) {
        Double total = 0.0;
        String date="";
        if (inMonth<10)
            date+="0"+inMonth;
        else
            date+=inMonth;
        date+="/"+inYear;

        File file = new File(filePath);
        FileInputStream fis;
        ObjectInputStream ois;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null){
                String[] splited = line.split("\\+");
                if((InputValidation.getMonth(splited[0])+"/"+InputValidation.getYear(splited[0])).equals(date))
                    total+=Double.parseDouble(splited[1]);
                line = br.readLine();
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total;
    }

    public Double totalInYear(int inYear) {
        Double total = 0.0;
        String date="";
        date+=inYear;

        File file = new File(filePath);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null){
                String[] splited = line.split("\\+");
                if(InputValidation.getYear(splited[0]).equals(date))
                    total+=Double.parseDouble(splited[1]);
                line = br.readLine();
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total;
    }

//    public boolean writeFile(Bill bill) {
//        File file = new File(filePath);
//        try {
//            if(!file.exists()){
//                file.createNewFile();
//            }
//            FileOutputStream fos = new FileOutputStream(file,true);
//            ObjectOutputStream oos = new ObjectOutputStream(fos){
//                @Override
//                protected void writeStreamHeader() throws IOException {
//                    reset();
//                }
//            };
//
//            oos.writeObject(bill);
//            oos.flush();
//            oos.close();
//            fos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }

    public void writeFile(Bill bill) {
        File file = new File(filePath);
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(bill.getBillTimeCreate())+"+");
            bw.write(bill.getTotalPaid()+"\n");
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
