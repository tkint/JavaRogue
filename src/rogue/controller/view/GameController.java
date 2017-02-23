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
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import rogue.model.boardobject.character.enemy.Enemy;
import rogue.app.Config;
import rogue.controller.BattleController;
import rogue.controller.MoveController;
import rogue.model.Board;
import rogue.model.boardobject.character.Character;
import rogue.model.boardobject.character.hero.Hero;
import rogue.model.enums.Characteristics;
import rogue.translations.Translations;

/**
 * FXML Controller class
 *
 * @author Thomas
 */
public class GameController implements Config, Translations, Initializable {

    @FXML
    private BorderPane body;

    private Scene scene;

    private HBox topPanel;
    private HBox bottomPanel;
    private VBox leftPanel;
    private VBox rightPanel;
    private GridPane boardPanel;
    private Board board;
    
    private MoveController moveController;
    private BattleController battleController;

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
                    this.moveController.moveCharacter(this.board.getHero(), "UP");
                    this.moveController.moveEnemies();
                    refresh();
                    break;
                case DOWN:
                    this.moveController.moveCharacter(this.board.getHero(), "DOWN");
                    this.moveController.moveEnemies();
                    refresh();
                    break;
                case LEFT:
                    this.moveController.moveCharacter(this.board.getHero(), "LEFT");
                    this.moveController.moveEnemies();
                    refresh();
                    break;
                case RIGHT:
                    this.moveController.moveCharacter(this.board.getHero(), "RIGHT");
                    this.moveController.moveEnemies();
                    refresh();
                    break;
                case ESCAPE:
                    menu();
                    break;
            }
        });
    }

    public void init(int level, Hero hero) {
        this.board = new Board(level, hero);
        this.moveController = new MoveController(this.board);
        this.battleController = new BattleController(this.board);
        refresh();
    }

    private void refresh() {
        displayBoardPanel();
        displayTopPanel();
        displayBottomPanel();
        displayLeftPanel();
        displayRightPanel();
    }

    private void displayTopPanel() {
        topPanel = new HBox();

        topPanel.setMinHeight(100);
        topPanel.setMaxHeight(100);
        topPanel.setStyle("-fx-background-color: white;");

        body.setTop(topPanel);
    }

    private void displayBottomPanel() {
        bottomPanel = new HBox();

        bottomPanel.setMinHeight(100);
        bottomPanel.setMaxHeight(100);
        bottomPanel.setStyle("-fx-background-color: white;");

        body.setBottom(bottomPanel);
    }

    private void displayLeftPanel() {
        leftPanel = new VBox();

        leftPanel.setMinWidth(100);
        leftPanel.setMaxWidth(100);
        leftPanel.setStyle("-fx-background-color: white;");

        body.setLeft(leftPanel);
    }

    private void displayRightPanel() {
        rightPanel = new VBox();

        rightPanel.setMinWidth(100);
        rightPanel.setMaxWidth(100);
        rightPanel.setStyle("-fx-background-color: white;");

        body.setRight(rightPanel);
    }

    private void displayBoardPanel() {
        boardPanel = new GridPane();

        boardPanel.setStyle("-fx-background-color: gray;");
        boardPanel.setAlignment(Pos.CENTER);
        boardPanel.setGridLinesVisible(true);

        for (int i = 0; i < this.board.getWidth(); i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setFillWidth(true);
            column.setHalignment(HPos.CENTER);
            column.setMinWidth(BOARD_CELL_WIDTH);
            column.setMaxWidth(BOARD_CELL_WIDTH);
            boardPanel.getColumnConstraints().add(column);
        }

        for (int i = 0; i < this.board.getHeight(); i++) {
            RowConstraints row = new RowConstraints();
            row.setFillHeight(true);
            row.setValignment(VPos.CENTER);
            row.setMinHeight(BOARD_CELL_HEIGHT);
            row.setMaxHeight(BOARD_CELL_HEIGHT);
            boardPanel.getRowConstraints().add(row);
        }

        for (int i = 0; i < this.board.getWidth(); i++) {
            for (int j = 0; j < this.board.getHeight(); j++) {
                Label label = new Label();
                label.setTextFill(Color.WHITE);
                if (this.board.getObjectInPosition(i, j) != null) {
                    label.setText(this.board.getObjectInPosition(i, j).getName());
                }
                boardPanel.add(label, i, j);
            }
        }

        body.setCenter(boardPanel);
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
