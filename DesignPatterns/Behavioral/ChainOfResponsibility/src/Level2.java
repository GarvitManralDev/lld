public class Level2 extends SupportHandler {

    @Override
    public void handle(int level) {
        if (level == 2) {
            System.out.println("Resolved by Level 2");
        } else if (next != null) {
            next.handle(level);
        }
    }
}
