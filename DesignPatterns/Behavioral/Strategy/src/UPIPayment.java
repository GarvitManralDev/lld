public class UPIPayment implements PaymentStrategy{

    @Override
    public void pay(double amount ){
        System.out.println("Amount "+ amount + " has been paid via UPI.");
    }
}
