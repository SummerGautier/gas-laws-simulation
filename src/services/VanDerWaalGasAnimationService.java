package services;

import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import models.ParticleSystem;

import java.util.Random;

public class VanDerWaalGasAnimationService extends ParticleAnimationService {
    private static VanDerWaalGasAnimationService vanDerWaalGasAnimationService;
    private final Timeline timeline = new Timeline();

    private VanDerWaalGasAnimationService(){
        super();
        System.out.println("VanDerWaalGasAnimationService Instance Created");
    }

    /**
     * Get singleton instance of VanDer Waal Gas Animaton Service
     * @return instance of VanDerWaalGasAnimationService
     */
    public static VanDerWaalGasAnimationService getInstance(){
        //if animation service was already instantiated, return existing instance
        //or else return a new instance of the gas animation +service
        if(vanDerWaalGasAnimationService == null) {
            vanDerWaalGasAnimationService = new VanDerWaalGasAnimationService();
        }
        return vanDerWaalGasAnimationService;
    }

    /**
     * Start animation of particles
     * @param particleSystem, the system of particles to simulate
     * @param animationPane, the pane where the animation will be drawn
     */
    public void animate(ParticleSystem particleSystem, Pane animationPane){
        Random random = new Random();
        int circleRadius = 5;

        Circle circle = new Circle(75, 75, circleRadius, Color.ORANGE);
        circle.setStroke(Color.BLUEVIOLET);
        animationPane.getChildren().add(circle);

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);

        KeyValue kVMoveX = new KeyValue(circle.centerXProperty(), random.nextInt((int) animationPane.getWidth()) - circleRadius);
        KeyValue kVRotate = new KeyValue(circle.rotateProperty(), random.nextInt((int) animationPane.getHeight()) - circleRadius);
        KeyValue kVArcHeight = new KeyValue(circle.radiusProperty(), 60);
        KeyValue kVArcWidth = new KeyValue(circle.radiusProperty(), 60);

        KeyFrame keyFrame = new KeyFrame(Duration.millis(random.nextInt(2000) + 2000), kVMoveX, kVRotate, kVArcHeight, kVArcWidth);

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

    }

    /**
     * stop the current timeline animation
     * @param animationPane, the pane where the animation is running
     */
    public void stopAnimation(Pane animationPane){
        timeline.stop();
        animationPane.getChildren().clear();
    }
}
