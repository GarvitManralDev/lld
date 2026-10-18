package core;

import enums.Direction;
import enums.LightColor;
import enums.Phase;
import models.TrafficLight;

import java.util.EnumMap;
import java.util.Map;

// One junction: four lights that change together, one phase at a time
public class Intersection {
    private final int id;
    private final Map<Direction, TrafficLight> lights;
    private Phase phase;
    private final int greenDuration;        // seconds
    private final int yellowDuration;       // seconds
    private int secondsInPhase = 0;
    private final CentralMonitor monitor;

    public Intersection(int id, int greenDuration, int yellowDuration, CentralMonitor monitor) {
        if (greenDuration <= 0 || yellowDuration <= 0) {
            throw new IllegalArgumentException("Durations must be positive");
        }
        this.id = id;
        this.greenDuration = greenDuration;
        this.yellowDuration = yellowDuration;
        this.monitor = monitor;
        this.phase = Phase.NS_GREEN;

        this.lights = new EnumMap<>(Direction.class);
        for (Direction direction : Direction.values()) {
            lights.put(direction, new TrafficLight(direction));     // all start red
        }
        applyPhase();                                               // first phase: N, S green
    }

    // The phase decides the color of all four lights
    private void applyPhase() {
        switch (phase) {
            case NS_GREEN:  setColors(LightColor.GREEN, LightColor.RED); break;
            case NS_YELLOW: setColors(LightColor.YELLOW, LightColor.RED); break;
            case EW_GREEN:  setColors(LightColor.RED, LightColor.GREEN); break;
            case EW_YELLOW: setColors(LightColor.RED, LightColor.YELLOW); break;
        }
    }

    // Turns the stopping axis red before the other axis goes green, so the two never overlap
    private void setColors(LightColor northSouth, LightColor eastWest) {
        if (northSouth == LightColor.RED) {
            setColor(Direction.NORTH, northSouth);
            setColor(Direction.SOUTH, northSouth);
            setColor(Direction.EAST, eastWest);
            setColor(Direction.WEST, eastWest);
        } else {
            setColor(Direction.EAST, eastWest);
            setColor(Direction.WEST, eastWest);
            setColor(Direction.NORTH, northSouth);
            setColor(Direction.SOUTH, northSouth);
        }
    }

    // Changes one light and tells the monitor, only if the color is actually different
    private void setColor(Direction direction, LightColor color) {
        TrafficLight light = lights.get(direction);
        if (light.getColor() != color) {
            light.setColor(color);
            monitor.logChange(id, direction, color);
        }
    }

    // Advances this intersection by one second
    public void tick() {
        secondsInPhase++;
        if (secondsInPhase >= durationOf(phase)) {
            phase = nextPhase();
            secondsInPhase = 0;
            applyPhase();
        }
    }

    private Phase nextPhase() {
        switch (phase) {
            case NS_GREEN:  return Phase.NS_YELLOW;
            case NS_YELLOW: return Phase.EW_GREEN;
            case EW_GREEN:  return Phase.EW_YELLOW;
            default:        return Phase.NS_GREEN;
        }
    }

    private int durationOf(Phase phase) {
        if (phase == Phase.NS_GREEN || phase == Phase.EW_GREEN) {
            return greenDuration;
        }
        return yellowDuration;
    }

    public int getId() {
        return id;
    }

    public Phase getPhase() {
        return phase;
    }

    public TrafficLight getLight(Direction direction) {
        return lights.get(direction);
    }
}
