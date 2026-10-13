package Elevator.state;

import Elevator.Elevator;
import Elevator.enums.Direction;
import Elevator.models.Request;

public interface ElevatorState {
    void move(Elevator elevator);
    void addRequest(Elevator elevator, Request request);
    Direction getDirection();
}
