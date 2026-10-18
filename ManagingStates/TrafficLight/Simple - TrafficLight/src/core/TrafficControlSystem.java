package core;

import java.util.LinkedHashMap;
import java.util.Map;

// The city: owns every intersection and the one monitor they all report to
public class TrafficControlSystem {
    private final Map<Integer, Intersection> intersections = new LinkedHashMap<>();   // id -> intersection, in insertion order
    private final CentralMonitor monitor = new CentralMonitor();

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
