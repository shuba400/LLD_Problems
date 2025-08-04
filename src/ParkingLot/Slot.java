package ParkingLot;

public class Slot {
    public Car occupiedCar;
    public String id;
    public int cx,cy;
    public RateStrategy rateStrategy;
    public VehicleType vehicleTypeAllowed;

    public Slot(int x,int y,VehicleType vehicleType){
        occupiedCar = null;
        this.cx = x;
        this.cy = y;
        this.id = String.format("%d %d",x,y);
        rateStrategy = new BasicRateStrategy(this);
        this.vehicleTypeAllowed = vehicleType;
    }

    public boolean isOccupied(){
        return (occupiedCar != null);
    }

    public void addCar(Car car){
        this.occupiedCar = car;
        car.slot = this;
    }

    public int distance(int x,int y){
        return Math.abs(x - cx) + Math.abs(y - cy);
    }

    public void emptySlot(){
        this.occupiedCar = null;
    }
}
