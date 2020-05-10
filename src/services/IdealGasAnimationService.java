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
        Random random = new Random();
        
        ListIterator<Particle> particleIterator = particleSystem.getParticles().listIterator();
        //init particle pos, speed, color etc.
        while(particleIterator.hasNext()) {
            Particle particle = particleIterator.next();
            particle.setFill(Color.RED);
            particle.setXPos(random.nextInt((int)animationPane.getWidth()));
            particle.setYPos((int) random.nextInt((int) animationPane.getHeight()));
            HashMap<String, Integer> speedProperties = new HashMap<String, Integer>();
            particle.setVelocity(random.nextInt(5) + 1);

        }

        animationPane.getChildren().addAll(particleSystem.getParticles());

        //set game loop to run continously (unless we call timeline.stop())
        timeline.setCycleCount(Timeline.INDEFINITE);

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
                        ListIterator<Particle> particleIterator = particleSystem.getParticles().listIterator();
                        while(particleIterator.hasNext()) {
                            Particle particle = particleIterator.next();

                            //bounds detection
                            if((particle.getXPos() > animationPane.getWidth() - particle.getWidth()) || (particle.getXPos() <= 0)){
                                particle.setXVelocity(particle.getXVelocity() * -1);
                            }
                            if((particle.getYPos() > animationPane.getHeight() - particle.getHeight()) || (particle.getYPos() <= 0)){
                                particle.setYVelocity(particle.getYVelocity() * -1);
                            }

                            particle.setXPos(particle.getXPos() + particle.getXVelocity());
                            particle.setYPos(particle.getYPos() + particle.getYVelocity());
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
