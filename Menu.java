package Saper;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

class Menu extends HBox {
	Menu(Minefield minefield) {
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(30,10,10,10));
    	Button newGame = new Button("New Game");
    	newGame.setOnAction(new EventHandler<ActionEvent>() {        	 
            @Override
            public void handle(ActionEvent e) {
                minefield.reset();
            }
        });
    	//newGame.setPadding(new Insets(10,10,10,10));
    	Button exit = new Button("Exit");
    	exit.setOnAction(new EventHandler<ActionEvent>() {        	 
            @Override
            public void handle(ActionEvent e) {
            	System.out.println("exit");
                Platform.exit();
            }
        });
    	this.getChildren().add(newGame);
    	this.getChildren().add(exit);
	}
}
