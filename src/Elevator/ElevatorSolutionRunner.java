package Elevator;

import SolutionRunner.SolutionRunner;

import java.util.*;

enum ElevatorState{
    OPEN,
    IDLE,
    MOVING_UP,
    MOVING_DOWN
}

class Display{
    void onFloorChange(Elevator elevator){
        System.out.printf("Elevator %d is at floor %d\n",elevator.id,elevator.currentFloor.lvl);
    }

    void onStateChange(Elevator elevator){
        System.out.printf("Elevator %d changed state to %s at floor %d\n",elevator.id,elevator.elevatorState.name(),elevator.currentFloor.lvl);
    }

}

interface FloorAlgorithm {
    Floor getNextFloor(Elevator elevator);

}
interface ElevatorAlgorithm {
    Elevator getElevator(Request request);

}
class LookNearestFloorAlgorithm implements FloorAlgorithm {
    @Override
    public Floor getNextFloor(Elevator elevator) {
        if(elevator.requestList.isEmpty()){
            return null;
        }
        return elevator.requestList.getFirst().requestedFloor;
    }

}
class BruteForceAlgorithm implements ElevatorAlgorithm{

    ArrayList<Elevator> elevatorList;
    public BruteForceAlgorithm(final ArrayList<Elevator> elevatorList){
        this.elevatorList = elevatorList;
    }

    @Override
    public Elevator getElevator(Request request) {
        int bestTime = Integer.MAX_VALUE;
        Elevator bestElevator = null;
        for(Elevator elevator: this.elevatorList){
            int currentTime = processRequestFor(elevator,request);
            if(currentTime < bestTime){
                bestTime = currentTime;
                bestElevator = elevator;
            }
        }
        return bestElevator;
    }

    int processRequestFor(final Elevator elevator,final Request request){
        Request clonedRequest = request.clone();
        Elevator cloneElevator = elevator.clone();
        cloneElevator.requestList.add(clonedRequest);
        int ans = 0;
        while (cloneElevator.requestList.contains(clonedRequest)){
            ans++;
            cloneElevator.step(false);
        }
        return ans;
    }

}

class Floor {
    int lvl;
    public Floor(int lvl){
        this.lvl = lvl;
    }
}

class Request implements Cloneable{
    Floor requestedFloor;
    Floor destinationFloor;
    boolean isInternalRequest;

    Request(Floor startFloor,Floor destinationFloor){
        this.isInternalRequest = false;
        this.requestedFloor = startFloor;
        this.destinationFloor = destinationFloor;
    }

    Request processRequest(){
        if(this.isInternalRequest){
            return null;
        }
        this.isInternalRequest = true;
        this.requestedFloor = this.destinationFloor;
        this.destinationFloor = null;
        return this;
    }

    @Override
    public Request clone() {
        try {
            return (Request) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

class Elevator implements Cloneable{
    int id;
    Floor currentFloor;
    ElevatorState elevatorState;
    ArrayList<Request> requestList;
    ArrayList<Display> displayList;
    FloorAlgorithm floorAlgorithm;
    HashMap<Integer,Floor> floorList;

    public Elevator(int id,Floor floor,HashMap<Integer,Floor> floorList,FloorAlgorithm floorAlgorithm){
        this.id = id;
        this.currentFloor = floor;
        this.floorAlgorithm = floorAlgorithm;
        this.floorList = floorList;
        elevatorState = ElevatorState.IDLE;
        requestList = new ArrayList<>();
        displayList = new ArrayList<>();
    }

    void addDisplay(Display display){
        displayList.add(display);
    }

    void notifyDisplay(boolean floorChange,boolean stateChange){
        for(Display display: displayList){
            if(floorChange){
                display.onFloorChange(this);
            }
            if (stateChange){
                display.onStateChange(this);
            }
        }
    }

    void step(boolean sentNotification){
        Floor initialFloor = this.currentFloor;
        ElevatorState initialState = this.elevatorState;
        List<Request> toRemove = new ArrayList<>();
        List<Request> toAdd = new ArrayList<>();
        for(final Request request: requestList){
            if(request.requestedFloor.lvl == this.currentFloor.lvl) {
                toRemove.add(request);
                if(!request.isInternalRequest){
                    toAdd.add(request.processRequest());
                }
            }
        }
        if(requestList.removeAll(toRemove)) this.elevatorState = ElevatorState.OPEN;
        requestList.addAll(toAdd);
        if(sentNotification) {
            if (initialState != ElevatorState.OPEN && this.elevatorState == ElevatorState.OPEN) {
                notifyDisplay(false, true);
                return;
            }
        }
        Floor nextFloor = floorAlgorithm.getNextFloor(this);
        if(nextFloor == null){
            this.elevatorState = ElevatorState.IDLE;
        } else if(nextFloor.lvl > this.currentFloor.lvl){
            this.currentFloor = floorList.get(initialFloor.lvl + 1);
            this.elevatorState = ElevatorState.MOVING_UP;
        } else {
            this.currentFloor = floorList.get(initialFloor.lvl - 1);
            this.elevatorState = ElevatorState.MOVING_DOWN;
        }

        boolean stateChange = false,floorChange = false;
        if(this.elevatorState != initialState) stateChange = true;
        if(this.currentFloor != initialFloor) floorChange = true;
        if(sentNotification) {
            notifyDisplay(floorChange, stateChange);
        }
    }

    void step(){
        step(true);
    }

    @Override
    public Elevator clone() {
        try {
            Elevator cloneElevator = (Elevator) super.clone();
            cloneElevator.requestList = (ArrayList<Request>) this.requestList.clone();
            for(int i = 0; i < cloneElevator.requestList.size(); i++){
                cloneElevator.requestList.set(i,requestList.get(i).clone());
            }
            return cloneElevator;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

class ElevatorController{
    ArrayList<Elevator> elevatorList;
    HashMap<Integer,Floor> floorList;
    ElevatorAlgorithm elevatorAlgorithm;
    Display display;
    public ElevatorController(int num_of_elevator,int num_of_floors){
        elevatorList = new ArrayList<>(num_of_elevator);
        floorList = new HashMap<>();
        display = new Display();
        for(int i = 0; i < num_of_floors; i++){
            floorList.put(i,new Floor(i));
        }
        for(int i = 0; i < num_of_elevator; i++){
            elevatorList.add(i,new Elevator(i,floorList.get(0),floorList,new LookNearestFloorAlgorithm()));
            elevatorList.get(i).addDisplay(this.display);
        }
        elevatorAlgorithm = new BruteForceAlgorithm(elevatorList);
    }

    void requestElevator(int startFloor,int destinationFloor){
        Request request = new Request(floorList.get(startFloor),floorList.get(destinationFloor));
        Elevator elevator = elevatorAlgorithm.getElevator(request);
        elevator.requestList.add(request);
        System.out.printf("Request for %d to %d is assigned to %d\n",startFloor,destinationFloor,elevator.id);
    }

    void step(){
        for(Elevator elevator:elevatorList){
            elevator.step();
        }
    }
}


public class ElevatorSolutionRunner implements SolutionRunner {
    public void run(){
        ElevatorController elevatorController = new ElevatorController(1,20);
        elevatorController.requestElevator(3,0);
        elevatorController.requestElevator(1,10);
        for(int j = 0; j < 100; j++) {
            elevatorController.step();
        }
    }
}
