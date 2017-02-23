/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rogue.controller;

import rogue.model.Board;
import rogue.model.boardobject.character.Character;
import rogue.model.enums.AttackTypes;
import static rogue.model.enums.AttackTypes.MAGICAL;
import rogue.model.enums.Characteristics;

/**
 *
 * @author Thomas
 */
public class BattleController {

    private Board board;

    public BattleController(Board board) {
        this.board = board;
    }

    public void hitCharacter(Character origin, Character target, AttackTypes attackType) {
        if (isInRange(origin, target)) {
            origin.addCharacteristic(Characteristics.CURRENT_HEALTH, -calculateDamages(origin, target, attackType));
        }
    }

    private int calculateDamages(Character origin, Character target, AttackTypes attackType) {
        int damages = 0;
        switch (attackType) {
            case PHYSICAL:
                damages += origin.getFullCharacteristic(Characteristics.STRENGHT);
                break;
            case MAGICAL:
                damages += origin.getFullCharacteristic(Characteristics.INTELLIGENCE);
                break;
            case HYBRID:
                damages += origin.getFullCharacteristic(Characteristics.STRENGHT);
                damages += origin.getFullCharacteristic(Characteristics.INTELLIGENCE);
                damages /= 2;
                break;
        }
        return damages;
    }

    private boolean isInRange(Character origin, Character target) {
        boolean inRange = false;
        int range = origin.getFullCharacteristic(Characteristics.PERCEIVE) - target.getFullCharacteristic(Characteristics.STEALTH);
        int i = origin.getPosX() - range;
        int j = origin.getPosY() - range;
        while (i <= origin.getPosX() + range && !inRange) {
            while (j <= origin.getPosY() + range && !inRange) {
                if (i == target.getPosX() && j == target.getPosY()) {
                    inRange = true;
                }
                j++;
            }
            i++;
        }
        return inRange;
    }
}
