package aeroscan.controller;

import aeroscan.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import aeroscan.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;
public class ImageShow {
    // Reference to the main application.
    private Main mainApp;

    @FXML
    private ImageView imgView;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ImageShow(){

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
}







