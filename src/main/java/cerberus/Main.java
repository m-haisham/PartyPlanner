package cerberus;

import cerberus.database.Database;
import cerberus.party.Duration;
import cerberus.party.Person;
import cerberus.party.Venue;
import cerberus.party.types.Birthday;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    public static Database database = new Database(new File("parties.db"));

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/base.fxml"));
        primaryStage.setTitle("code");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

//        System.out.println(database.getCelebrations().find().size());
    }
}
