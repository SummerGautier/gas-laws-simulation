package animation;

import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import models.ParticleSystem;

public class ParticleAnimationService{
    private static ParticleAnimationService particleAnimationService;
    private final Timeline timeline = new Timeline();

    /**
     * Private constructor of particle animation service
     */
    private ParticleAnimationService(){
        System.out.println("ParticleAnimationService Instance Created");
    }

    /**
     * Get singleton instance of ParticleAnimationService
     * @return instance of ParticleAnimationService
     */
    public static ParticleAnimationService getInstance(){
        //if animation service was already instantiated, return existing instance
        if(particleAnimationService == null) {
            particleAnimationService = new ParticleAnimationService();
        }
        return particleAnimationService;
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
