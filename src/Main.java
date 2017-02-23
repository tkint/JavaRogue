/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rogue.app.Config;
import rogue.controller.view.MenuController;
import rogue.translations.Translations;

/**
 *
 * @author Thomas
 */
public class Main extends Application implements Config, Translations {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/rogue/view/Menu.fxml"));

        Parent root = (Parent) loader.load();

        Scene scene = new Scene(root);

        MenuController menuController = loader.getController();
        menuController.setScene(scene);
        menuController.setSelectedMenu(0);

        stage.setResizable(false);
        stage.setWidth(Config.APP_WIDTH);
        stage.setHeight(Config.APP_HEIGHT);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
