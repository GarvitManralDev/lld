package models;

import enums.Direction;
import enums.LightColor;

// The signal facing one direction at an intersection
public class TrafficLight {
    private final Direction direction;
    private LightColor color = LightColor.RED;      // every light starts red (safe default)

    public TrafficLight(Direction direction) {
        this.direction = direction;
    }

    public void setColor(LightColor color) {
        this.color = color;
    }

    public LightColor getColor() {
        return color;
    }

    public Direction getDirection() {
        return direction;
    }
}
