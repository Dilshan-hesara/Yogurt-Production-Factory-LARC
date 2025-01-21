package lk.edu.yogurtproduction.yogurtproductionitsolution;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AppInitializer extends Application {
    @Override
    public void start(Stage stage) throws Exception {

      //  FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/WelcomePage.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/Dashboad.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Yogurt Production");
        stage.setResizable(false);

        Image image = new Image(getClass().getResourceAsStream("/images/7-app_icon.png"));
        stage.getIcons().add(image);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }

    
}

