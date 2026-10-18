package core;

import java.util.LinkedHashMap;
import java.util.Map;

// Singleton: one traffic control system for the city. It owns every intersection and the monitor they all report to
public class TrafficControlSystem {
    private static TrafficControlSystem instance;

    private final Map<Integer, Intersection> intersections = new LinkedHashMap<>();   // id -> intersection, in insertion order
    private final CentralMonitor monitor = new CentralMonitor();

    // synchronized: two threads can't both see null and create two systems
    public static synchronized TrafficControlSystem getInstance() {
        if (instance == null) {
            instance = new TrafficControlSystem();
        }
        return instance;
    }

    public Intersection addIntersection(int id, int greenDuration, int yellowDuration) {
        if (intersections.containsKey(id)) {
            throw new IllegalArgumentException("Intersection " + id + " already exists");
        }
        Intersection intersection = new Intersection(id, greenDuration, yellowDuration, monitor);
        intersections.put(id, intersection);
        return intersection;
    }

    // One second passes for every intersection; each one runs its own cycle independently
    public void tick() {
        for (Intersection intersection : intersections.values()) {
            intersection.tick();
        }
    }

    public Intersection getIntersection(int id) {
        Intersection intersection = intersections.get(id);
        if (intersection == null) {
            throw new IllegalArgumentException("No intersection " + id);
        }
        return intersection;
    }
}
