import entities.Game;
import entities.Ladder;
import entities.Player;
import entities.Snake;

void main() {

    // Making Players
    Queue<Player> players = new ArrayDeque<>();
    Player Garvit = new Player("Garvit");
    players.add(Garvit);
    Player Vaibhav = new Player("Vaibhav");
    players.add(Vaibhav);

    //Making Snakes
    List<Snake> snakes = new ArrayList<>();
    Snake snake1 = new Snake(67,10);
    snakes.add(snake1);
    Snake snake2 = new Snake(56,23);
    snakes.add(snake2);

    //Making Ladders
    List<Ladder> ladders = new ArrayList<>();
    Ladder ladder1 = new Ladder(15,100);
    ladders.add(ladder1);
    Ladder ladder2 = new Ladder(39,87);
    ladders.add(ladder2);

    Game game = new Game(players,snakes,ladders,10);
    game.play();
}
