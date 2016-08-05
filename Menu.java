package Saper;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;

class Menu extends HBox {
	Menu(Minefield minefield) {
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(30,10,10,10));
    	Button newGame = new Button("New Game");
    	newGame.setId("covered");
    	newGame.setOnAction(new EventHandler<ActionEvent>() {        	 
            @Override
            public void handle(ActionEvent e) {
                minefield.reset();
            }
        });
    	//newGame.setPadding(new Insets(10,10,10,10));
    	Button exit = new Button("Exit");
    	exit.setId("covered");
    	exit.setOnAction(new EventHandler<ActionEvent>() {        	 
            @Override
            public void handle(ActionEvent e) {
                Platform.exit();
            }
        });
    	
    	ObservableList<String> options = 
    		    FXCollections.observableArrayList(
    		        "Village",
    		        "Town",
    		        "City"
    		    );
    	ComboBox<String> comboBox = new ComboBox<String>(options);
    	comboBox.setPromptText("Difficulty");
    	this.getChildren().add(newGame);
    	this.getChildren().add(exit);
    	this.getChildren().add(comboBox);
	}
}
