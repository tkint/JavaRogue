/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rogue.controller;

import rogue.model.Board;
import rogue.model.boardobject.BoardObject;
import rogue.model.boardobject.character.Character;
import rogue.model.boardobject.character.enemy.Enemy;
import rogue.model.boardobject.character.hero.Hero;
import rogue.model.enums.Characteristics;

/**
 *
 * @author Thomas
 */
public class MoveController {

    private Board board;

    public MoveController(Board board) {
        this.board = board;
    }

    public void moveCharacter(Character character, String direction) {
        if (character != null) {
            switch (direction) {
                case "UP":
                    if (this.board.getObjectInPosition(character.getPosX(), character.getPosY() - 1) == null) {
                        character.moveUp();
                    }
                    break;
                case "DOWN":
                    if (this.board.getObjectInPosition(character.getPosX(), character.getPosY() + 1) == null) {
                        character.moveDown();
                    }
                    break;
                case "LEFT":
                    if (this.board.getObjectInPosition(character.getPosX() - 1, character.getPosY()) == null) {
                        character.moveLeft();
                    }
                    break;
                case "RIGHT":
                    if (this.board.getObjectInPosition(character.getPosX() + 1, character.getPosY()) == null) {
                        character.moveRight();
                    }
                    break;
                default:
                    switch((int) (Math.random() * 4)) {
                        case 0:
                            moveCharacter(character, "DOWN");
                            break;
                        case 1:
                            moveCharacter(character, "RIGHT");
                            break;
                        case 2:
                            moveCharacter(character, "LEFT");
                            break;
                        case 3:
                            moveCharacter(character, "UP");
                            break;
                    }
                    break;
            }
        } else {
            System.out.println("Character null!");
        }
    }

    public void moveEnemies() {
        Hero hero = this.board.getHero();
        for (Enemy enemy : this.board.getEnemies()) {
            int range = hero.getCharacteristic(Characteristics.STEALTH) - enemy.getCharacteristic(Characteristics.PERCEIVE);
            String direction;
            if ((direction = getCloserDirection(enemy, hero, 1)) != null) {
                moveCharacter(enemy, direction);
            }
        }
    }

    private String getCloserDirection(BoardObject b1, BoardObject b2, int range) {
        String direction = "";

        int distanceX = b2.getPosX() - b1.getPosX();
        int distanceY = b2.getPosY() - b1.getPosY();

        for (int i = b1.getPosX() - range; i <= b1.getPosX() + range; i++) {
            for (int j = b1.getPosY() - range; j <= b1.getPosY() + range; j++) {
                if (i == b2.getPosX() && j == b2.getPosY()) {
                    if (distanceX == 0) {
                        if (distanceY > 0) {
                            direction = "DOWN";
                        } else {
                            direction = "UP";
                        }
                    } else if (distanceX > 0) {
                        if (distanceY == 0) {
                            direction = "RIGHT";
                        } else if (distanceY > 0) {
                            if (Math.abs(distanceX) < Math.abs(distanceY)) {
                                direction = "DOWN"; // Bas
                            } else {
                                direction = "RIGHT"; // Droite
                            }
                        } else {
                            if (Math.abs(distanceX) < Math.abs(distanceY)) {
                                direction = "UP"; // Haut
                            } else {
                                direction = "RIGHT"; // Droite
                            }
                        }
                    } else {
                        if (distanceY == 0) {
                            direction = "LEFT";
                        } else if (distanceY > 0) {
                            if (Math.abs(distanceX) < Math.abs(distanceY)) {
                                direction = "DOWN"; // Bas
                            } else {
                                direction = "LEFT"; // Gauche
                            }
                        } else {
                            if (Math.abs(distanceX) < Math.abs(distanceY)) {
                                direction = "UP"; // Haut
                            } else {
                                direction = "LEFT"; // Gauche
                            }
                        }
                    }
                }
            }
        }

        System.out.println("----------------------------------------------------------------");
        System.out.println(b1.getName() + " : " + b1.getPosX() + " | " + b1.getPosY() + " -|- " + b2.getName() + " : " + b2.getPosX() + " | " + b2.getPosY());
        System.out.println("L'objet " + b1.getName() + " est situé à " + distanceX + " sur X et " + distanceY + " sur Y de " + b2.getName());

        return direction;
    }
}
