package aeroscan.controller;

import aeroscan.Main;
import aeroscan.model.CustomCellPhotos;
import aeroscan.model.CustomListCellPhotos;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;

import java.net.URL;
import java.util.ResourceBundle;

public class Aera implements Initializable{
    // Reference to the main application.
    private Main mainApp;
    private String currentDirectory;
    private JFXTabPane tabPane = new JFXTabPane();
    private Tab photosTab = new Tab();
    private Tab referencesTab = new Tab();

    @FXML
    private AnchorPane rootLayout;
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public Aera(String currentDirectory, Main mainApp){
        this.currentDirectory = currentDirectory;
        this.mainApp = mainApp;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        photosTab.setText("Photos");
        ListView listViewPhotos = new ListView();
        try {
            System.out.println("src/data/" + this.currentDirectory);

            File directory = new File("src/data/" + this.currentDirectory + "/photos");


            listViewPhotos.setCellFactory(
                    param -> new CustomListCellPhotos()
            );


            for(File f : directory.listFiles()) {
                System.out.println("Loading " + f.getName());
                if (f.getName().endsWith("png") || f.getName().endsWith("jpg")) {
                    //System.out.println();
                    //Path dir = f.toPath().resolve("references");  // specify your directory
                    //System.out.println(f.getPath());
                    listViewPhotos.getItems().add(new CustomCellPhotos(f.getName(), f, mainApp, currentDirectory));
                    System.out.println(f.toURI().toURL().toExternalForm());

                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        photosTab.setContent(listViewPhotos);

        referencesTab.setText("Références");
        ListView listViewReferences = new ListView();
        try {
            System.out.println("src/data/" + this.currentDirectory);
            File directory = new File("src/data/" + this.currentDirectory + "/references");


            listViewReferences.setCellFactory(
                    param -> new CustomListCellPhotos()
            );


            for(File f : directory.listFiles()) {
                System.out.println("Loading " + f.getName());
                if (f.getName().endsWith("png") || f.getName().endsWith("jpg")) {
                    System.out.println();
                    //Path dir = f.toPath().resolve("references");  // specify your directory
                    System.out.println("src/data/" + this.currentDirectory + "/photos " + f.getPath());
                    listViewReferences.getItems().add(new CustomCellPhotos(f.getName(), f, mainApp, currentDirectory));

                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        referencesTab.setContent(listViewReferences);



        tabPane.getTabs().add(photosTab);
        tabPane.getTabs().add(referencesTab);
        final FileChooser fileChooser = new FileChooser();
        JFXButton AddPhotoButton = new JFXButton("Ajouter une photo");
        AddPhotoButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {


                        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
                if (file != null) {
                    try {
                        String tab = tabPane.getSelectionModel().getSelectedItem() == photosTab ? "photos":"references";
                        File dest = new File("src/data/" + currentDirectory + "/"+tab+"/" + file.getName());
                        Files.copy(file.toPath(), dest.toPath());
                        System.out.println("Copied " + file.toPath() + " in " + dest.toPath());

                        // reload page
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/aeroscan/view/Aera.fxml"));

                        Aera controller = new Aera(currentDirectory, mainApp);
                        loader.setController(controller);
                        AnchorPane aera = loader.load();
                        // Set scans page into the center of root layout.
                        mainApp.getRootLayout().setCenter(aera);
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }
            }
        });
        JFXButton trainButton = new JFXButton("Entraîner l'algorithme");
        trainButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    // reload page
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/aeroscan/view/LoadingScreen.fxml"));

                    LoadingScreen controller = new LoadingScreen("Training in progress");
                    loader.setController(controller);
                    AnchorPane aera = loader.load();
                    // Set scans page into the center of root layout.
                    mainApp.getRootLayout().setCenter(aera);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                JSONParser parser = new JSONParser();

                try {

                    Object obj = parser.parse(new FileReader("src/data/" + currentDirectory + "/config.json"));

                    JSONObject jsonObject = (JSONObject) obj;
                    long epochs = (long) jsonObject.get("Epochs");
                    double severity = (double) jsonObject.get("Severity");
                    String algorithm = (String) jsonObject.get("Algorithm");

                    System.out.println(jsonObject);


                    switch (algorithm) {
                        case "KNN":
                            Process pKNN = Runtime.getRuntime().exec("python src/algorithms/knn.py " + "src/data/" + currentDirectory + "/references" + " " + 1 + " " + epochs + " " + severity);
                            pKNN.waitFor();
                            System.out.println("Python : " + pKNN.exitValue());
                            BufferedReader in = new BufferedReader(new InputStreamReader(pKNN.getInputStream()));
                            in.lines().forEach(System.out::println);
                            break;
                        case "SVM":
                            Process pSVM = Runtime.getRuntime().exec("python src/algorithms/svm.py " + "src/data/" + currentDirectory + "/references" + " " + 1 + " " + epochs + " " + severity);
                            pSVM.waitFor();
                            System.out.println("Python : " + pSVM.exitValue());
                            BufferedReader in1 = new BufferedReader(new InputStreamReader(pSVM.getInputStream()));
                            in1.lines().forEach(System.out::println);
                            break;
                        case "AE":
                            Process pAE = Runtime.getRuntime().exec("python src/algorithms/cae.py " + "src/data/" + currentDirectory + "/references" + " " + 1 + " " + epochs + " " + severity);
                            pAE.waitFor();
                            System.out.println("Python : " + pAE.exitValue());
                            BufferedReader in2 = new BufferedReader(new InputStreamReader(pAE.getInputStream()));
                            in2.lines().forEach(System.out::println);

                            BufferedReader in3 = new BufferedReader(new InputStreamReader(pAE.getErrorStream()));
                            in3.lines().forEach(System.out::println);
                            break;
                    }


                } catch (InterruptedException e){
                    e.printStackTrace();
                }catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Reload the page once the training is complete
                try {
                    // Load scans page
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/aeroscan/view/Aera.fxml"));

                    Aera controller = new Aera(currentDirectory, mainApp);
                    loader.setController(controller);
                    AnchorPane aera = loader.load();
                    // Set scans page into the center of root layout.
                    mainApp.getRootLayout().setCenter(aera);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        //trainButton.getStyleClass().add("button-raised");

        BorderPane borderPane = new BorderPane();


        HBox hBox = new HBox();
        hBox.getChildren().addAll(AddPhotoButton, trainButton);

        borderPane.setCenter(tabPane);
        borderPane.setBottom(hBox);

        AnchorPane.setTopAnchor(borderPane, 0.0);
        AnchorPane.setBottomAnchor(borderPane, 0.0);
        AnchorPane.setLeftAnchor(borderPane, 0.0);
        AnchorPane.setRightAnchor(borderPane, 0.0);
        rootLayout.getChildren().add(borderPane);
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







