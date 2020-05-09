package services;

import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import models.Particle;
import models.ParticleSystem;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

public class IdealGasAnimationService extends ParticleAnimationService {
    private static IdealGasAnimationService idealGasAnimationService;
    private final Timeline timeline = new Timeline();

    private IdealGasAnimationService(){
        super();
        System.out.println("IdealGasAnimationService Instance Created");
    }

    /**
     * Get singleton instance of IdealGasAnimatonService
     * @return instance of IdealGasAnimationService
     */
    public static IdealGasAnimationService getInstance(){
        //if animation service was already instantiated, return existing instance
        //or else return a new instance of the gas animation service
        if(idealGasAnimationService == null) {
            idealGasAnimationService = new IdealGasAnimationService();
        }
        return idealGasAnimationService;
    }

    /**
     * Start animation of particles
     * @param particleSystem, the system of particles to simulate
     * @param animationPane, the pane where the animation will be drawn
     */
    public void animate(ParticleSystem particleSystem, Pane animationPane){
        ArrayList<Circle> circlesList = new ArrayList<Circle>();
        Random random = new Random();
        int circleRadius = 5;

        while(circlesList.size() <= 10) {
            int startX = random.nextInt((int) animationPane.getWidth()) - circleRadius;
            int startY = random.nextInt((int) animationPane.getHeight()) - circleRadius;
            Circle circle = new Circle(startX, startY, circleRadius, Color.DARKRED);
            circle.setStroke(Color.RED);
            circlesList.add(circle);
        }
        animationPane.getChildren().addAll(circlesList);

        ListIterator<Circle> circlesIterator = circlesList.listIterator();
        while(circlesIterator.hasNext()) {
            Circle circle = circlesIterator.next();
            KeyValue kVMoveX = new KeyValue(circle.centerXProperty(), random.nextInt((int) animationPane.getWidth()) - circleRadius);
            KeyValue kVRotate = new KeyValue(circle.rotateProperty(), random.nextInt((int) animationPane.getHeight()) - circleRadius);

            KeyFrame keyFrame = new KeyFrame(Duration.millis(random.nextInt(2000) + 2000), kVMoveX, kVRotate);

            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();

    }

    /**
     * stop the current timeline animation
     * @param animationPane, pane where animation is running
     */
    public void stopAnimation(Pane animationPane){
        timeline.stop();
        animationPane.getChildren().clear();
    }
}
