package services;

import javafx.scene.layout.Pane;
import models.ParticleSystem;

public abstract class ParticleAnimationService {
    public abstract void animate(ParticleSystem particleSystem, Pane animationPane);

    public abstract void stopAnimation(Pane animationPane);
}

