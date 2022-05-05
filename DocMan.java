import javafx.application.Application;
import javafx.scene.control.TextArea;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.StyleConverter.StringStore;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.PathElement;
import javafx.stage.Stage;
import javafx.scene.control.SplitPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.layout.Region;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Priority;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import javafx.print.PrinterJob;
import javafx.stage.FileChooser.ExtensionFilter;
import java.util.*;

public class DocMan extends Application{

    // private TextArea textArea;
    private Scene scene;
    Preview preview = new Preview();
    EditPanel editPanel = new EditPanel();
    TopMenu tMenu;

    @Override public void start(Stage stage) {
        // create the scene
        System.setProperty("http.proxyHost","127.0.0.1");
        System.setProperty("http.proxyPort","8889");
        try {
            // set title for the stage
            stage.setTitle("DocMan");
            stage.setWidth(1500);
            stage.setHeight(1000);
            tMenu = new TopMenu(stage);
            // create a splitpane
            SplitPane split_pane = new SplitPane();
            // split_pane.setMinHeight(940);
            split_pane.prefHeightProperty().bind(stage.heightProperty());
            split_pane.getItems().add(editPanel);
            split_pane.getItems().add(preview);
            // create a scene
            VBox showBox = tMenu.getMenuBox(split_pane);
            
            scene = new Scene(showBox, Color.web("#666970"));

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

    /**
     * TopMenu
     */
    public class TopMenu {
        // create a menu
        Menu fileM = new Menu("File");
        Menu convertM = new Menu("Compile");
        // create a menubar
        MenuBar mb = new MenuBar();
        // create menuitems
        MenuItem m1 = new MenuItem("Open");
        MenuItem m2 = new MenuItem("Save");
        MenuItem m3 = new MenuItem("to HTML");
        MenuItem m4 = new MenuItem("Print");

        public TopMenu(Stage stage) {

            // add menu items to menu
            m1.setOnAction(e -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open DocMan File");
                fileChooser.getExtensionFilters().addAll(new ExtensionFilter("DocMan File", "*.dm"));
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) loadFile(file);
            });
            m2.setOnAction(e -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save DocMan File");
                fileChooser.getExtensionFilters().addAll(new ExtensionFilter("DocMan File", "*.dm"));
                File file = fileChooser.showSaveDialog(stage);
                if (file != null) saveFile(file);
            });
            fileM.getItems().add(m1);
            fileM.getItems().add(m2);

            m3.setOnAction(e -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save HTML File");
                fileChooser.getExtensionFilters().addAll(new ExtensionFilter("HTML File", "*.html"));
                File file = fileChooser.showSaveDialog(stage);
                if (file != null) saveHTMLFile(file);
            });
            m4.setOnAction(e -> {
                PrinterJob job = PrinterJob.createPrinterJob();
                if (job != null && job.showPrintDialog(null)) {
                    preview.webEngine.print(job);
                    job.endJob();
                }
            });
            convertM.getItems().add(m3);
            convertM.getItems().add(m4);
            
            mb.getMenus().add(fileM);
            mb.getMenus().add(convertM);
        }

        public void loadFile(File file) {
            try {
                FileInputStream fis = new FileInputStream(file);
                byte[] data = new byte[(int) file.length()];
                fis.read(data);
                fis.close();
                String str = new String(data, "UTF-8");
                editPanel.setText(str);
                preview.content.setRawContent(str);
                preview.refresh();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        public void saveFile(File file) {
            if(!file.getName().endsWith(".dm")){
                file = new File(file.getName()+".dm");
            }
            try(
                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                //convert string to byte array
                byte[] bytes = editPanel.getText().getBytes();
                //write byte array to file
                bos.write(bytes);
                bos.close();
                fos.close();
                System.out.print("Data written to file successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        public void saveHTMLFile(File file) {
            if(!file.getName().endsWith(".html")){
                file = new File(file.getName()+".html");
            }
            try(
                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                //convert string to byte array
                byte[] bytes = preview.content.getContent().getBytes();
                //write byte array to file
                bos.write(bytes);
                bos.close();
                fos.close();
                System.out.print("Data written to file successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public VBox getMenuBox(javafx.scene.Node under) {
            return new VBox(mb, under);
        }
    }


    public class EditPanel extends TextArea{
    
        String defaultText = "StructBegin\n"+
            "Title{DocMan}\n"+
            "Author{Open-source Project}\n"+
            "Section{Overview}\n"+
            "Section{Installation}\n"+
            "Section{Build}\n"+
            "Section{Usage}\n"+
            "Section{Section Title 5}\n"+
            "StructEnd\n"+
            "\n"+
            "/Section{Overview}\n"+
            "<img src=\"https://raw.githubusercontent.com/jyu42/DocMan/main/assets/themeImg.jpeg\" alt=\"Theme Image\" width=\"200\" >\n"+
            "This is a very useful document manage system with <span style=\"font-style: italic;color:#AF7AC5\">live preview</span>.\n"+
            "/SectionEnd\n"+
            "\n"+
            "/Section{Installation}\n"+
            "The process of installation is <span style=\"font-weight: bold;color:blue\">easy</span>.\n"+
            "/SectionEnd\n"+
            "\n"+
            "/Section{Build}\n"+
            "It only relies on <span style=\"font-weight: bold;color:red\">one</span> external java library.\n"+
            "/SectionEnd\n"+
            "\n"+
            "/Section{Usage}\n"+
            "Using it feels even better.\n"+
            "/SubsecStart{Get Ready}\n"+
            "Start at any time.\n"+
            "/SubsecEnd\n"+

            "/SectionEnd\n"+
            "\n";

        public  EditPanel() {

            setText(defaultText);
            preview.content.setRawContent(defaultText);
            preview.refresh();

            textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {

                    preview.content.setRawContent(arg0.getValue());
                    preview.refresh();
                }
            });
        }
    }


    public class Preview extends Region{
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();
        Content content = new Content();
        
        public Preview() {
            //apply the styles
            getStyleClass().add("browser");
            // load the web page
            refresh();

            // webEngine.load("http://www.oracle.com/products/index.html");
            //add the web view to the scene
            getChildren().add(browser);

        }

        public void setContent(String content) {
            webEngine.loadContent(content);
        }
        

        public void refresh() {
            this.setContent(content.getContent());
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

    /**
     * Content
     */
    public class Content {

        String header = "<title>DocMan</title>";
        String body = "<h1>Document Management System</h1>";
        String rawContent = "";

        Pattern p_bodyStruct = Pattern.compile("StructBegin(.*)StructEnd", Pattern.DOTALL);
        Pattern p_title = Pattern.compile("Title\\{(.*?)\\}");
        Pattern p_auther = Pattern.compile("Author\\{(.*?)\\}");
        Pattern p_section = Pattern.compile("Section\\{(.*?)\\}");

        
        public String getContent() {
        String baseFormat = "%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s";
        return String.format(baseFormat, 
            "<html>", 
                "<head><meta charset=\"utf-8\">", 
                    header,
                "</head>",
                "<body>",
                    body,
                "</body>",
            "</html>");
        }

        public void setRawContent(String rawCont) {
            rawContent = rawCont;
            body = getBody();
        }
        
        private String getBody() {
            String bodyStruct = getBodyStructure(rawContent);

            String title = getTitle(bodyStruct);
            String author = getAuthor(bodyStruct);
            List<String> sectionNames = getSections(bodyStruct);
            
            String bodyContent = getBodyContent(rawContent);

            String bodyFormat = "%s\n%s\n";
            for (int i = 0; i < sectionNames.size(); i++) {
                String stringPatternsec = "/Section\\{" + sectionNames.get(i) + "\\}(.*?)/SectionEnd";


                Pattern p_SubsecStart = Pattern.compile("/SubsecStart\\{(.*?)\\}");

                
                Pattern p_section_content = Pattern.compile(stringPatternsec, Pattern.DOTALL);

                String sectionContent = match(p_section_content, bodyContent);
                if(sectionContent.length() > 0){
                    bodyFormat += "<h2>" + (i+1) + " " + sectionNames.get(i) + "</h2>\n";
                    java.lang.String[] sectionContentSplit = sectionContent.split("\n");
                    boolean isSubsec = false;
                    for(int x=0, y=0; x<sectionContentSplit.length; x++){
                        String SubsecStart = match(p_SubsecStart, sectionContentSplit[x]);

                        if(SubsecStart.length() > 0){
                            bodyFormat += "<h3>" + (i+1) + "." + (++y) + SubsecStart + "</h3>\n";
                            isSubsec = true;
                            continue;
                        } else {
                            if(sectionContentSplit[x].contains("/SubsecEnd")){
                                isSubsec = false;
                                continue;
                            }
                        }
                        if(isSubsec) bodyFormat += "<p style=\"margin-left:20px;\">" + sectionContentSplit[x] + "</p>\n";
                        else bodyFormat += "<p>" + sectionContentSplit[x] + "</p>\n";
                    }

                }
            }
            return String.format(bodyFormat, 
            "<h1>" + title + "</h1>", 
            "<h3>" + author + "</h3>");
        }

        private String getBodyStructure(String content) {
            return match(p_bodyStruct, content);
        }

        private String getBodyContent(String content) {
            Matcher m = p_bodyStruct.matcher(content);
            return m.replaceFirst("");
        }

        private String getTitle(String content) {
            return match(p_title, content);
        }

        private String getAuthor(String content) {
            return match(p_auther, content);
        }

        private List<String> getSections(String content) {
            Matcher m = p_section.matcher(content);
            List<String> sectionNames = new ArrayList<>();
            while(m.find()){
                sectionNames.add(m.group(1));
            }
            return sectionNames;
        }

        private String match(Pattern p, String target) {
            Matcher m = p.matcher(target);
            if(m.find()) {
                return m.group(1);
            } else return "";
        }

        private boolean checkCommented(String content) {
            if(content.strip().startsWith("#")) return true;
            else return false;
        }

        public void setHeader(String header) {
            this.header = "<title>"+ header +"</title>";    
        }
        
        public void setBody(String body) {
            this.body = body;
        }

        public void setTitle() {
            
        }
    }
    
}
