package Saper;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.Random;
import java.util.stream.IntStream;

public class Interface extends Application {
	@Override
    public void start(Stage primaryStage) {    	
		primaryStage.setTitle("Saper");
		GridPane grid = new GridPane();
		grid.setGridLinesVisible(true);
    	grid.setAlignment(Pos.CENTER);
    	//grid.setHgap(10);
    	//grid.setVgap(10);
    	//grid.setPadding(new Insets(0, 0, 0, 0));
    	grid.setPrefWidth(30);
    	grid.setPrefHeight(30);
    	// generating bombs
    	final int boardSize = 15;
		Random rand = new Random();
    	int max = 224;
    	int[] bombs = new int[20];
    	for(int i = 0; i < 20; i++) {    		
    		bombs[i] = rand.nextInt((max) + 1);
    		System.out.println(bombs[i]);
    	}
    	// creating Minefield
    	Minefield[][] fields = new Minefield[boardSize][boardSize];
    	for(int i = 0; i < boardSize; i++)
    		for(int j = 0; j < boardSize; j++) {
    			fields[i][j] = new Minefield();
        		fields[i][j].setPrefHeight(grid.getPrefHeight());    		
        		fields[i][j].setMinWidth(grid.getPrefWidth());
    			final int numberOfField = i*15 + j;
    			boolean contains = IntStream.of(bombs).anyMatch(x -> x == numberOfField);
    			if(contains) {
    				fields[i][j].setBomb(true);
    				fields[i][j].setId("bomb");
    			}
    			//fields[i][j].setId("hidden");
    			grid.add(fields[i][j], i, j);
    		}
    	// counting bombsAround
    	for(int i = 0; i < boardSize; i++)
    		for(int j = 0; j < boardSize; j++) {
    			if(!fields[i][j].isBomb()) 
        			for(int r = i - 1; r < i + 2; r++)
        				for(int k = j - 1; k < j + 2; k++)
        					if(r >= 0 && r < boardSize && k >= 0 && k < boardSize &&
        						fields[r][k].isBomb()) fields[i][j].setBombsAround(fields[i][j].getBombsAround()+1);				
    			if(!fields[i][j].isBomb()) fields[i][j].setText(Integer.toString(fields[i][j].getBombsAround()));
    		}
    	
    	Scene scene = new Scene(grid, 600, 600);
    	primaryStage.setScene(scene);
    	scene.getStylesheets().add(Interface.class.getResource("styles.css").toExternalForm());
    	primaryStage.show();
	}
	
	public static void main(String[] args) {
        launch(args);
    }
}

// repeated bombs