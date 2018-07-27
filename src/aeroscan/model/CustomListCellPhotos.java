package aeroscan.model;

import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class CustomListCellPhotos extends ListCell<CustomCellPhotos> {
    private static final String LISTCELL_NAME_CLASS = "listCell-name";
    private static final String LISTCELL_DATE_CLASS = "listCell-date";
    private static final String LISTCELL_ICON_CLASS = "listCell-icon";

    private GridPane grid = new GridPane();
    private ImageView icon = new ImageView();
    private Pane imageContainer = new Pane();
    private Label name = new Label();
    private Label date = new Label();
    private Label statut = new Label();
    private VBox actions_containers = new VBox();

    private Label name_label = new Label("Title: ");
    private Label statut_label = new Label("Statut: ");
    private Label date_label = new Label("Date: ");
    private Label actions_label = new Label("Actions: ");



    public CustomListCellPhotos() {
        configureGrid();
        configureIcon();
        configureName();
        configureDate();
        addControlsToGrid();
    }


    private void configureGrid() {
        grid.setHgap(25);
        grid.setVgap(5);
        grid.setPadding(new Insets(5, 5, 5, 5));
    }

    private void configureIcon() {

        double width = 150;
        double height = 150;
        icon.getStyleClass().add(LISTCELL_ICON_CLASS);
//icon.setFitWidth(width);
        icon.setFitHeight(height);
        icon.setPreserveRatio(true);


// From : https://stackoverflow.com/questions/20489908/border-radius-and-shadow-on-imageview
// set a clip to apply rounded border to the original image.
        Rectangle clip = new Rectangle(
                width, icon.getFitHeight()
        );
        clip.setArcWidth(8);
        clip.setArcHeight(8);
        icon.setClip(clip);

// snapshot the rounded image.
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = icon.snapshot(parameters, null);

// remove the rounding clip so that our effect can show through.
//icon.setClip(null);

// apply a shadow effect.
        clip.setEffect(new DropShadow( 5, Color.BLACK));
        icon.setEffect(new DropShadow(5, Color.BLACK));

// store the rounded image in the imageView.
        icon.setImage(image);

        imageContainer.setPrefSize(150, 150);
        imageContainer.getChildren().add(icon);

    }

    private void configureName() {
        name.getStyleClass().add(LISTCELL_NAME_CLASS);
    }

    private void configureDate() {
        date.getStyleClass().add(LISTCELL_DATE_CLASS);
    }

    private void addControlsToGrid() {
        grid.add(imageContainer, 0, 0, 1, 5);
        grid.add(name_label, 1, 0, 1, 1);
        grid.add(statut_label, 1, 1, 1, 1);
        grid.add(date_label, 1, 2, 1, 1);
        grid.add(actions_label, 1, 3, 1, 1);
        grid.add(name, 2, 0, 1, 1);
        grid.add(statut, 2, 1, 1, 1);
        grid.add(date, 2, 2, 1, 1);
        grid.add(actions_containers, 2, 3, 1, 1);
    }

    @Override
    protected void updateItem(CustomCellPhotos cell, boolean empty) {
        super.updateItem(cell, empty);
        if (empty) {
            clearContent();
        } else {
            addContent(cell);
        }
    }

    private void clearContent() {
        actions_containers.getChildren().clear();
        setText(null);
        setGraphic(null);
    }

    private void addContent(CustomCellPhotos cell) {
        setText(null);

        icon.setImage(cell.getIcon());
        name.setText(cell.getName());
        date.setText(cell.getDate());
        statut.setText(cell.getStatut());
        actions_containers.getChildren().clear();
        actions_containers.getChildren().addAll(cell.getShowButton(), cell.getTrainButton(), cell.getDeleteButton());
        setGraphic(grid);
    }
}