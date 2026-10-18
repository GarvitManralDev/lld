package states;

import core.Intersection;
import enums.LightColor;

// EW_YELLOW: E, W yellow · N, S red
public class EastWestYellowState implements IntersectionState {

    @Override
    public void applyLights(Intersection intersection) {
        intersection.setColors(LightColor.RED, LightColor.YELLOW);
    }

    @Override
    public int getDuration(Intersection intersection) {
        return intersection.getYellowDuration();
    }

    @Override
    public IntersectionState next() {
        return new NorthSouthGreenState();
    }

    @Override
    public String getName() {
        return "EW_YELLOW";
    }
}
