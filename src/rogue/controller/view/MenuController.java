/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rogue.controller.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import rogue.app.Config;
import rogue.translations.Translations;

/**
 * FXML Controller class
 *
 * @author Thomas
 */
public class MenuController implements Config, Translations, Initializable {

    @FXML
    private ImageView menuImg;

    private Scene scene;

    private final int menuSize = 3;
    private int selectedMenu = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setScene(Scene scene) {
        this.scene = scene;
        setKeys();
    }

    private void setKeys() {
        this.scene.setOnKeyPressed((event) -> {
            switch (event.getCode()) {
                case UP:
                    selectMenuUp();
                    break;
                case DOWN:
                    selectMenuDown();
                    break;
                case ENTER:
                    enterMenu();
                    break;
                case ESCAPE:
                    System.exit(0);
                    break;
            }
            refreshMenu();
        });
    }

    public void setSelectedMenu(int selectedMenu) {
        if (selectedMenu >= 0 && selectedMenu < this.menuSize) {
            this.selectedMenu = selectedMenu;
            refreshMenu();
        }
    }

    private void selectMenuDown() {
        this.selectedMenu += 1;
        this.selectedMenu %= this.menuSize;
    }

    private void selectMenuUp() {
        this.selectedMenu += this.menuSize - 1;
        this.selectedMenu %= this.menuSize;
    }

    private void refreshMenu() {
        Image image = new Image("/rogue/img/Menu_" + this.selectedMenu + ".png");
        this.menuImg.setImage(image);
    }

    private void enterMenu() {
        switch (this.selectedMenu) {
            case 0:
                newGame();
                break;
            case 1:
                credits();
                break;
            case 2:
                quit();
                break;
        }
    }

    private void newGame() {
        try {
            Stage stage = ((Stage) this.scene.getWindow());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/rogue/view/NewGame.fxml"));
            loader.setResources(WORDS);

            Parent root = (Parent) loader.load();

            Scene newGame = new Scene(root);

            NewGameController newGameController = loader.getController();
            newGameController.setScene(newGame);

            stage.setScene(newGame);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Erreur de chargement" + ex.toString());
        }
    }

    private void credits() {
        try {
            Stage stage = ((Stage) this.scene.getWindow());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/rogue/view/Credits.fxml"));

            Parent root = (Parent) loader.load();

            Scene credits = new Scene(root);

            CreditsController creditsController = loader.getController();
            creditsController.setScene(credits);

            stage.setScene(credits);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Erreur de chargement");
        }
    }

    private void quit() {
        System.exit(0);
    }
}
