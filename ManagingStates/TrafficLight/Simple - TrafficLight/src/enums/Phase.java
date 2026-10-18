package enums;

// What the whole intersection is doing: one setting for all four lights
public enum Phase {
    NS_GREEN,       // N, S green  · E, W red
    NS_YELLOW,      // N, S yellow · E, W red
    EW_GREEN,       // E, W green  · N, S red
    EW_YELLOW       // E, W yellow · N, S red
}
