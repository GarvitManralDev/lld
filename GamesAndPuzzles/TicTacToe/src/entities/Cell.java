package entities;
import enums.Symbol;

public class Cell{
    private Symbol symbol;

    Cell(){
        this.symbol = Symbol.E;
    }

    public void setSymbol(Symbol symbol){
        this.symbol = symbol;
    }

    public Symbol getSymbol(){
        return this.symbol;
    }

    public boolean isEmpty(){
        return this.symbol == Symbol.E;
    }
}
