package aeroscan.model;


import javafx.scene.image.Image;

public class CustomCellAlgorithms {
    private final String DEFAULT_ICON_PATHNAME="aeroscan/resources/icons/document-empty.png";

    private String description;
    private String title;
    private Image icon;

    public CustomCellAlgorithms(String title, String description, Image icon){
        this.description=description;
        this.title=title;
        this.icon=icon;
    }

    public CustomCellAlgorithms(String title, String description){
        this.description=description;
        this.title=title;
        this.icon=new Image(DEFAULT_ICON_PATHNAME);
    }

    public void setIcon(Image icon){
        this.icon = icon;
    }

    public String getDescription(){
        return description;
    }

    public String getTitle(){
        return title;
    }

    public Image getIcon(){
        return icon;
    }
}
