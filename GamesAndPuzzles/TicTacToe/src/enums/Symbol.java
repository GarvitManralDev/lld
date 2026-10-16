package enums;

public enum Symbol{
    X('X'),
    O('O'),
    E('-');

    private final char displayChar;

    Symbol(char displayChar){
        this.displayChar = displayChar;
    }

    public char getDisplayChar(){
        return this.displayChar;
    }
}
