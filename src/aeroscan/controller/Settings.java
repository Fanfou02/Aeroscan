package aeroscan.controller;

import aeroscan.Main;
import aeroscan.utils.CustomCellPhotos;
import aeroscan.utils.CustomListCellPhotos;
import com.jfoenix.controls.*;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

import org.controlsfx.dialog.ExceptionDialog;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;
import org.controlsfx.dialog.LoginDialog;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;

public class Settings implements Initializable{
    // Reference to the main application.
    private Main mainApp;
    private boolean newProject;

    @FXML
    private Label titleLabel;
    @FXML
    private Label algorithmLabel;
    @FXML
    private Label severityLabel;
    @FXML
    private JFXTextField nameTextField;
    @FXML
    private JFXTextField placeTextField;
    @FXML
    private JFXTextField epochsTextField;
    @FXML
    private JFXRadioButton knnRadioButton;
    @FXML
    private JFXRadioButton svmRadioButton;
    @FXML
    private JFXRadioButton aeRadioButton;
    @FXML
    private JFXSlider severitySlider;
    @FXML
    private JFXButton saveButton;

    @FXML
    private AnchorPane rootLayout;
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public Settings(boolean newProject, Main mainApp){
        this.newProject = newProject;
        this.mainApp = mainApp;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final ToggleGroup group = new ToggleGroup();
        knnRadioButton.setSelected(true);
        knnRadioButton.setToggleGroup(group);
        svmRadioButton.setToggleGroup(group);
        aeRadioButton.setToggleGroup(group);

        saveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Create the project's directories
                File dir = new File("src/data/" + nameTextField.getText());

                if (dir.exists()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Erreur lors de la création du projet");

                    // alert.setHeaderText("Results:");
                    alert.setContentText("Un projet comporte déjà le même nom ou vous n'avez pas spécifié de nom valide.");

                    alert.showAndWait();
                } else if(!epochsTextField.getText().matches("\\d*")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Erreur lors de la création du projet");
                    // alert.setHeaderText("Results:");
                    alert.setContentText("Le champs \"Epochs\" doit être un nombre entier.");
                    alert.showAndWait();
                }
                else {

                    /*
                     * To create directory or folder, use mkdir
                     * method of File class.
                     */
                    boolean isDirCreated = dir.mkdir();

                    if (isDirCreated)
                        System.out.println(nameTextField.getText() + " Directory created successfully");
                    else
                        System.out.println("Failed to create directory " + nameTextField.getText());

                    File photosdir = new File("src/data/" + nameTextField.getText() + "/photos");
                    File referencesdir = new File("src/data/" + nameTextField.getText() + "/references");
                    photosdir.mkdir();
                    referencesdir.mkdir();

                    // Default parameters
                    String location = (placeTextField.getText().isEmpty()) ? "Unknown" : placeTextField.getText();
                    int epochs = (epochsTextField.getText().isEmpty()) ? 200 : Integer.parseInt(epochsTextField.getText());
                    String algorithm = aeRadioButton.isSelected()? "AE" : knnRadioButton.isSelected()? "KNN" : "SVM";

                    // Save the configs in a Json
                    JSONObject obj = new JSONObject();
                    obj.put("Name", nameTextField.getText());
                    obj.put("Location", location);
                    obj.put("Epochs", epochs);
                    obj.put("Algorithm", algorithm);
                    obj.put("Severity", severitySlider.getValue());


                    // try-with-resources statement based on post comment below :)
                    try (FileWriter file = new FileWriter("src/data/" + nameTextField.getText() + "/config.json")) {
                        file.write(obj.toJSONString());
                        System.out.println("Successfully Copied JSON Object to File...");
                        System.out.println("\nJSON Object: " + obj);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Change the center layout to Scans page
                    try {
                        // Load scans page
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/aeroscan/view/Aera.fxml"));

                        Aera controller = new Aera(nameTextField.getText(), mainApp);
                        loader.setController(controller);
                        AnchorPane aera = loader.load();
                        // Set scans page into the center of root layout.
                        mainApp.getRootLayout().setCenter(aera);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }
}







