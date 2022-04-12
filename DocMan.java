import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;

public class DocMan extends Application{

    // private TextArea textArea;
    private Scene scene;
    TextArea textArea = new TextArea();
    Browser browser = new Browser();

    @Override public void start(Stage stage) {
        // create the scene


        try {
  
            // set title for the stage
            stage.setTitle("DocMan");
  
            // create a splitpane
            SplitPane split_pane = new SplitPane();
  
            
            textArea.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                    // TODO Auto-generated method stub
                    System.out.println(arg0);
                    browser.setContent(arg0.getValue());
                }
            });
            
            split_pane.getItems().add(textArea);
            split_pane.getItems().add(browser);
            // create a scene
            // Scene scene = new Scene(split_pane, 500, 300);
  
            scene = new Scene(split_pane, 1500, 1000, Color.web("#666970"));
            // scene.getStylesheets().add("/home/sheldonvon/Proj/DocSys_JAVA/text-area-background.css");

            scene.getStylesheets().add(DocMan.class.getResource("style.css").toExternalForm());
            // set the scene
            stage.setScene(scene);
  
            stage.show();
        }
  
        catch (Exception e) {
  
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Browser extends Region {
 
    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();
     
    public Browser() {
        //apply the styles
        getStyleClass().add("browser");
        // load the web page
        String a = "<html><head>DocMan</head><body><h1>Document Management System</h1></body></html>";
        webEngine.loadContent(a);;

        // webEngine.load("http://www.oracle.com/products/index.html");
        //add the web view to the scene
        getChildren().add(browser);
 
    }

    public void setContent(String content) {
        webEngine.loadContent(content);
    }

    private Node createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }
 
    @Override protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }
 
    @Override protected double computePrefWidth(double height) {
        return 900;
    }
 
    @Override protected double computePrefHeight(double width) {
        return 400;
    }
}