module com.example.flashfitness {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.example.flashfitness to javafx.fxml;
    exports com.example.flashfitness;
}