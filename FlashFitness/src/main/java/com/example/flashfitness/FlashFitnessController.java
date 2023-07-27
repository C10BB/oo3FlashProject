package com.example.flashfitness;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.beans.binding.Bindings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FlashFitnessController implements Initializable {
    private Parent root;
    private Stage stage;
    private Scene scene;

    public void backToMenuClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Menu");
        stage.setScene(scene);
        stage.show();
    }

    //calorie Track part of the code
    public void calorieTrackerButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("CalorieTracker.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Calorie Calculator");
        stage.setScene(scene);
        stage.show();
    }

    ///
    @FXML
    private RadioButton maleButton, femaleButton;
    @FXML
    private Label genderWarn;
    @FXML
    private ToggleGroup genderTog;
    ///
    @FXML
    private TextArea heightInput, weightInput, ageInput;
    @FXML
    private Button submitButton;
    /////
    @FXML
    private RadioButton activity1, activity2, activity3, activity4, activity5;
    @FXML
    private ToggleGroup activityTog;
    ////
    @FXML
    private RadioButton calorieMain, calorieDef, calorieSurp;
    @FXML
    private ToggleGroup calorieTog;
    ////
    @FXML
    private Label displayCalorieIntake;
    /////
    @FXML
    private Label proteinDis, fatsDis, carbsDis;
    @FXML
    private PieChart macrosPie;
    double protein = 0 , fats = 0, carbs=0;

    public void calculateCalorieIntake(ActionEvent event){
        int flagGender = 0;

        if(maleButton.isSelected()){
            System.out.println("Male");
            flagGender = 1;
        } else if (femaleButton.isSelected()) {
            System.out.println("Female");
            flagGender = 2;
        } else{
            genderWarn.setText("Only Choose one option");
        }

        ////
        int height, weight, age;
        double bmr = 0;

        height = Integer.parseInt(heightInput.getText());
        System.out.println(height);

        weight = Integer.parseInt(weightInput.getText());
        System.out.println(weight);

        age = Integer.parseInt(ageInput.getText());
        System.out.println(age);

        //calculate Base Metabolic Rate (BMR)
        if(flagGender == 1){//if male
            bmr = (10*weight) + (6.25*height) + (5*age) + 5;
            System.out.println(bmr);
        } else if (flagGender == 2) {//if female
            bmr = (10*weight) + (6.25*height) + (5*age) -161;
            System.out.println(bmr);
        }
        ////
        double calorieIntake = 0;

        if(activity1.isSelected()){
            calorieIntake = bmr*1.2;
            System.out.println(calorieIntake);
        } else if (activity2.isSelected()) {
            calorieIntake = bmr * 1.375;
            System.out.println(calorieIntake);
        } else if (activity3.isSelected()) {
            calorieIntake = bmr * 1.55;
            System.out.println(calorieIntake);
        } else if (activity4.isSelected()) {
            calorieIntake = bmr * 1.725;
            System.out.println(calorieIntake);
        } else if (activity5.isSelected()) {
            calorieIntake= bmr * 1.9;
            System.out.println(calorieIntake);
        }else {
            System.out.println("Choose One Option");
        }
        ///////
        double calorieIntakeFinal = 0;
        if(calorieDef.isSelected()){
            calorieIntakeFinal = calorieIntake - 500;
            System.out.println(calorieIntakeFinal);
        } else if (calorieSurp.isSelected()) {
            calorieIntakeFinal = calorieIntake + 300;
            System.out.println(calorieIntakeFinal);
        } else if (calorieMain.isSelected()){
            calorieIntakeFinal = calorieIntake;
            System.out.println(calorieIntakeFinal);
        }

        displayCalorieIntake.setText(String.valueOf(String.format("%1g%n",calorieIntakeFinal)));
        //////


        protein = calorieIntakeFinal*(.3/4);
        fats = calorieIntakeFinal*(.3/4);
        carbs = calorieIntakeFinal*(.4/4);

        proteinDis.setText(String.valueOf(String.format("%1g%n",protein)));
        fatsDis.setText(String.valueOf(String.format("%1g%n",fats)));
        carbsDis.setText(String.valueOf(String.format("%1g%n",carbs)));

       updatePieChart();

    }
    private ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
    private void updatePieChart() {
        // Clear previous data
        if (pieData != null)
            pieData.clear();
        else
            pieData = FXCollections.observableArrayList();

        int proteinInt = (int) Math.round(protein);
        int fatsInt = (int) Math.round(fats);
        int carbsInt = (int) Math.round(carbs);

        // Add new data to the PieChart
        pieData.addAll(
                new PieChart.Data("Protein", proteinInt),
                new PieChart.Data("Fats", fatsInt),
                new PieChart.Data("Carbs", carbsInt));

        pieData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " amount: ", data.pieValueProperty()
                        )
                )
        );
        // Initialize the PieChart with data
        macrosPie.setData(pieData);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pieData = FXCollections.observableArrayList();
    }



    public void exDatabaseButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ExercDatabase.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Exercise Database");
        stage.setScene(scene);
        stage.show();
    }

    @FXML BorderPane exPage;
    @FXML AnchorPane mainPage;

    @FXML private void home(MouseEvent event){exPage.setCenter(mainPage);}
    @FXML private void chestPageClick(ActionEvent event){loadPage("chestPage.fxml");}
    @FXML private void backPageClick(MouseEvent event){loadPage("backPage.fxml");}
    @FXML private void armsPageClick(MouseEvent event){loadPage("armPage.fxml");}
    @FXML private void absPageClick(MouseEvent event){loadPage("absPage.fxml");}
    @FXML private void legsPageClick(MouseEvent event){loadPage("legsPage.fxml"); }//legPics.setImage(legsP);
    private void loadPage(String page) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(page));
        } catch (IOException ex) {
            Logger.getLogger(FlashFitnessController.class.getName()).log(Level.SEVERE, null, ex);

        }
        exPage.setCenter(root);
    }

    @FXML
    private Button exitButton;
    @FXML
    private AnchorPane mainMenu;


    public void exit(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to exit!");
        alert.setContentText("are you sure you want to exit?");

        if(alert.showAndWait().get() == ButtonType.OK){
            stage = (Stage) mainMenu.getScene().getWindow();
            System.out.println("You successfully logged out!");
            stage.close();
        }

    }

}







