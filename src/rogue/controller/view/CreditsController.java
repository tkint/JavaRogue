/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rogue.controller.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import rogue.app.Config;
import rogue.translations.Translations;

/**
 * FXML Controller class
 *
 * @author Thomas
 */
public class CreditsController implements Config, Translations, Initializable {

    @FXML
    private VBox creditsList;

    private Scene scene;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setScene(Scene scene) {
        this.scene = scene;
        setKeys();
        setText();
        animate();
    }

    private void setKeys() {
        this.scene.setOnKeyPressed((event) -> {
            switch (event.getCode()) {
                case ESCAPE:
                    menu();
                    break;
            }
        });
    }

    private void setText() {
        Text text = new Text(Config.APP_NAME);
        text.setFill(Paint.valueOf("#339966"));
        text.setFont(Font.font("System", FontWeight.BOLD, 50));
        creditsList.getChildren().add(text);
        
        String[] texts = Translations.getTranslations(CREDITS, "");
        
        for (int i = 0; i < texts.length; i++) {
            if (i % 2 == 0) {
                creditsList.getChildren().add(new Text());
            }
            Text t = new Text(texts[i]);
            t.setFill(Color.WHITE);
            creditsList.getChildren().add(t);
        }
        
        creditsList.setAlignment(Pos.TOP_CENTER);
        creditsList.setFillWidth(true);
    }

    public void animate() {
        Path path = new Path();
        path.getElements().add(new MoveTo(Config.APP_WIDTH / 2, Config.APP_HEIGHT + 200));
        path.getElements().add(new VLineTo(-Config.APP_HEIGHT / 2));
        
        PathTransition transition = new PathTransition(Duration.millis(20000), path, creditsList);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.setCycleCount(1);
        transition.play();
        transition.setOnFinished((event) -> {
            menu();
        });
    }

    private void menu() {
        try {
            Stage stage = ((Stage) this.scene.getWindow());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/rogue/view/Menu.fxml"));

            Parent root = (Parent) loader.load();

            Scene menu = new Scene(root);

            MenuController menuController = loader.getController();
            menuController.setScene(menu);
            menuController.setSelectedMenu(1);

            stage.setScene(menu);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Erreur de chargement");
        }
    }
}
