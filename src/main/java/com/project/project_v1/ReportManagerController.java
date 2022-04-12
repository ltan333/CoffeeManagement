package com.project.project_v1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class ReportManagerController implements Initializable {

    @FXML
    private Label backBtn;

    @FXML
    private Label perDayBtn;

    @FXML
    private Label perMonthBtn;

    @FXML
    private Label perYearBtn;

    @FXML
    private StackPane stackPane;

    @FXML
    private Label titleLabel;

    Stage stage = new Stage();
    MainController mainController = new MainController();
    InputValidation inputCheck = new InputValidation();
    History history = new History();
    AreaChart chart;
    boolean isPerDayBtnSelected,isPerMonthBtnSelected,isPerYearBtnSelected;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        perDayBtnClicked();
        backBtn.setOnMouseClicked(mouseEvent -> {
            try {
                backBtnClicked();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        perDayBtn.setOnMouseClicked(mouseEvent -> {
            perDayBtnClicked();
        });

        perMonthBtn.setOnMouseClicked(mouseEvent -> {
            perMonthBtnClicked();
        });

        perYearBtn.setOnMouseClicked(mouseEvent -> {
            perYearBtnClicked();
        });

        perDayBtn.setOnMouseEntered(mouseEvent -> {
            if(!isPerDayBtnSelected){
                perDayBtn.setScaleX(1.1);
                perDayBtn.setScaleZ(1.1);
            }
        });
        perDayBtn.setOnMouseExited(mouseEvent -> {
            perDayBtn.setScaleX(1);
            perDayBtn.setScaleZ(1);
        });

        perMonthBtn.setOnMouseEntered(mouseEvent -> {
            if(!isPerMonthBtnSelected) {
                perMonthBtn.setScaleX(1.1);
                perMonthBtn.setScaleZ(1.1);
            }
        });
        perMonthBtn.setOnMouseExited(mouseEvent -> {
            perMonthBtn.setScaleX(1);
            perMonthBtn.setScaleZ(1);
        });

        perYearBtn.setOnMouseEntered(mouseEvent -> {
            if(!isPerYearBtnSelected) {
                perYearBtn.setScaleX(1.1);
                perYearBtn.setScaleZ(1.1);
            }
        });
        perYearBtn.setOnMouseExited(mouseEvent -> {
            perYearBtn.setScaleX(1);
            perYearBtn.setScaleZ(1);
        });

    }

    public void perDayBtnClicked() {
        isPerDayBtnSelected = true;
        isPerMonthBtnSelected = false;
        isPerYearBtnSelected = false;
        perDayBtn.setStyle("-fx-background-color:  #333333;");
        perMonthBtn.setStyle("-fx-background-color:  null;-fx-cursor: hand;");
        perYearBtn.setStyle("-fx-background-color:  null;-fx-cursor: hand;");
        titleLabel.setText("Repory per day");
        stackPane.getChildren().clear();
        stackPane.getChildren().add(dayChart());
    }

    public void perMonthBtnClicked() {
        isPerDayBtnSelected = false;
        isPerMonthBtnSelected = true;
        isPerYearBtnSelected = false;
        perMonthBtn.setStyle("-fx-background-color:  #333333; ");
        perDayBtn.setStyle("-fx-background-color:  null;-fx-cursor: hand;");
        perYearBtn.setStyle("-fx-background-color:  null;-fx-cursor: hand;");
        titleLabel.setText("Repory per month");
        stackPane.getChildren().clear();
        stackPane.getChildren().add(monthChart());
    }

    public void perYearBtnClicked() {
        isPerDayBtnSelected = false;
        isPerMonthBtnSelected = false;
        isPerYearBtnSelected = true;
        perYearBtn.setStyle("-fx-background-color:  #333333; ");
        perMonthBtn.setStyle("-fx-background-color:  null;-fx-cursor: hand;");
        perDayBtn.setStyle("-fx-background-color:  null;-fx-cursor: hand;");
        titleLabel.setText("Repory per year");
        stackPane.getChildren().clear();
        stackPane.getChildren().add(yearChart());
    }


    private XYChart.Series seriDay(String seriName) {
        XYChart.Series seri = new XYChart.Series();
        seri.setName(seriName);
        int currentDay = Calendar.getInstance().get(Calendar.DATE);
        for (int i = 1; i <= currentDay; i++) {
            seri.getData().add(new XYChart.Data(i+"",history.totalInDay(i, 4, 2022)));
        }
        return seri;
    }

    private XYChart.Series seriMonth(String seriName) {
        XYChart.Series seri = new XYChart.Series();
        seri.setName(seriName);
        int currentMonth = Integer.parseInt(InputValidation.getMonth(InputValidation.getCurrentDate()));
        for (int i = 1; i <= currentMonth; i++) {
            seri.getData().add(new XYChart.Data(i+"",history.totalInMonth(i,Integer.parseInt(InputValidation.getYear(InputValidation.getCurrentDate())))));
        }
        return seri;
    }

    private XYChart.Series seriYear(String seriName) {
        XYChart.Series seri = new XYChart.Series();
        seri.setName(seriName);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear-5; i <= currentYear; i++) {
            seri.getData().add(new XYChart.Data(i+"",history.totalInYear(i)));
        }
        return seri;
    }





    private AreaChart yearChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Years");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Total Revenue (VND)");
        chart = new AreaChart(xAxis,yAxis);
        chart.getData().add(seriYear("Total Revenue In Every Year"));
        return chart;
    }
    private AreaChart monthChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Months");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Total Revenue (VND)");
        chart = new AreaChart(xAxis,yAxis);
        chart.getData().add(seriMonth("Total Revenue In Every Month"));
        return chart;
    }

    private AreaChart dayChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Days");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Total Revenue (VND)");
        chart = new AreaChart(xAxis,yAxis);
        chart.getData().add(seriDay("Total Revenue In Every Day"));
        return chart;
    }

    private void backBtnClicked() throws IOException {
        stage = (Stage)backBtn.getScene().getWindow();
        stage.close();
        mainController.createMainStage();
    }


    public void createReportStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ReportManagerScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = new Stage();
        stage.setTitle("Coffee Shop Management");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
