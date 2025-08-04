package ParkingLot;

public class Car {
    public String id;
    public Slot slot;
    public Integer inTime;
    public Integer outTime;
    public PaymentStrategy paymentStrategy;
    public VehicleType vehicleType;

    public Car(String numPlate,VehicleType vehicleType){
        this.id = numPlate;
        this.slot = null;
        this.paymentStrategy = new BasicPaymentStrategy(this);
        this.vehicleType = vehicleType;
    }
}
