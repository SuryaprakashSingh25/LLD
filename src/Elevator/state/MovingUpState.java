package Elevator.state;

import Elevator.Elevator;
import Elevator.enums.Direction;
import Elevator.enums.RequestSource;
import Elevator.models.Request;

public class MovingUpState implements ElevatorState{
    @Override
    public void move(Elevator elevator) {
        if(elevator.getUpRequests().isEmpty()){
            elevator.setState(new IdleState());
            return;
        }
        Integer nextFloor=elevator.getUpRequests().first();
        elevator.setCurrentFloor(elevator.getCurrentFloor()+1);

        if(elevator.getCurrentFloor()==nextFloor){
            System.out.println("Elevator " + elevator.getId() + " stopped at floor " + nextFloor);
            elevator.getUpRequests().pollFirst();
        }
        if(elevator.getUpRequests().isEmpty()){
            elevator.setState(new IdleState());
        }
    }

    @Override
    public Direction getDirection() {
        return Direction.UP;
    }

    @Override
    public void addRequest(Elevator elevator, Request request) {
        if(request.getSource()== RequestSource.INTERNAL){
            if(request.getTargetFloor()> elevator.getCurrentFloor()){
                elevator.getUpRequests().add(request.getTargetFloor());
            }
            else{
                elevator.getDownRequests().add(request.getTargetFloor());
            }
            return;
        }
        if(request.getDirection()==Direction.UP && request.getTargetFloor()>= elevator.getCurrentFloor()){
            elevator.getUpRequests().add(request.getTargetFloor());
        }
        else if(request.getDirection()==Direction.DOWN){
            elevator.getDownRequests().add(request.getTargetFloor());
        }
    }
}
