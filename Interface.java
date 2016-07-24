package Saper;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Interface extends Application {
	@Override
    public void start(Stage primaryStage) {    	
		primaryStage.setTitle("Saper");
		BorderPane pane = new BorderPane();
    	Minefield minefield = new Minefield();
    	Menu menu = new Menu(minefield);
    	pane.setCenter(minefield);
    	pane.setTop(menu);
    	Stopwatch stopwatch = new Stopwatch();
    	pane.setBottom(stopwatch);
    	
    	Scene scene = new Scene(pane, 600, 600);
    	primaryStage.setScene(scene);
    	scene.getStylesheets().add(Interface.class.getResource("styles.css").toExternalForm());
    	primaryStage.show();
	}
	
	public static void main(String[] args) {
        launch(args);
    }
}