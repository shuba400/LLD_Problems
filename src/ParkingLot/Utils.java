package ParkingLot;

import java.util.Random;

public class Utils {
    Random random = new Random();

    public VehicleType generateVehicleType(){
        VehicleType[] value = VehicleType.values();
        return value[random.nextInt(value.length)];
    }
}
