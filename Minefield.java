package Saper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import Saper.Field.FieldState;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

class Minefield extends GridPane {
	private static final int BOARDSIZE = 15;
	private static final int BOMBS = 20;
	Field[][] fields = null;
	private int leftToWin = BOARDSIZE * BOARDSIZE - BOMBS;
	boolean started = false;
	private Stopwatch stopwatch = null;
	private HBox InfoPanel;
	private Label numOfFlagsLeft;
	private int flagsLeft = BOMBS;
	
	Minefield() {
		this.setAlignment(Pos.CENTER);
		this.setPrefWidth(30);
		this.setPrefHeight(30);
		prepareInfoPanel();
		reset();
	}	
	protected void reset() {
		leftToWin = BOARDSIZE * BOARDSIZE - BOMBS;
		started = false;		
		flagsLeft = BOMBS;
		numOfFlagsLeft.setText(Integer.toString(flagsLeft));
		ArrayList<Integer> bombs = generateRandomArray(BOMBS);
    	
		fields = new Field[BOARDSIZE][BOARDSIZE];
		for(int i = 0; i < BOARDSIZE; i++)
    		for(int j = 0; j < BOARDSIZE; j++) {
    			fields[i][j] = new Field();
        		fields[i][j].setPrefHeight(this.getPrefHeight());    		
        		fields[i][j].setPrefWidth(this.getPrefWidth());
    			final int numberOfField = i*15 + j;
    			if(bombs.contains(numberOfField)) fields[i][j].setBomb(true);
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
                        	fields[ii][jj].setState(FieldState.FLAGGED);
                        	numOfFlagsLeft.setText(Integer.toString(--flagsLeft)); 
                        	//coverAll();
                        	//this.coverAll();
                    	}
                    	else if(fields[ii][jj].getState() == FieldState.FLAGGED) {
                    		fields[ii][jj].setState(FieldState.COVERED);
                    		numOfFlagsLeft.setText(Integer.toString(++flagsLeft));
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
		stopwatch.stop();
		stopwatch.reset();
		coverAll();
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
							&& fields[r][k].getState() == FieldState.COVERED) {
							check(r,k);
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
			this.reset();
		} else {
			Platform.exit();
		}
	}	
	protected Stopwatch getStopwatch() {
		return stopwatch;
	}
	protected HBox getInfoPanel() {
		return InfoPanel;
	}
	protected void prepareInfoPanel() {
		InfoPanel = new HBox();		
		stopwatch = new Stopwatch();
		Label flagImg = new mLabel();
		flagImg.setId("cleanFlag");
		flagImg.setPrefHeight(30);
		flagImg.setPrefWidth(30);
		numOfFlagsLeft = new mLabel();
		numOfFlagsLeft.setText(Integer.toString(flagsLeft));
		HBox flagsBox = new HBox();
		flagsBox.getChildren().add(flagImg);
		flagsBox.getChildren().add(numOfFlagsLeft);
		InfoPanel.getChildren().add(stopwatch);
		InfoPanel.getChildren().add(flagsBox);
		InfoPanel.setAlignment(Pos.CENTER);
	}
	private static ArrayList<Integer> generateRandomArray(int n) {
		ArrayList<Integer> randomList = new ArrayList<Integer>();
    	for(int i = 0; i < BOARDSIZE * BOARDSIZE; i++)
    		randomList.add(new Integer(i));
    	Collections.shuffle(randomList);
    	ArrayList<Integer> bombs = new ArrayList<Integer>(randomList.subList(0, n));
    	System.out.println(bombs);
    	return bombs;
	}
	private void coverAll() {
		for(int i = 0; i < BOARDSIZE; i++)
			for(int j = 0; j < BOARDSIZE; j++)
				fields[i][j].setState(FieldState.COVERED);
		System.out.println("Cover");
	}
}