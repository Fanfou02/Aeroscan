package aeroscan.utils;

        import javafx.geometry.Insets;
        import javafx.scene.control.Label;
        import javafx.scene.control.ListCell;
        import javafx.scene.image.ImageView;
        import javafx.scene.layout.GridPane;
        import javafx.scene.layout.Region;

public class CustomListCellAlgorithms extends ListCell<CustomCellAlgorithms> {
    private static final String LISTCELL_DESCRIPTION_CLASS = "listCell-description";
    private static final String LISTCELL_TITLE_CLASS = "listCell-title";
    private static final String LISTCELL_ICON_CLASS = "listCell-icon";

    private GridPane grid = new GridPane();
    private ImageView icon = new ImageView();
    private Label description = new Label();
    private Label title = new Label();

    public CustomListCellAlgorithms() {
        configureGrid();
        configureIcon();
        configureDescription();
        configureTitle();
        addControlsToGrid();
    }

    private void configureGrid() {
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 16, 0, 20));
    }

    private void configureIcon() {
        icon.getStyleClass().add(LISTCELL_ICON_CLASS);
        icon.setFitWidth(160);
        icon.setFitHeight(160);
    }

    private void configureDescription() {
        description.getStyleClass().add(LISTCELL_DESCRIPTION_CLASS);
        description.setWrapText(true);
    }

    private void configureTitle() {
        title.getStyleClass().add(LISTCELL_TITLE_CLASS);
        title.setWrapText(true);
    }

    private void addControlsToGrid() {
        grid.add(icon, 0, 0, 2, 1);
        grid.add(title, 0, 1);
        grid.add(description, 0, 2);
    }

    @Override
    protected void updateItem(CustomCellAlgorithms cell, boolean empty) {
        super.updateItem(cell, empty);
        if (empty) {
            clearContent();
        } else {

            addContent(cell);
            setMinHeight(0);
            setPrefHeight(0);
        }
    }

    private void clearContent() {
        setText(null);
        setGraphic(null);
    }

    private void addContent(CustomCellAlgorithms cell) {
        setText(null);
        icon.setImage(cell.getIcon());
        description.setText(cell.getDescription());
        title.setText(cell.getTitle());
        setGraphic(grid);
    }
}