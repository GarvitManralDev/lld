import core.Intersection;
import core.TrafficControlSystem;
import enums.Direction;
import enums.LightColor;

void main() {
    // Scenario 1: one intersection goes through a full cycle
    System.out.println("========== SCENARIO 1: Full cycle (green 3s, yellow 1s) ==========");
    TrafficControlSystem city = new TrafficControlSystem();
    Intersection first = city.addIntersection(1, 3, 1);     // starts in NS_GREEN
    for (int t = 0; t < 8 ; t++) {   // NS_GREEN -> NS_YELLOW -> EW_GREEN -> EW_YELLOW -> NS_GREEN
        System.out.println("--- t = " + (t + 1) + "s ---");
        city.tick();
    }
    System.out.println("Intersection 1 is back in " + first.getPhase());

    // Scenario 2: two intersections with different timings run independently
    System.out.println("\n========== SCENARIO 2: Two intersections ==========");
    TrafficControlSystem twoJunctions = new TrafficControlSystem();
    twoJunctions.addIntersection(1, 3, 1);
    twoJunctions.addIntersection(2, 2, 1);
    for (int t = 1; t <= 4; t++) {
        twoJunctions.tick();
        System.out.println("t = " + t + "s  ->  #1 " + twoJunctions.getIntersection(1).getPhase()
                + ",  #2 " + twoJunctions.getIntersection(2).getPhase());
    }

    // Scenario 3: Error cases
    System.out.println("\n========== SCENARIO 4: Error cases ==========");
    try {
        city.addIntersection(1, 5, 2);      // id 1 already used
    } catch (IllegalArgumentException e) {
        System.out.println("Error: " + e.getMessage());
    }
    try {
        city.getIntersection(9);                             // no such intersection
    } catch (IllegalArgumentException e) {
        System.out.println("Error: " + e.getMessage());
    }
    try {
        city.addIntersection(3, 0, 1);                 // durations must be positive
    } catch (IllegalArgumentException e) {
        System.out.println("Error: " + e.getMessage());
    }
}
