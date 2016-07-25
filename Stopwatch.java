package Saper;

import java.time.Duration;
import java.time.LocalTime;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

class Stopwatch extends VBox {
	protected AnimationTimer timer = null;
	protected long elapsedSeconds;

	Stopwatch() {
        Label stopwatch = new Label("Time: 0");
        BooleanProperty running = new SimpleBooleanProperty(false);

        timer = new AnimationTimer() {

            private LocalTime startTime ;

            @Override
            public void handle(long now) {
            	elapsedSeconds = Duration.between(startTime, LocalTime.now()).getSeconds();
                stopwatch.setText("Time: " + elapsedSeconds);
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

//        Button startStop = new Button();
//        startStop.textProperty().bind(Bindings.when(running).then("Stop").otherwise("Start"));
//        startStop.setOnAction(e -> {
//            if (running.get()) {
//                timer.stop();
//            } else {
//                timer.start();
//            }
//        });
        
        this.getChildren().add(stopwatch);
//        this.getChildren().add(startStop);
        this.setPadding(new Insets(24));
        this.setMinWidth(240);
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