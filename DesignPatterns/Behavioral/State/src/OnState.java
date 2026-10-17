public class OnState implements LightState {

    @Override
    public LightState press() {
        System.out.println("Light turned OFF");
        return new OffState();
    }
}
