package ParkingLot;

/*
    10k-30k
    1 entrance/1 exit

    //n*m grid
    //entrance at 0,0 (non parking spot)
    //

 */

public class ParkingLotRunner {

    public void run(){
        ParkingLotController parkingLotController = new ParkingLotController(2,2);
        Car carA = new Car("CARA",VehicleType.BIKE);
        Car carB = new Car("CARB",VehicleType.CAR);
        Car carC = new Car("CARC",VehicleType.EV);
        Car carD = new Car("CarD",VehicleType.CAR);
        Car carE = new Car("CarE",VehicleType.TRUCK);

        parkingLotController.addCar(carA,0);
        parkingLotController.addCar(carB,1);
        parkingLotController.addCar(carC,2);
        parkingLotController.addCar(carD,3);
        parkingLotController.addCar(carE,3);
        parkingLotController.exitCar(carA,2);
        parkingLotController.addCar(carE,3);
        parkingLotController.exitCar(carB,3);

    }

}
