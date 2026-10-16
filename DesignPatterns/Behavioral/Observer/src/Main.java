void main() {

    Subject s1 = new Subject();

    // 1. Register flow
    Observer1 o1 = new Observer1();
    Observer2 o2 = new Observer2();

    s1.registerObserver(o1);
    s1.registerObserver(o2);

    s1.notifyAllObservers(); // Both get notified

    // 2. Unregister flow
    s1.unregisterObserver(o1);

    s1.notifyAllObservers(); // Only Observer2 gets notified
}