package org.javaacademy.wonderfield.wheel;

import java.util.Random;

import static org.javaacademy.wonderfield.Game.RANDOM;
import static org.javaacademy.wonderfield.wheel.SectorType.DOUBLE_SCORE;
import static org.javaacademy.wonderfield.wheel.SectorType.POINTS;
import static org.javaacademy.wonderfield.wheel.SectorType.SKIP_TURN;

/**
 * Барабан
 */
public class RoundWheel {
    private static final int SCORE_STEP = 100;
    private static final int SKIP_TURN_SECTOR_NUMBER = 14;
    private static final int DOUBLE_SCORE_SECTOR_NUMBER = 13;
    private static final int COUNT_SECTORS = 14;

    /**
     * Вращение барабана
     */
    public RoundWheelValue rotateWheel() {
        int sectorNumber = RANDOM.nextInt(COUNT_SECTORS) + 1;
        if (sectorNumber == SKIP_TURN_SECTOR_NUMBER) {
            return new RoundWheelValue(0, SKIP_TURN);
        } else if (sectorNumber == DOUBLE_SCORE_SECTOR_NUMBER) {
            return new RoundWheelValue(0, DOUBLE_SCORE);
        }
        return new RoundWheelValue(sectorNumber * SCORE_STEP, POINTS);
    }
}
