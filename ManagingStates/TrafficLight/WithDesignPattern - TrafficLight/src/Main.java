import core.Intersection;
import core.TrafficControlSystem;
import enums.Direction;
import enums.LightColor;

// Runs the city for some seconds, printing the time before each tick
void runFor(TrafficControlSystem city, int seconds, int startTime) {
    for (int t = startTime; t < startTime + seconds; t++) {
        System.out.println("--- t = " + (t + 1) + "s ---");
        city.tick();
    }
}

void main() {
    TrafficControlSystem city = TrafficControlSystem.getInstance();     // the one system for the whole city

    // Scenario 1: one intersection goes through a full cycle
    System.out.println("========== SCENARIO 1: Full cycle (green 3s, yellow 1s) ==========");
    Intersection first = city.addIntersection(1, 3, 1);     // starts in NS_GREEN
    runFor(city, 8, 0);                                      // NS_GREEN -> NS_YELLOW -> EW_GREEN -> EW_YELLOW -> NS_GREEN
    System.out.println("Intersection 1 is back in " + first.getPhase());

    // Scenario 2: a second intersection with different timings runs independently in the same city
    System.out.println("\n========== SCENARIO 2: Two intersections ==========");
    city.addIntersection(2, 2, 1);
    for (int t = 1; t <= 4; t++) {
        city.tick();
        System.out.println("t = " + t + "s  ->  #1 " + city.getIntersection(1).getPhase()
                + ",  #2 " + city.getIntersection(2).getPhase());
    }

    // Scenario 3: safety check, the two axes are never both allowed to go
    System.out.println("\n========== SCENARIO 3: Safety check over 50 seconds ==========");
    Intersection junction = city.addIntersection(3, 4, 2);
    boolean safe = true;
    java.io.PrintStream console = System.out;
    System.setOut(new java.io.PrintStream(java.io.OutputStream.nullOutputStream()));   // hide monitor logs
    for (int t = 1; t <= 50; t++) {
        city.tick();
        boolean northSouthGo = junction.getLight(Direction.NORTH).getColor() != LightColor.RED;
        boolean eastWestGo = junction.getLight(Direction.EAST).getColor() != LightColor.RED;
        if (northSouthGo && eastWestGo) {
            safe = false;
        }
    }
    System.setOut(console);
    System.out.println("N-S and E-W never non-red together: " + safe);

    // Scenario 4: Singleton and error cases
    System.out.println("\n========== SCENARIO 4: Singleton and error cases ==========");
    System.out.println("Same system everywhere: " + (TrafficControlSystem.getInstance() == city));
    try {
        city.addIntersection(1, 5, 2);                       // id 1 already used
    } catch (IllegalArgumentException e) {
        System.out.println("Error: " + e.getMessage());
    }
    try {
        city.getIntersection(9);                             // no such intersection
    } catch (IllegalArgumentException e) {
        System.out.println("Error: " + e.getMessage());
    }
    try {
        city.addIntersection(4, 0, 1);                       // durations must be positive
    } catch (IllegalArgumentException e) {
        System.out.println("Error: " + e.getMessage());
    }
}
