package Elevator.state;

import Elevator.Elevator;
import Elevator.enums.Direction;
import Elevator.models.Request;

public class IdleState implements ElevatorState{
    @Override
    public void move(Elevator elevator) {
        if(!elevator.getUpRequests().isEmpty()){
            elevator.setState(new MovingUpState());
        }
        else if(!elevator.getDownRequests().isEmpty()){
            elevator.setState(new MovingDownState());
        }
    }

    @Override
    public void addRequest(Elevator elevator, Request request) {
        if(request.getTargetFloor()> elevator.getCurrentFloor()){
            elevator.getUpRequests().add(request.getTargetFloor());
        }
        else if(request.getTargetFloor()< elevator.getCurrentFloor()){
            elevator.getDownRequests().add(request.getTargetFloor());
        }
    }

    @Override
    public Direction getDirection() {
        return Direction.IDLE;
    }
}
