void main() {

    PaymentService p = new PaymentService();

    // 1. Card Strategy
    p.setStrategy(new CardPayment());
    p.pay(500);

    // 2. UPI Strategy
    p.setStrategy(new UPIPayment());
    p.pay(600);

}
