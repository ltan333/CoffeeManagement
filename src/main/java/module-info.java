module com.project.project_v1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.project.project_v1 to javafx.fxml;
    exports com.project.project_v1;
}