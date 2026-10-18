package core;

import enums.Direction;
import enums.LightColor;

// Hears about every light change in the city
public class CentralMonitor {

    public void logChange(int intersectionId, Direction direction, LightColor color) {
        System.out.println("[Monitor] Intersection " + intersectionId + ": " + direction + " -> " + color);
    }
}
