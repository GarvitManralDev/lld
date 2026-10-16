import entities.Game;
import enums.Symbol;


void main() {
    Game game = new Game(3,"Garvit", Symbol.X,"Ayush", Symbol.O);

    game.makeMove(0, 0);  // X at (0,0)
    game.makeMove(1, 0);  // O at (1,0)
    game.makeMove(0, 1);  // X at (0,1)
    game.makeMove(1, 1);  // O at (1,1)
    game.makeMove(0, 2);  // X at (0,2) - Garvit Wins
}
