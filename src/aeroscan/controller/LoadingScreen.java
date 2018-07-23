package aeroscan.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;


public class LoadingScreen implements Initializable{
    @FXML
    private AnchorPane rootLayout;
    @FXML
    private Label textLoading;
    @FXML
    private ImageView logo;

    private String text;

    public LoadingScreen(String text){
        this.text = text;

        /*
        this.anchorPane.setStyle("-fx-background-color: #000000");
        this.anchorPane.setStyle("-fx-opacity: 100%");


        AnchorPane.setTopAnchor(anchorPane, 0.0);
        AnchorPane.setBottomAnchor(anchorPane, 0.0);
        AnchorPane.setLeftAnchor(anchorPane, 0.0);
        AnchorPane.setRightAnchor(anchorPane, 0.0);
        this.rootLayout.getChildren().add(anchorPane);

        this.anchorPane.getChildren().addAll(textLoading, logo);

        this.logo.setFitWidth(50);
        this.logo.setFitHeight(50);
        this.logo.setLayoutX(anchorPane.getWidth()/2);
        this.logo.setLayoutY(anchorPane.getHeight()/2);

        this.textLoading.setLayoutX(anchorPane.getWidth()/3);
        this.textLoading.setLayoutY(anchorPane.getHeight()/3);
        this.textLoading.setFont(new Font(30));
        */
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.textLoading.setText(this.text);
        this.logo.setImage(new Image("aeroscan/resources/icons/logo.png"));
    }
}
