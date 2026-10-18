package core;

import enums.Direction;
import enums.LightColor;
import models.TrafficLight;
import states.IntersectionState;
import states.NorthSouthGreenState;

import java.util.EnumMap;
import java.util.Map;

// One junction: four lights that change together. The current state decides the lights, duration and next phase
public class Intersection {
    private final int id;
    private final Map<Direction, TrafficLight> lights;
    private IntersectionState state;
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
        this.state = new NorthSouthGreenState();

        this.lights = new EnumMap<>(Direction.class);
        for (Direction direction : Direction.values()) {
            lights.put(direction, new TrafficLight(direction));     // all start red
        }
        state.applyLights(this);                                    // first phase: N, S green
    }

    // Turns the stopping axis red before the other axis goes green, so the two never overlap
    // Called by the states
    public void setColors(LightColor northSouth, LightColor eastWest) {
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
        if (secondsInPhase >= state.getDuration(this)) {
            state = state.next();
            secondsInPhase = 0;
            state.applyLights(this);
        }
    }

    public int getGreenDuration() {
        return greenDuration;
    }

    public int getYellowDuration() {
        return yellowDuration;
    }

    public String getPhase() {
        return state.getName();
    }

    public TrafficLight getLight(Direction direction) {
        return lights.get(direction);
    }
}
