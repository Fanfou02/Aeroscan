package aeroscan.controller;

import aeroscan.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class RootLayout {


    // Reference to the main application.
    private Main mainApp;

    @FXML
    private ImageView scansButtonImage;

    @FXML
    private ImageView algorithmsButtonImage;

    @FXML
    private ImageView flightPlannerButtonImage;

    @FXML
    private ImageView helpButtonImage;

    @FXML
    private BorderPane rootLayout;

    @FXML
    private Button scansButton;

    @FXML
    private Button algorithmsButton;

    @FXML
    private Button flightPlannerButton;

    @FXML
    private Button helpButton;



    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public RootLayout(){

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        onScansClicked();
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param rootLayout
     */
    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    @FXML
    private void onScansClicked(){
        // Set the image of the scan button blue and unset the others
        scansButtonImage.setImage(new Image("aeroscan/resources/icons/map_blue.png"));
        algorithmsButtonImage.setImage(new Image("aeroscan/resources/icons/algorithm.png"));
        flightPlannerButtonImage.setImage(new Image("aeroscan/resources/icons/flight.png"));
        helpButtonImage.setImage(new Image("aeroscan/resources/icons/help.png"));

        //Set the text color of the algorithm button blue and unset the others
        scansButton.getStyleClass().add("buttonSelected");
        algorithmsButton.getStyleClass().removeAll("buttonSelected");
        flightPlannerButton.getStyleClass().removeAll("buttonSelected");
        helpButton.getStyleClass().removeAll("buttonSelected");

        // Change the center layout to Scans page
        try {
            // Load scans page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aeroscan/view/Scans.fxml"));
            AnchorPane scans = loader.load();

            // Set scans page into the center of root layout.
            rootLayout.setCenter(scans);

            // Give the controller access to the main app.
            Scans controller = loader.getController();
            controller.setMainApp(this.mainApp);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onAlgorithmsClicked(){
        // Set the image of the algorithms button blue and unset the others
        scansButtonImage.setImage(new Image("aeroscan/resources/icons/map.png"));
        algorithmsButtonImage.setImage(new Image("aeroscan/resources/icons/algorithm_blue.png"));
        flightPlannerButtonImage.setImage(new Image("aeroscan/resources/icons/flight.png"));
        helpButtonImage.setImage(new Image("aeroscan/resources/icons/help.png"));

        //Set the text color of the algorithm button blue and unset the others
        scansButton.getStyleClass().removeAll("buttonSelected");
        algorithmsButton.getStyleClass().add("buttonSelected");
        flightPlannerButton.getStyleClass().removeAll("buttonSelected");
        helpButton.getStyleClass().removeAll("buttonSelected");

        // Change the center layout to Scans page
        try {
            // Load algorithms page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aeroscan/view/Algorithms.fxml"));
            AnchorPane scans = loader.load();

            // Set algorithms page into the center of root layout.
            rootLayout.setCenter(scans);

            // Give the controller access to the main app.
            Algorithms controller = loader.getController();
            controller.setMainApp(this.mainApp);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onFlightPlannerClicked(){
        // Set the image of the FlightPlanner button blue and unset the others
        scansButtonImage.setImage(new Image("aeroscan/resources/icons/map.png"));
        algorithmsButtonImage.setImage(new Image("aeroscan/resources/icons/algorithm.png"));
        flightPlannerButtonImage.setImage(new Image("aeroscan/resources/icons/flight_blue.png"));
        helpButtonImage.setImage(new Image("aeroscan/resources/icons/help.png"));

        //Set the text color of the algorithm button blue and unset the others
        scansButton.getStyleClass().removeAll("buttonSelected");
        algorithmsButton.getStyleClass().removeAll("buttonSelected");
        flightPlannerButton.getStyleClass().add("buttonSelected");
        helpButton.getStyleClass().removeAll("buttonSelected");

        // Change the center layout to FlightPlanner page
        try {
            // Load FlightPlanner page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aeroscan/view/FlightPlanner.fxml"));
            AnchorPane flights = loader.load();

            // Set FlightPlanner page into the center of root layout.
            rootLayout.setCenter(flights);

            // Give the controller access to the main app.
            FlightPlanner controller = loader.getController();
            controller.setMainApp(this.mainApp);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onHelpClicked(){
        // Set the image of the Help button blue and unset the others
        scansButtonImage.setImage(new Image("aeroscan/resources/icons/map.png"));
        algorithmsButtonImage.setImage(new Image("aeroscan/resources/icons/algorithm.png"));
        flightPlannerButtonImage.setImage(new Image("aeroscan/resources/icons/flight.png"));
        helpButtonImage.setImage(new Image("aeroscan/resources/icons/help_blue.png"));

        //Set the text color of the algorithm button blue and unset the others
        scansButton.getStyleClass().removeAll("buttonSelected");
        algorithmsButton.getStyleClass().removeAll("buttonSelected");
        flightPlannerButton.getStyleClass().removeAll("buttonSelected");
        helpButton.getStyleClass().add("buttonSelected");

        // Change the center layout to Help page
        try {
            // Load Help page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aeroscan/view/Help.fxml"));
            AnchorPane help = loader.load();

            // Set Help page into the center of root layout.
            rootLayout.setCenter(help);

            // Give the controller access to the main app.
            Help controller = loader.getController();
            controller.setMainApp(this.mainApp);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}