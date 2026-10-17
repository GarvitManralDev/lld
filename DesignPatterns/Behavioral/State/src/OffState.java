public class OffState implements LightState {

    @Override
    public LightState press() {
        System.out.println("Light turned ON");
        return new OnState();
    }
}
