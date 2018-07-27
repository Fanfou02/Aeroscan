package aeroscan.controller;

import aeroscan.Main;
import aeroscan.model.CustomCellScans;
import aeroscan.model.CustomListCellScans;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.icc.IccDirectory;
import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;


public class Scans implements Initializable{
    @FXML
    private ListView listView;
    @FXML
    private BorderPane borderPane;
    private Main mainApp;

    @FXML
    private AnchorPane rootLayout;


    private final String DIRECTORY_PATH = "/data/";

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public Scans(Main mainApp){
        this.mainApp = mainApp;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
        File directory = new File("src/data");


            listView.setCellFactory(
                    param -> new CustomListCellScans()
            );


        for(File f : directory.listFiles()) {
            System.out.println("Loading " + f.getName());
            if (f.isDirectory()) {
                System.out.println();
                Path dir = f.toPath().resolve("references");  // specify your directory

                System.out.println();
                Optional<Path> lastFilePath = Files.list(dir)    // here we get the stream with full directory listing
                        .filter(map -> !Files.isDirectory(map)) // exclude subdirectories from listing
                        .filter(map -> map.toString().endsWith(".png") || map.toString().endsWith(".jpg")) // include only PNG files
                        .max(Comparator.comparingLong(map -> map.toFile().lastModified()));  // finally get the last file using simple comparator by lastModified field

                if ( lastFilePath.isPresent() ) // your folder may be empty
                {
                    String date = "Unknown";
                    try {
                        Metadata metadata = ImageMetadataReader.readMetadata(new File(lastFilePath.get().toUri()));
                        IccDirectory dirPhoto = metadata.getFirstDirectoryOfType(IccDirectory.class);//ExifSubIFDDirectory.class
                        // query the datetime tag's value
                        date = dirPhoto.getDate(IccDirectory.TAG_PROFILE_DATETIME).toString();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    System.out.println(lastFilePath.get().toUri().toURL().toExternalForm());
                    listView.getItems().add(new CustomCellScans(f.getName(), date, new Image(lastFilePath.get().toUri().toURL().toExternalForm())));
                }
                else
                    listView.getItems().add(new CustomCellScans(f.getName(), "N/A"));
            }
        }

//        listView.getItems().add(new CustomCellScans("Add New Area", ""));


        listView.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<CustomCellScans>() {
                    public void changed(ObservableValue<? extends CustomCellScans> observable,
                                        CustomCellScans oldValue, CustomCellScans newValue) {
                        // Change the center layout to Aera page
                        try {
                            // Load scans page
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aeroscan/view/Aera.fxml"));

                            Aera controller = new Aera(newValue.getName(), mainApp);
                            loader.setController(controller);
                            AnchorPane aera = loader.load();
                            // Set scans page into the center of root layout.
                            mainApp.getRootLayout().setCenter(aera);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });


            JFXButton newProject = new JFXButton("Démarrer un nouveau projet");
            newProject.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        // Load scans page
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/aeroscan/view/Settings.fxml"));
                        Settings controller = new Settings(true, mainApp);
                        loader.setController(controller);
                        AnchorPane settings = loader.load();
                        // Set scans page into the center of root layout.
                        mainApp.getRootLayout().setCenter(settings);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            HBox hBox = new HBox();
            HBox.setHgrow(newProject, Priority.ALWAYS);
            newProject.setMaxWidth(Double.MAX_VALUE);
            hBox.getChildren().add(newProject);
            borderPane.setBottom(hBox);



            //rootLayout.getChildren().add(borderPane);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}







