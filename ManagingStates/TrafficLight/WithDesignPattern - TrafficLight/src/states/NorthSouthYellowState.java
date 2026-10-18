package states;

import core.Intersection;
import enums.LightColor;

// NS_YELLOW: N, S yellow · E, W red
public class NorthSouthYellowState implements IntersectionState {

    @Override
    public void applyLights(Intersection intersection) {
        intersection.setColors(LightColor.YELLOW, LightColor.RED);
    }

    @Override
    public int getDuration(Intersection intersection) {
        return intersection.getYellowDuration();
    }

    @Override
    public IntersectionState next() {
        return new EastWestGreenState();
    }

    @Override
    public String getName() {
        return "NS_YELLOW";
    }
}
