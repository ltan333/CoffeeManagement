package com.project.project_v1;


import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Account {
    private String account;
    private String pass;
    private String name=" ";
    private String meow=" ";
    private String phoneNumber=" ";
    private String filePath = ".\\src\\main\\java\\com\\project\\project_v1\\data\\data.txt";

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeow() {
        return meow;
    }

    public void setMeow(String meow) {
        this.meow = meow;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAccount() {
        return account;
    }

    public String getPass() {
        return pass;
    }

    public boolean verifyPass(String pass) throws IOException, NoSuchAlgorithmException {
        String passHash = this.readFile(filePath)[0];

        String nowPass = this.hashMD5(pass);
        return passHash.toUpperCase().equals(nowPass.toUpperCase());
    }

    public String[] readFile(String filePath) throws IOException {
        File file = new File(filePath);
        if(!file.exists()) file.createNewFile();
        InputStream inputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String[] info = {" "," "," "," "};
        String getChar ="";
        int character;
        int i = 0;
        while ((character = reader.read()) != -1){
            if((char)character != '+'){
                getChar+=(char)character+"";
            }else {
                info[i++]=getChar;
                getChar ="";
            }
        }
        this.setPass(info[0]);
        if(info[1].isEmpty())
            this.setName("Full name");
        else
            this.setName(info[1]);
        this.setMeow(info[2]);
        this.setPhoneNumber(info[3]);
        return info;
    }
     public void writeFile(String filePath, String pass) throws IOException {
        File file = new File(filePath);
         if(!file.exists())file.createNewFile();
         OutputStream os = new FileOutputStream(file);
         OutputStreamWriter writer = new OutputStreamWriter(os);
         this.setPass(pass);
         writer.write(pass);
         writer.write('+');
         writer.write(this.getName());
         writer.write('+');
         writer.write(this.getMeow());
         writer.write('+');
         writer.write(this.getPhoneNumber());
         writer.write('+');

         writer.flush();
         writer.close();
         os.close();
     }
    private String hashMD5(String pass) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] md5sum = md.digest(pass.getBytes());
        String output = String.format("%032X", new BigInteger(1, md5sum));
        return output;
    }

    public boolean changePass(String oldPass, String newPass) throws IOException, NoSuchAlgorithmException {
        if (!hashMD5(oldPass).toUpperCase().equalsIgnoreCase(readFile(filePath)[0])) {
            return false;
        }
        writeFile(filePath,hashMD5(newPass));
        return true;
    }
}
