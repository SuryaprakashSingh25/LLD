package Elevator.Dispatcher;

import Elevator.Elevate.Direction;
import Elevator.Elevate.Elevator;
import Elevator.Elevate.Request;

import java.util.List;

public class OptimalDispatchStrategy implements DispatchStrategy{
    @Override
    public Elevator selectElevator(List<Elevator> elevators, Request request) {
        Elevator best=null;
        int minCost=Integer.MAX_VALUE;
        for(Elevator e:elevators){
            int cost=calculateCost(e,request);
            if(cost<minCost){
                minCost=cost;
                best=e;
            }
        }
        return best!=null?best:elevators.get(0);
    }

    private int calculateCost(Elevator e,Request r){
        int currentFloor=e.getCurrentFloor();
        Direction dir=e.getDirection();
        int target=r.getTargetFloor();
        if(dir==Direction.IDLE){
            return Math.abs(currentFloor-target);
        }
        else if(dir==Direction.UP && target>=currentFloor && r.getDirection()==Direction.UP){
            return target-currentFloor;
        }
        else if(dir==Direction.DOWN && target<=currentFloor && r.getDirection()==Direction.DOWN){
            return currentFloor-target;
        }
        else{
            return Math.abs(currentFloor-target)+100;
        }
    }
}
