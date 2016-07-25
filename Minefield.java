package Saper;

import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import Saper.Field.FieldState;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

class Minefield extends GridPane {
	private static final int BOARDSIZE = 15;
	private static final int BOMBS = 20;
	Field[][] fields = null;
	private int leftToWin = BOARDSIZE * BOARDSIZE - BOMBS;
	boolean started = false;
	Stopwatch stopwatch = null;	

	Minefield() {
		stopwatch = new Stopwatch();
		reset();
	}
	
	protected void reset() {
		leftToWin = BOARDSIZE * BOARDSIZE - BOMBS;
		started = false;
		//this.setGridLinesVisible(true);
		this.setAlignment(Pos.CENTER);
    	//grid.setHgap(10);
    	//grid.setVgap(10);
    	//this.setPadding(new Insets(10, 10, 10, 10));
		this.setPrefWidth(30);
		this.setPrefHeight(30);
		Random rand = new Random();
    	int max = 224;
    	int[] bombs = new int[BOMBS];
    	for(int i = 0; i < BOMBS; i++) {    		
    		bombs[i] = rand.nextInt((max) + 1);
    		System.out.println(bombs[i]);
    	}
    	
		fields = new Field[BOARDSIZE][BOARDSIZE];
		for(int i = 0; i < BOARDSIZE; i++)
    		for(int j = 0; j < BOARDSIZE; j++) {
    			fields[i][j] = new Field();
        		fields[i][j].setPrefHeight(this.getPrefHeight());    		
        		fields[i][j].setMinWidth(this.getPrefWidth());
    			final int numberOfField = i*15 + j;
    			boolean contains = IntStream.of(bombs).anyMatch(x -> x == numberOfField);
    			if(contains) {
    				fields[i][j].setBomb(true);
    			}    			
    			fields[i][j].setId("covered");
    			final int ii = i, jj = j;
    			fields[i][j].addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
                    if( e.isPrimaryButtonDown() && e.isSecondaryButtonDown()) { 
                        doubleClick(ii,jj);
                    } else if( e.isPrimaryButtonDown()) {
                    	if(started == false) {
                    		started = true;
                    		stopwatch.start();
                    	}
                    	if(fields[ii][jj].getState() != FieldState.FLAGGED)
                    		check(ii,jj);
                    } else if( e.isSecondaryButtonDown()) {
                    	if(fields[ii][jj].getState() == FieldState.COVERED) {
                    		
                        	fields[ii][jj].setState(FieldState.FLAGGED);;
                    	}
                    	else if(fields[ii][jj].getState() == FieldState.FLAGGED) {
                    		fields[ii][jj].setState(FieldState.COVERED);
                    		
                    	}
                    }

                });    			
    			
    			this.add(fields[i][j], j, i);
    		}
		
		for(int i = 0; i < BOARDSIZE; i++)
    		for(int j = 0; j < BOARDSIZE; j++) {
    			if(!fields[i][j].isBomb()) 
        			for(int r = i - 1; r < i + 2; r++)
        				for(int k = j - 1; k < j + 2; k++)
        					if(r >= 0 && r < BOARDSIZE && k >= 0 && k < BOARDSIZE &&
        						fields[r][k].isBomb()) fields[i][j].setBombsAround(fields[i][j].getBombsAround()+1);				
    			//if(!fields[i][j].isBomb()) fields[i][j].setText(Integer.toString(fields[i][j].getBombsAround()));
    			//if(!fields[i][j].isBomb()) fields[i][j].setText(i + "," + j);
    		}
	}
	
	protected void check(int i, int j) {
		if(fields[i][j].isBomb()) {
			fields[i][j].setId("bomb");
			gameover();
		}
		else {
			uncover(i,j);
		}
	}
	
	protected void uncover(int i, int j) {	
		if(fields[i][j].getState() == FieldState.COVERED) {
			fields[i][j].setText(Integer.toString(fields[i][j].getBombsAround()));
			fields[i][j].setId("uncovered");
			fields[i][j].setState(FieldState.DISPLAYED);
			if(--leftToWin == 0) win();
			if(fields[i][j].getBombsAround() == 0) {
				fields[i][j].setText(null);	
				for(int r = i - 1; r < i + 2; r++)
					for(int k = j - 1; k < j + 2; k++)
						if(r >= 0 && r < BOARDSIZE && k >= 0 && k < BOARDSIZE && !(r == i && k == j)
							&& fields[r][k].getState() == FieldState.COVERED) {
							uncover(r,k);
						}
			}
		}		
	}
	protected void doubleClick(int i, int j) {
		if(fields[i][j].getState() == FieldState.DISPLAYED) {
			int flags = 0;
			for(int r = i - 1; r < i + 2; r++)
				for(int k = j - 1; k < j + 2; k++)
					if(r >= 0 && r < BOARDSIZE && k >= 0 && k < BOARDSIZE && !(r == i && k == j) &&
						fields[r][k].getState() == FieldState.FLAGGED) flags++;

			//System.out.println(fields[i][j].getBombsAround() + "," + flags);
			if(flags == fields[i][j].getBombsAround())
			{
				for(int r = i - 1; r < i + 2; r++)
					for(int k = j - 1; k < j + 2; k++)
						if(r >= 0 && r < BOARDSIZE && k >= 0 && k < BOARDSIZE && !(r == i && k == j)
							&& fields[r][k].getState() == FieldState.COVERED && fields[r][k].isBomb() == false) {
							uncover(r,k);
						}
			}
		}	
	}
	
	protected void gameover() {
		stopwatch.stop();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Game Over");
		alert.setHeaderText(null);
		alert.setContentText("This city is lost!\n Do you wanna try to save another?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    reset();
		} else {
			Platform.exit();
		}
	}
	
	protected void win() {
		stopwatch.stop();
		for(int i = 0; i < BOARDSIZE; i++)
    		for(int j = 0; j < BOARDSIZE; j++)
    			if(fields[i][j].isBomb()) fields[i][j].setState(FieldState.FLAGGED);
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Victory!");
		alert.setHeaderText(null);
		alert.setContentText("You've saved the city!\nYou did it in just " + stopwatch.getElapsedSeconds() + "s\nDo you wanna save another?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			reset();
		} else {
			Platform.exit();
		}
	}
	
	protected Stopwatch getStopwatch() {
		return stopwatch;
	}
}