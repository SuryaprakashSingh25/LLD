package Elevator.Elevate;

import Elevator.Dispatcher.DispatchStrategy;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ElevatorController {
    public final List<Elevator> elevators;
    private final DispatchStrategy strategy;

    public ElevatorController(int elevatorCount, DispatchStrategy strategy){
        this.elevators=new CopyOnWriteArrayList<>();
        for(int i=1;i<=elevatorCount;i++){
            elevators.add(new Elevator(i));
        }
        this.strategy=strategy;
    }

    public void requestElevator(int floor,Direction direction){
        Request request=new Request(floor,direction);
        Elevator selected=strategy.selectElevator(elevators,request);
        System.out.printf("[Controller] Dispatched Elevator %d to floor %d (%s)\n", selected.getId(), floor, direction);
        selected.addRequests(floor);
    }

    public void step(){
        for(Elevator e:elevators){
            e.step();
        }
    }

    public boolean hasActiveRequests(){
        for(Elevator e:elevators){
            if(e.hasRequests()){
                return true;
            }
        }
        return false;
    }

    public List<Elevator> getElevators(){
        return elevators;
    }
}
