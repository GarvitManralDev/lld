package states;

import core.Intersection;
import enums.LightColor;

// NS_GREEN: N, S green  · E, W red
public class NorthSouthGreenState implements IntersectionState {

    @Override
    public void applyLights(Intersection intersection) {
        intersection.setColors(LightColor.GREEN, LightColor.RED);
    }

    @Override
    public int getDuration(Intersection intersection) {
        return intersection.getGreenDuration();
    }

    @Override
    public IntersectionState next() {
        return new NorthSouthYellowState();
    }

    @Override
    public String getName() {
        return "NS_GREEN";
    }
}
