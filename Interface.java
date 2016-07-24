package Saper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Interface extends Application {
	@Override
    public void start(Stage primaryStage) {    	
		primaryStage.setTitle("Saper");
    	Minefield minefield = new Minefield();
    	
    	Scene scene = new Scene(minefield, 600, 600);
    	primaryStage.setScene(scene);
    	scene.getStylesheets().add(Interface.class.getResource("styles.css").toExternalForm());
    	primaryStage.show();
	}
	
	public static void main(String[] args) {
        launch(args);
    }
}

// repeated bombs