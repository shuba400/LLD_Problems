package ParkingLot;

public class BasicRateStrategy implements RateStrategy {
    public Slot slot;
    public Integer rateMultiplier = 1;

    public BasicRateStrategy(final Slot slot){
        this.slot = slot;
    }

    @Override
    public Integer calculateRate() {
        final Car car = slot.occupiedCar;
        int inTime = car.inTime;
        int outTime = car.outTime;
        return (outTime - inTime)*rateMultiplier;
    }
}
