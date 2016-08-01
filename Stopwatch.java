package Saper;

import java.time.Duration;
import java.time.LocalTime;
import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class Stopwatch extends HBox {
	protected AnimationTimer timer = null;
	protected long elapsedSeconds;

	Stopwatch() {
		Label clockImg = new Label();
		clockImg.setId("clock");
		clockImg.setPrefHeight(30);
		clockImg.setPrefWidth(30);
		
        Label stopwatch = new Label("0");
        BooleanProperty running = new SimpleBooleanProperty(false);
        timer = new AnimationTimer() {
            private LocalTime startTime;
            @Override
            public void handle(long now) {
            	elapsedSeconds = Duration.between(startTime, LocalTime.now()).getSeconds();
                stopwatch.setText(Long.toString(elapsedSeconds));
            }
            @Override
            public void start() {
                running.set(true);
                startTime = LocalTime.now();
                super.start();
            }
            @Override
            public void stop() {
                running.set(false);
                super.stop();
            }
        };
        
        this.getChildren().add(clockImg);
        this.getChildren().add(stopwatch);
        //this.setPadding(new Insets(24));
        //this.setMinWidth(240);
        this.setAlignment(Pos.CENTER);
    }
    
    protected void start() {
    	timer.start();
    }
    
    protected void stop() {
    	timer.stop();
    }
    
    protected long getElapsedSeconds() {
		return elapsedSeconds;
	}
}