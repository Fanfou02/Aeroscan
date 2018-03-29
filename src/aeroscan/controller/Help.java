package aeroscan.controller;

import aeroscan.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class Help {
    // Reference to the main application.
    private Main mainApp;

    @FXML
    private TextArea textArea;

    private final String title = "Help";
    private final String body = "Ideas neque illos mea eae. Vi at ad complector id quaecumque consuetudo. Fal praecise rum physicam actiones lus recenseo uno. Ita hac cera meis rari. Du re impulsum ab ex ulterius perspexi. Hic sae admi sex quid veat. Sex prudentiae sae concipitur jam percipimus imaginandi quantumvis indubitati. Firma corpo situm nam totos latum mem. Ei id ferias multis dividi fusius firmae firmum. \\n\" +\n" +
            "                \"\\n\" +\n" +
            "                \"Credendi at nequidem exhibere de. Debeo me dicam ex at ferri digna. Coloribus differant disputari vix cogitandi jam stabilire. Perfacile ut reliquiae perfectae ut. Fuisse falsas captum cui volent notior verbis sui. Meam idem nam ope prae isti quia jure hac. Lor durent has secius fronte usu auditu sumpti. Falso nomen aliud vim calor jam alias annos ubi. Movendi sum creatus vim fas majorem attendo propter. Sui videamus uno profecto refutent rom notitiam innumera potuerit. \\n\" +\n" +
            "                \"\\n\" +\n" +
            "                \"Ignem ratio fingo istam etc vix una velut veris deo. Teneri habeam perire putavi to cogito sentio me ac. Ad potuisse in ne supponit loquendo me. ";

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public Help(){

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setText(body);
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







