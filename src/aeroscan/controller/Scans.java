package aeroscan.controller;

import aeroscan.Main;
import aeroscan.utils.CustomCellScans;
import aeroscan.utils.CustomListCellScans;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderRepeat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Optional;


public class Scans {

    @FXML
    private ListView listView;

    private BorderPane rootLayout;


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

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    // Change the center layout to Scans page
                    try {
                        // Load scans page
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/aeroscan/view/Aera.fxml"));
                        AnchorPane aera = loader.load();

                        // Set scans page into the center of root layout.
                        rootLayout.setCenter(aera);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param rootLayout
     */
    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }
}







