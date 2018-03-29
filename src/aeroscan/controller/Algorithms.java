package aeroscan.controller;

import aeroscan.Main;
import aeroscan.utils.CustomCellAlgorithms;
import aeroscan.utils.CustomCellScans;
import aeroscan.utils.CustomListCellAlgorithms;
import aeroscan.utils.CustomListCellScans;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;

import java.io.File;


public class Algorithms {
    // Reference to the main application.
    private Main mainApp;

    @FXML
    private ListView listView;

    private final String DIRECTORY_PATH = "/data/";

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public Algorithms(){

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        try {
            File directory = new File("src/data");


            listView.setCellFactory(
                    param -> new CustomListCellAlgorithms()
            );


            for(File f : directory.listFiles()) {
                System.out.println("Loading " + f.getName());
                if (f.isDirectory()) {

                    listView.getItems().add(new CustomCellAlgorithms("Anomaly Detection \nAlgorithm", "This algorithm allow user\n to detect all sort of ano-\nmaly depending on a 3 \ndays training dataset", new Image("aeroscan/resources/icons/anomaly-algorithm.png")));
                }
            }

            listView.getItems().add(new CustomCellAlgorithms("Add New Area", ""));


        } catch (Exception e){
            e.printStackTrace();
        }
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







