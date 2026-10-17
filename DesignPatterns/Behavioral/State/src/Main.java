// State pattern demo: the same press() does different things depending on the current state
void main() {
    LightSwitch light = new LightSwitch();

    light.press();      // Light turned ON
    light.press();      // Light turned OFF
    light.press();      // Light turned ON
}
