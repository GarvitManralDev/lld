public class Level3 extends SupportHandler {

    @Override
    public void handle(int level) {
        if (level == 3) {
            System.out.println("Resolved by Level 3");
        } else {
            System.out.println("Cannot resolve issue");
        }
    }
}
