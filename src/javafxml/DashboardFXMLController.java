package javafxml;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import models.IdealParticleSystem;
import models.ParticleSystem;
import animation.ParticleAnimationService;
import models.VanderWaalsParticleSystem;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardFXMLController implements Initializable {
    private ParticleAnimationService particleAnimationService;
    private IdealParticleSystem idealParticleSystem;
    private VanderWaalsParticleSystem vanderWaalsParticleSystem;
    private ParticleSystem particleSystem;
    private enum PlayBackStatus { STARTED, STOPPED}

    @FXML private Button playBackBtn;
    @FXML private Pane animationPane;
    @FXML private CheckBox enableVanderWaalCheckBox;

    public DashboardFXMLController(){
        //initialize singletons here
        this.particleAnimationService = particleAnimationService.getInstance();
        this.idealParticleSystem = new IdealParticleSystem();
        this.vanderWaalsParticleSystem = new VanderWaalsParticleSystem();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /* Initialize Controls Here */
        //playback button
        this.playBackBtn.setOnAction(new playBackHandler());
        this.playBackBtn.setUserData(PlayBackStatus.STOPPED);

        //enable vander waals checkbox
        this.enableVanderWaalCheckBox.setDisable(false);

    }

    @FXML
    public void startSimulation(){
        //instantiate correct particle system with the proper number of particles
        particleSystem = (enableVanderWaalCheckBox.isSelected())? vanderWaalsParticleSystem : idealParticleSystem;
        particleSystem.setNumberOfParticles(150);
        //use animation service to start particle animation
        particleAnimationService.animate(particleSystem, this.animationPane);
        //update status of play button
        this.playBackBtn.setUserData(PlayBackStatus.STARTED);
        this.playBackBtn.setStyle("-fx-background-color: indianred");
        this.playBackBtn.setText("Stop Simulation");
        //disable switching of animation service
        this.enableVanderWaalCheckBox.setDisable(true);
    }

    @FXML
    public void stopSimulation(){
        //stop animation
        particleAnimationService.stopAnimation(this.animationPane);
        //update status of play button
        this.playBackBtn.setUserData(PlayBackStatus.STOPPED);
        this.playBackBtn.setStyle("-fx-background-color: lightgreen");
        this.playBackBtn.setText("Start Simulation");
        //enable switching of animation service
        this.enableVanderWaalCheckBox.setDisable(false);
    }

    /* EVENT HANDLERS */
    private class playBackHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent e) {
            Object userData = playBackBtn.getUserData();
            if (PlayBackStatus.STOPPED.equals(userData)) {
                startSimulation();
            } else if (PlayBackStatus.STARTED.equals(userData)) {
                stopSimulation();
            }
        }
    }
}
