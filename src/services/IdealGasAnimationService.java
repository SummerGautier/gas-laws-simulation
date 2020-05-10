package services;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import models.Particle;
import models.ParticleSystem;

import java.util.ArrayList;
import java.util.HashMap;
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
        //init particles
        while(circlesList.size() <= 50) {
            int startX = random.nextInt((int) animationPane.getWidth()) + circleRadius;
            int startY = random.nextInt((int) animationPane.getHeight()) +  circleRadius;
            Circle circle = new Circle(startX, startY, circleRadius, Color.DARKRED);
            HashMap<String, Integer> circleProperties = new HashMap<String, Integer>();
            int speedInit = random.nextInt(5) + 1;
            circleProperties.put("speedX", speedInit);
            circleProperties.put("speedY", speedInit);
            circle.getProperties().putAll(circleProperties);
            circle.setStroke(Color.RED);
            circlesList.add(circle);
        }
        animationPane.getChildren().addAll(circlesList);

        //set game loop to run continously (unless we call timeline.stop())
        timeline.setCycleCount(Timeline.INDEFINITE);

        //animation start time
        final long timeStart = System.currentTimeMillis();

        /*
         * Particle Updates Calculated Every 60 seconds In This KeyFrame
         */
        KeyFrame kf = new KeyFrame(
                //60 Frames Per Second
                Duration.seconds(0.017),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        //updating particle position
                        ListIterator<Circle> circlesIterator = circlesList.listIterator();
                        while(circlesIterator.hasNext()) {
                            Circle circle = circlesIterator.next();

                            //bounds detection
                            if((circle.getCenterX() > animationPane.getWidth() - (circleRadius * 2)) || (circle.getCenterX() <= circleRadius)){
                                circle.getProperties().replace("speedX", (int) circle.getProperties().get("speedX") * -1);
                            }
                            if((circle.getCenterY() > animationPane.getHeight() - (circleRadius * 2)) || (circle.getCenterY() <= circleRadius)){
                                circle.getProperties().replace("speedY", (int) circle.getProperties().get("speedY") * -1);
                            }

                            int speedX = (int) circle.getProperties().get("speedX");
                            int speedY = (int) circle.getProperties().get("speedY");

                            circle.setCenterX(circle.getCenterX() + speedX);
                            circle.setCenterY(circle.getCenterY() + speedY);
                        }
                    }
                }
        );

        //add key frame to simulation loop
        timeline.getKeyFrames().add(kf);
        //begin simulation loop
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
