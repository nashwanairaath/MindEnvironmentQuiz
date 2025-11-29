package MindEnvQuizApp;

import javafx.application.Application;
import javafx.stage.Stage;
import MindEnvQuizApp.controller.MainMenu;

public class MainApp extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Mind & Environment Quiz");
        
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);
        
        MainMenu mainMenu = new MainMenu(primaryStage);
        mainMenu.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
