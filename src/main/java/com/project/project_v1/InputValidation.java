package com.project.project_v1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InputValidation {
    public boolean isEmptyString(String inputString) {
        return inputString.isEmpty()? true:false;
    }

    public boolean checkMaxStringLength(String inputString, int max){
        if(inputString.length()>max){
            return true;
        }else
            return false;
    }

    public boolean checkMinStringLength(String inputString, int min) {
        //string it hon min thi true
        if(inputString.length()<min){
            return true;
        }else
            return false;
    }
    public boolean isNumber(String num) {
        try {
            //Nếu chuyển được từ String về Double thì trả về true.
            Double.parseDouble(num);//hàm này là hàm chuyển 1 chuỗi về số Double, chuyển đc thì nó trả về chuỗi đó ở kiểu số, ko đc thì nó bị lỗi
            return true;
            //Còn nếu nó ko parse đc thì nó lỗi nó sẽ văng xuống đây và trả về false./
        } catch (NumberFormatException e) {//Cái lỗi đó nó cũng là 1 class, phải tạo đối tượng cho nó để có thể in ra lỗi ntn
            e.getMessage();//nó sẽ trả về mess của lôi
            return false;
        }
    }

    public static String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date test = new Date();
        return dateFormat.format(test);
    }
    public static String getHour(String dd_MM_yyyy_HH_ss) {
        String[] getMinAndHour = dd_MM_yyyy_HH_ss.split(" ");
        String[] getH = getMinAndHour[1].split(":");
        return getH[0];
    }
    public static String getDay(String dd_MM_yyyy_HH_ss) {
        String[] getMinAndHour = dd_MM_yyyy_HH_ss.split(" ");
        String[] getD = getMinAndHour[0].split("/");
        return getD[0];
    }
    public static String getMonth(String dd_MM_yyyy_HH_ss) {
        String[] getMinAndHour = dd_MM_yyyy_HH_ss.split(" ");
        String[] getM = getMinAndHour[0].split("/");
        return getM[1];
    }
    public static String getYear(String dd_MM_yyyy_HH_ss) {
        String[] getMinAndHour = dd_MM_yyyy_HH_ss.split(" ");
        String[] getY = getMinAndHour[0].split("/");
        return getY[2];
    }

}
