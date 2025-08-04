package ParkingLot;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class ParkingLotController {
    public ArrayList<Slot> parkingLot;
    final Utils utils;
    int n,m;
    public  ParkingLotController(int n, int m){
        this.utils = new Utils();
        this.parkingLot = new ArrayList<>();
        this.n = n;
        this.m = m;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                parkingLot.add(new Slot(i,j,utils.generateVehicleType()));
                System.out.print(parkingLot.getLast().vehicleTypeAllowed + " ");
            }
            System.out.println();
        }
    }

    public void addCar(final Car car,final Integer epochInTime){
        int minDistance = n + m;
        car.inTime = epochInTime;
        Slot parkingSlot = null;

        for(Slot slot : parkingLot){
            if(slot.isOccupied()) continue;
            if(car.vehicleType == slot.vehicleTypeAllowed && slot.distance(0,0) < minDistance){
                minDistance = slot.distance(0,0);
                parkingSlot = slot;
            }
        }
        if(parkingSlot == null) {
            System.out.printf("Printing Slot is full for %s, Sorry :(\n",car.id);
            return;
        }
        System.out.printf("Slot Specified for Car %s : %s\n",car.id,parkingSlot.id);
        parkingSlot.addCar(car);
    }

    public void exitCar(final Car car,final Integer epochExitTime){
        car.outTime = epochExitTime;
        final Slot slot = car.slot;
        Integer rate = slot.rateStrategy.calculateRate();
        System.out.printf("Parking cost for cat %s is : %d\n",car.id,rate);
        System.out.println("Pending Payment...");
        if(car.paymentStrategy.makePayment()) {
            System.out.print("Payment is successful...");
        } else {
            System.out.println("Adding payment to backlog");
        }
        slot.emptySlot();
    }
}
