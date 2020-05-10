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
        ArrayList<Particle> particles = particleSystem.getParticles();
        Random random = new Random();
        
        ListIterator<Particle> particleIterator = particles.listIterator();
        //init particle pos, speed, color etc.
        while(particleIterator.hasNext()) {
            Particle particle = particleIterator.next();
            particle.setFill(Color.RED);
            particle.setCenterX(random.nextInt((int)animationPane.getWidth()) + particle.getRadius());

            particle.setCenterY((int) random.nextInt((int) animationPane.getHeight()) + particle.getRadius());
            HashMap<String, Integer> speedProperties = new HashMap<String, Integer>();
            int speedInit = random.nextInt(5) + 1;
            speedProperties.put("speedX", speedInit);
            speedProperties.put("speedY", speedInit);
            particle.getProperties().putAll(speedProperties);
            particle.setStroke(Color.RED);
        }
        animationPane.getChildren().addAll(particles);

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
                        ListIterator<Particle> particleIterator = particles.listIterator();
                        while(particleIterator.hasNext()) {
                            Particle particle = particleIterator.next();

                            //bounds detection
                            if((particle.getCenterX() > animationPane.getWidth() - (particle.getRadius() * 2)) || (particle.getCenterX() <= particle.getRadius())){
                                particle.getProperties().replace("speedX", (int) particle.getProperties().get("speedX") * -1);
                            }
                            if((particle.getCenterY() > animationPane.getHeight() - (particle.getRadius() * 2)) || (particle.getCenterY() <= particle.getRadius())){
                                particle.getProperties().replace("speedY", (int) particle.getProperties().get("speedY") * -1);
                            }

                            int speedX = (int) particle.getProperties().get("speedX");
                            int speedY = (int) particle.getProperties().get("speedY");

                            particle.setCenterX(particle.getCenterX() + speedX);
                            particle.setCenterY(particle.getCenterY() + speedY);
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
