package ParkingLot;

public class BasicPaymentStrategy implements PaymentStrategy{
    final Car car;
    public BasicPaymentStrategy(Car car){
        this.car = car;
    }

    @Override
    public boolean makePayment() {
        System.out.printf("Car %s made a successful payment\n",car.id);
        return  true;
    }
}
