package aeroscan.utils;


import javafx.scene.image.Image;

public class CustomCellScans {
    private final String DEFAULT_ICON_PATHNAME="aeroscan/resources/icons/document-empty.png";

    private String name;
    private String date;
    private Image icon;

    public CustomCellScans(String name, String date, Image icon){
        this.name=name;
        this.date=date;
        this.icon=icon;
    }

    public CustomCellScans(String name, String date){
        this.name=name;
        this.date=date;
        this.icon=new Image(DEFAULT_ICON_PATHNAME);
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

    public Image getIcon(){
        return icon;
    }
}
