/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rogue.controller.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import rogue.app.Config;
import rogue.model.boardobject.character.hero.Hero;
import rogue.model.enums.Blessings;
import rogue.model.enums.HeroClasses;
import rogue.translations.Translations;

/**
 * FXML Controller class
 *
 * @author Thomas
 */
public class NewGameController implements Config, Translations, rogue.model.translations.Translations, Initializable {

    @FXML
    private AnchorPane anchorPane;

    private Scene scene;

    private int step = 0;
    private int index = 0;
    private int menuSize = 0;

    private String name;
    private HeroClasses classe;
    private Blessings blessing;
    private int level;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setScene(Scene scene) {
        this.scene = scene;
        setKeys();
        chooseNameScene();
    }

    private void setKeys() {
        this.scene.setOnKeyPressed((event) -> {
            switch (event.getCode()) {
                case ENTER:
                    switch (step) {
                        case 0:
                            chooseName();
                            break;
                        case 1:
                            chooseHeroClass();
                            break;
                        case 2:
                            chooseHeroBlessing();
                            break;
                        case 3:
                            chooseLevel();
                            break;
                    }
                    break;
                case UP:
                    if (step > 0) {
                        selectMenuUp();
                        refreshSelect();
                    }
                    break;
                case DOWN:
                    if (step > 0) {
                        selectMenuDown();
                        refreshSelect();
                    }
                    break;
                case ESCAPE:
                    menu();
                    break;
            }

        });
    }

    private void chooseNameScene() {
        Label label = new Label(Translations.WORDS.getString("CHOOSE_HERO_NAME"));
        label.setFont(Font.font(36));
        label.setTextFill(Color.WHITE);

        TextField textField = new TextField();
        textField.setId("nameField");
        textField.setAlignment(Pos.CENTER);
        textField.setFont(Font.font("System", FontWeight.BOLD, 36));
        textField.setStyle("-fx-background: transparent;"
                + "-fx-background-color: transparent;"
                + "-fx-text-inner-color: #339966");
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >= HERO_NAME_SIZE || (newValue.length() > 0 && !newValue.substring(newValue.length() - 1).matches("[a-zA-Z]"))) {
                ((StringProperty) observable).setValue(oldValue);
            }
        });

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label, textField);
        vBox.setFillWidth(true);
        vBox.setLayoutX(150);
        vBox.setLayoutY(200);
        vBox.setSpacing(10);

        anchorPane.getChildren().add(vBox);
    }

    private void chooseName() {
        TextField nameField;
        if ((nameField = (TextField) scene.lookup("#nameField")) != null) {
            if (!nameField.getText().isEmpty()) {
                name = nameField.getText();
                chooseHeroClassScene();
                step++;
            }
        }
    }

    private void chooseHeroClassScene() {
        anchorPane.getChildren().clear();

        index = 0;
        menuSize = HeroClasses.values().length;

        Label label = new Label(Translations.WORDS.getString("CHOOSE_HERO_CLASS"));
        label.setFont(Font.font(36));
        label.setTextFill(Color.WHITE);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(label);
        vBox.setFillWidth(true);
        vBox.setLayoutX(200);
        vBox.setLayoutY(100);
        vBox.setSpacing(10);

        int i = 0;
        for (HeroClasses c : HeroClasses.values()) {
            Label l = new Label(HEROCLASSES.getString(c.name()));
            l.setFont(Font.font(36));
            if (i == 0) {
                l.setTextFill(Paint.valueOf("#339966"));
            } else {
                l.setTextFill(Color.WHITE);
            }
            vBox.getChildren().add(l);
            i++;
        }

        anchorPane.getChildren().add(vBox);
    }

    private void chooseHeroClass() {
        int i = 0;
        for (HeroClasses c : HeroClasses.values()) {
            if (i == index) {
                classe = c;
            }
            i++;
        }
        chooseHeroBlessingScene();
        step++;
    }

    private void chooseHeroBlessingScene() {
        anchorPane.getChildren().clear();

        index = 0;
        menuSize = Blessings.values().length;

        Label label = new Label(Translations.WORDS.getString("CHOOSE_HERO_BLESSING"));
        label.setFont(Font.font(36));
        label.setTextFill(Color.WHITE);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(label);
        vBox.setFillWidth(true);
        vBox.setLayoutX(150);
        vBox.setLayoutY(100);
        vBox.setSpacing(10);

        int i = 0;
        for (Blessings b : Blessings.values()) {
            Label l = new Label(BLESSINGS.getString(b.name()));
            l.setFont(Font.font(36));
            if (i == 0) {
                l.setTextFill(Paint.valueOf("#339966"));
            } else {
                l.setTextFill(Color.WHITE);
            }
            vBox.getChildren().add(l);
            i++;
        }

        anchorPane.getChildren().add(vBox);
    }

    private void chooseHeroBlessing() {
        int i = 0;
        for (Blessings b : Blessings.values()) {
            if (i == index) {
                blessing = b;
            }
            i++;
        }
        chooseLevelScene();
        step++;
    }

    private void chooseLevelScene() {
        anchorPane.getChildren().clear();

        index = 0;
        menuSize = rogue.Config.LEVELS.length;

        Label label = new Label(Translations.WORDS.getString("CHOOSE_LEVEL"));
        label.setFont(Font.font(36));
        label.setTextFill(Color.WHITE);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(label);
        vBox.setFillWidth(true);
        vBox.setLayoutX(200);
        vBox.setLayoutY(100);
        vBox.setSpacing(10);
        
        for (int i = 0; i < rogue.Config.LEVELS.length; i++) {
            Label l = new Label(Integer.toString(i));
            l.setFont(Font.font(36));
            if (i == 0) {
                l.setTextFill(Paint.valueOf("#339966"));
            } else {
                l.setTextFill(Color.WHITE);
            }
            vBox.getChildren().add(l);
        }

        anchorPane.getChildren().add(vBox);
    }

    private void chooseLevel() {
        level = index;
        launch();
    }

    private void selectMenuDown() {
        index += 1;
        index %= menuSize;
    }

    private void selectMenuUp() {
        index += menuSize - 1;
        index %= menuSize;
    }

    private void refreshSelect() {
        VBox vBox = (VBox) anchorPane.getChildren().get(0);
        for (int i = 1; i < vBox.getChildren().size(); i++) {
            Label label = (Label) vBox.getChildren().get(i);
            if (i == index + 1) {
                label.setTextFill(Paint.valueOf("#339966"));
            } else {
                label.setTextFill(Color.WHITE);
            }
        }
    }

    private void launch() {
        try {
            Stage stage = ((Stage) this.scene.getWindow());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/rogue/view/Game.fxml"));

            Parent root = (Parent) loader.load();

            Scene game = new Scene(root);

            GameController gameController = loader.getController();
            gameController.init(level, new Hero(name, classe, blessing));
            gameController.setScene(game);

            stage.setScene(game);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Erreur de chargement");
        }
    }

    private void menu() {
        try {
            Stage stage = ((Stage) this.scene.getWindow());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/rogue/view/Menu.fxml"));

            Parent root = (Parent) loader.load();

            Scene menu = new Scene(root);

            MenuController menuController = loader.getController();
            menuController.setScene(menu);
            menuController.setSelectedMenu(0);

            stage.setScene(menu);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Erreur de chargement");
        }
    }
}
