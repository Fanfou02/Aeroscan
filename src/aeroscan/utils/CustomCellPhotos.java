package aeroscan.utils;
import aeroscan.Main;
import aeroscan.controller.Aera;
import aeroscan.controller.Scans;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.icc.IccDirectory;
import com.jfoenix.controls.JFXButton;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.dialog.ExceptionDialog;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class CustomCellPhotos {
    private final String DEFAULT_ICON_PATHNAME="aeroscan/resources/icons/document-empty.png";
    private final String NOT_PREDICTED = "Pas encore analyser", ANOMALY = "Anomalie détecté", NOT_ANOMALY = "Pas d'anomalies";
    private String name;
    private String date;
    private String statut;
    private String currentDirectory;
    private File file;
    private Image icon;
    private JFXButton deleteButton = new JFXButton("Supprimer");
    private JFXButton trainButton = new JFXButton("Analyser");
    private JFXButton showButton = new JFXButton("Afficher");
    private Main mainApp;

    public CustomCellPhotos(String name, File file, Main mainApp, String directory){
        this.currentDirectory = directory;
        this.name=name;
        this.date=date;
        this.file = file;
        this.statut= file.getName().endsWith("_A.jpg")? ANOMALY : file.getName().endsWith("_NA.jpg") ? NOT_ANOMALY : NOT_PREDICTED;
        try {
            this.icon = new Image(this.file.toURI().toURL().toExternalForm());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        this.mainApp = mainApp;

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            IccDirectory dir = metadata.getFirstDirectoryOfType(IccDirectory.class);//ExifSubIFDDirectory.class
            // query the datetime tag's value
            this.date = dir.getDate(IccDirectory.TAG_PROFILE_DATETIME).toString();
        } catch (Exception e){
            e.printStackTrace();
        }

        configureButtons();
    }

    private void configureButtons() {

        showButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage dialog = new Stage();
                dialog.setWidth(800);
                dialog.setHeight(600);
                dialog.setTitle(name);

                final ImageView selectedImage = new ImageView();
                try {
                    Image image1 = new Image(file.toURI().toURL().toExternalForm());
                    selectedImage.setImage(image1);
                    selectedImage.setFitHeight(dialog.getHeight());
                    selectedImage.setFitWidth(dialog.getWidth());
                } catch (Exception e){
                    System.err.println("Error during photo retrieval : ");
                    e.printStackTrace();
                }

                ScrollPane scrollPane = new ScrollPane();
                final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);

                zoomProperty.addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable arg0) {
                        selectedImage.setFitWidth(zoomProperty.get() * 4);
                        selectedImage.setFitHeight(zoomProperty.get() * 3);
                    }
                });

                scrollPane.addEventFilter(ZoomEvent.ZOOM, new EventHandler<ZoomEvent>() {
                    @Override
                    public void handle(ZoomEvent event) {
                        zoomProperty.set(zoomProperty.get() * event.getZoomFactor());
                    }
                });

                scrollPane.setContent(selectedImage);

                dialog.setScene(
                        new Scene(scrollPane)
                );

                dialog.initOwner(mainApp.getPrimaryStage());
                dialog.initModality(Modality.WINDOW_MODAL);
                dialog.showAndWait();
            }
        });

        trainButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });
        deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try{
                    if (file.exists())
                        System.out.println("File Exist");
                    else
                        System.out.println("File dont Exist");
                    if(file.delete())
                        System.out.println("file deleted");
                    else
                        System.out.println("file not deleted");
                    // Change the center layout to Scans page

                    // Load scans page
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/aeroscan/view/Aera.fxml"));

                    Aera controller = new Aera(currentDirectory, mainApp);
                    loader.setController(controller);
                    AnchorPane aera = loader.load();
                    // Set scans page into the center of root layout.
                    mainApp.getRootLayout().setCenter(aera);

                }catch(Exception e){

                    e.printStackTrace();

                }
            }
        });
    }
    public void setIcon(Image icon){
        this.icon = icon;
    }

    public String getName(){
        return name;
    }

    public String getDate(){
        return date;
    }

    public String getStatut(){
        return statut;
    }

    public Image getIcon(){
        return icon;
    }


    public JFXButton getDeleteButton() {
        return deleteButton;
    }

    public JFXButton getShowButton() {
        return showButton;
    }

    public JFXButton getTrainButton() {
        return trainButton;
    }
}
