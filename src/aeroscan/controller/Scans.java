package aeroscan.controller;

import aeroscan.Main;
import aeroscan.utils.CustomCellScans;
import aeroscan.utils.CustomListCellScans;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Optional;


public class Scans {
    // Reference to the main application.
    private Main mainApp;

    @FXML
    private ListView listView;

    private final String DIRECTORY_PATH = "/data/";

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public Scans(){

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
                    param -> new CustomListCellScans()
            );


        for(File f : directory.listFiles()) {
            System.out.println("Loading " + f.getName());
            if (f.isDirectory()) {
                System.out.println();
                Path dir = f.toPath().resolve("maps");  // specify your directory

                System.out.println();
                Optional<Path> lastFilePath = Files.list(dir)    // here we get the stream with full directory listing
                        .filter(map -> !Files.isDirectory(map)) // exclude subdirectories from listing
                        .filter(map -> map.toString().endsWith(".png")) // include only PNG files
                        .max(Comparator.comparingLong(map -> map.toFile().lastModified()));  // finally get the last file using simple comparator by lastModified field

                if ( lastFilePath.isPresent() ) // your folder may be empty
                {
                    System.out.println(lastFilePath.get().toUri().toURL().toExternalForm());
                    listView.getItems().add(new CustomCellScans(f.getName(), "02.02.2018", new Image(lastFilePath.get().toUri().toURL().toExternalForm())));
                }
                else
                    listView.getItems().add(new CustomCellScans(f.getName(), "02.02.2018"));
            }
        }

        listView.getItems().add(new CustomCellScans("Add New Area", ""));


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







