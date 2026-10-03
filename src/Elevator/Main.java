package Elevator;

import Elevator.Dispatcher.OptimalDispatchStrategy;
import Elevator.Elevate.Direction;
import Elevator.Elevate.ElevatorController;

public class Main {
    public static void main(String[] args) {
        ElevatorController controller=new ElevatorController(2,new OptimalDispatchStrategy());
        controller.requestElevator(3, Direction.UP);
        controller.requestElevator(7,Direction.DOWN);
        int ticks=0;
        while(controller.hasActiveRequests() && ticks<15){
            System.out.printf("--- Tick %d ---\n", ++ticks);
            controller.step();
        }
    }
}
