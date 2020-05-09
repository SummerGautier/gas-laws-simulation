import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.ParticleSystem;
import services.IdealGasAnimationService;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //instantiating dashboard and scene properties
        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        primaryStage.setTitle("Gas Laws Interactive Simulation v1.0");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();

    }

    //start application
    public static void main(String[] args) { launch(args); }
}