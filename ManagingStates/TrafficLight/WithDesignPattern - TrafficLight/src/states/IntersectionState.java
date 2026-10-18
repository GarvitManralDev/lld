package states;

import core.Intersection;

// One phase of the intersection: which lights are on, how long it lasts, and what comes next
public interface IntersectionState {
    void applyLights(Intersection intersection);
    int getDuration(Intersection intersection);
    IntersectionState next();
    String getName();
}
