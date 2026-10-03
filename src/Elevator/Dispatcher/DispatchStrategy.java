package Elevator.Dispatcher;

import Elevator.Elevate.Elevator;
import Elevator.Elevate.Request;

import java.util.List;

public interface DispatchStrategy {
    Elevator selectElevator(List<Elevator> elevators, Request request);
}
