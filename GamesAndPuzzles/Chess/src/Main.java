import enums.Color;
import models.Game;
import models.Player;
import pieces.Coordinate;

// Fool's Mate: the fastest checkmate in chess, black wins in 2 moves.
// Rows: 0 = black's back row, 7 = white's back row. Columns: 0 = a-file ... 7 = h-file.
void main() {
    Player white = new Player("Garvit", Color.WHITE);
    Player black = new Player("Ayush", Color.BLACK);
    Game game = new Game(white, black);

    game.makeMove(new Coordinate(6, 5), new Coordinate(5, 5));  // White pawn  f2 -> f3
    game.makeMove(new Coordinate(1, 4), new Coordinate(3, 4));  // Black pawn  e7 -> e5
    game.makeMove(new Coordinate(6, 6), new Coordinate(4, 6));  // White pawn  g2 -> g4
    game.makeMove(new Coordinate(0, 3), new Coordinate(4, 7));  // Black queen d8 -> h4, checkmate
}
