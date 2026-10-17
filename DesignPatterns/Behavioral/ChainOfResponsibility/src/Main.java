// Chain of Responsibility demo: the request starts at Level 1 and moves down the chain
void main() {
    SupportHandler l1 = new Level1();
    SupportHandler l2 = new Level2();
    SupportHandler l3 = new Level3();

    l1.setNext(l2);
    l2.setNext(l3);

    l1.handle(2);       // Resolved by Level 2
    l1.handle(3);       // Resolved by Level 3
    l1.handle(5);       // Cannot resolve issue
}
