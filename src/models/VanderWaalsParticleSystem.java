package models;

import javafx.scene.paint.Color;

public class VanderWaalsParticleSystem extends ParticleSystem{
    public VanderWaalsParticleSystem(){
        super();
        this.color = Color.DARKBLUE;
        this.strokeColor = Color.WHEAT;
    }
    @Override
    public void calculateParticleVelocities() {

    }
}
