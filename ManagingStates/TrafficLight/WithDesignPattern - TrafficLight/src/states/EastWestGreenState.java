package states;

import core.Intersection;
import enums.LightColor;

// EW_GREEN: E, W green  · N, S red
public class EastWestGreenState implements IntersectionState {

    @Override
    public void applyLights(Intersection intersection) {
        intersection.setColors(LightColor.RED, LightColor.GREEN);
    }

    @Override
    public int getDuration(Intersection intersection) {
        return intersection.getGreenDuration();
    }

    @Override
    public IntersectionState next() {
        return new EastWestYellowState();
    }

    @Override
    public String getName() {
        return "EW_GREEN";
    }
}
