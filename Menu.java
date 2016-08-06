package Saper;

import Saper.Minefield.Difficulty;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

class Menu extends HBox {
	Menu(Minefield minefield) {
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(30,30,0,30));
    	Button newGame = new Button("New Game");
    	newGame.setId("covered");
    	newGame.setOnAction(new EventHandler<ActionEvent>() {        	 
            @Override
            public void handle(ActionEvent e) {
                minefield.reset();
            }
        });
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
    	comboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {
            	switch (t1) {
            	case "Village":
            		minefield.changeDifficulty(Difficulty.VILLAGE);
            		break;
            	case "Town":
            		minefield.changeDifficulty(Difficulty.TOWN);
            		break;
            	case "City":
            		minefield.changeDifficulty(Difficulty.CITY);
            		break;
            	}
            }    
        });
    	
    	this.getChildren().add(newGame);    	
    	this.getChildren().add(comboBox);
    	this.getChildren().add(exit);
	}
}
