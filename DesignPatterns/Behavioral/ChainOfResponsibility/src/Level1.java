public class Level1 extends SupportHandler {

    @Override
    public void handle(int level) {
        if (level == 1) {
            System.out.println("Resolved by Level 1");
        } else if (next != null) {
            next.handle(level);
        }
    }
}
