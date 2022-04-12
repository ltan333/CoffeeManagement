package com.project.project_v1;


public class test  {
    public static void main(String[] args) {
        System.out.println(new InputValidation().getHour("12/4/2022 18:32"));
        System.out.println(new InputValidation().getDay("12/4/2022 18:32"));
        System.out.println(new InputValidation().getMonth("12/04/2022 18:32"));
        System.out.println(new InputValidation().getYear("12/04/2022 18:32"));

    }
}
