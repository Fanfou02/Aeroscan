package aeroscan.controller;

import aeroscan.Main;
import javafx.fxml.FXML;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;

public class FlightPlanner {
    // Reference to the main application.
    private Main mainApp;

    @FXML
    private AnchorPane flightLayout;

    @FXML
    private Label comingSoonLabel;

    MyBrowser myBrowser;
    double lat;
    double lon;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public FlightPlanner(){

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        myBrowser = new MyBrowser();
        flightLayout.getChildren().add(myBrowser);
        AnchorPane.setTopAnchor(myBrowser, 0.0);
        AnchorPane.setBottomAnchor(myBrowser, 0.0);
        AnchorPane.setLeftAnchor(myBrowser, 0.0);
        AnchorPane.setRightAnchor(myBrowser, 0.0);

        comingSoonLabel.setAlignment(Pos.CENTER);

    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }


    class MyBrowser extends AnchorPane {


        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();


        public MyBrowser() {


            final URL urlGoogleMaps = getClass().getResource("demo.html");
            webEngine.load(urlGoogleMaps.toExternalForm());
            webEngine.setOnAlert(new EventHandler<WebEvent<String>>() {
                @Override
                public void handle(WebEvent<String> e) {
                    System.out.println(e.toString());
                }
            });

            AnchorPane.setTopAnchor(webView, 0.0);
            AnchorPane.setBottomAnchor(webView, 0.0);
            AnchorPane.setLeftAnchor(webView, 0.0);
            AnchorPane.setRightAnchor(webView, 0.0);

            getChildren().add(webView);

            //final TextField latitude = new TextField("" + 35.857908 * 1.00007);
            //final TextField longitude = new TextField("" + 10.598997 * 1.00007);
            //Button update = new Button("Update");
            /*update.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    lat = Double.parseDouble(latitude.getText());
                    lon = Double.parseDouble(longitude.getText());

                    System.out.printf("%.2f %.2f%n", lat, lon);

                    webEngine.executeScript("" +
                            "window.lat = " + lat + ";" +
                            "window.lon = " + lon + ";" +
                            "document.goToLocation(window.lat, window.lon);"
                    );
                }
            });

            HBox toolbar  = new HBox();
            toolbar.getChildren().addAll(latitude, longitude, update);

            getChildren().addAll(toolbar);
            */
        }
    }
}







