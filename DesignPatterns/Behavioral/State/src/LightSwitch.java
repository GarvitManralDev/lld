// Context: the only class that changes the state, the states decide what comes next
public class LightSwitch {
    private LightState state = new OffState();

    public void press() {
        state = state.press();
    }
}
