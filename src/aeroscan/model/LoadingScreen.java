package aeroscan.model;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;


public class LoadingScreen {
    private AnchorPane rootLayout;
    private AnchorPane anchorPane = new AnchorPane();
    private Label textLoading = new Label();
    private ImageView logo = new ImageView(new Image("aeroscan/resources/icons/logo.png"));

    public LoadingScreen(AnchorPane rootLayout, String text){
        this.rootLayout = rootLayout;
        this.textLoading.setText(text);


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
    }
}
