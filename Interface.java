package Saper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Interface extends Application {
	@Override
    public void start(Stage primaryStage) {
		primaryStage.setTitle("Saper");
		BorderPane pane = new BorderPane();
    	Minefield minefield = new Minefield(primaryStage);
    	Menu menu = new Menu(minefield);
    	
    	pane.setCenter(minefield);
    	pane.setTop(menu);
    	pane.setBottom(minefield.getInfoPanel());
    	pane.setId("displayed");
    	
    	Scene scene = new Scene(pane);
    	primaryStage.setScene(scene);
    	scene.getStylesheets().add(Interface.class.getResource("styles.css").toExternalForm());
    	primaryStage.show();
	}
	
	public static void main(String[] args) {
        launch(args);
    }
}