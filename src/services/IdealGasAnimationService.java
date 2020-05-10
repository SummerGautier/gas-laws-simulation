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
        //init starting position of particles
        particleSystem.init(animationPane.getWidth(), animationPane.getHeight());
        animationPane.getChildren().addAll(particleSystem.getParticles());

        //set animation loop to run continuously (unless we call timeline.stop())
        timeline.setCycleCount(Timeline.INDEFINITE);

        //Particle Updates Calculated Every 60 seconds In This KeyFrame
        KeyFrame kf = new KeyFrame(Duration.seconds(0.017), event -> {
                    //updating particle position
                    particleSystem.updateParticlePositions(animationPane.getWidth(), animationPane.getHeight());
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
