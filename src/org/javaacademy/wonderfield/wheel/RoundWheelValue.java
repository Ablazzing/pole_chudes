package org.javaacademy.wonderfield.wheel;

/**
 * Результат вращения барабана
 */
public class RoundWheelValue {
    private final int points;
    private final SectorType type;

    public RoundWheelValue(int points, SectorType type) {
        this.points = points;
        this.type = type;
    }

    public int getPoints() {
        return points;
    }

    public SectorType getType() {
        return type;
    }
}
