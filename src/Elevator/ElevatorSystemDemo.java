package Elevator;

import Elevator.enums.Direction;

public class ElevatorSystemDemo {
    public static void main(String[] args) throws InterruptedException {
        int numElevators=2;
        ElevatorSystem elevatorSystem=ElevatorSystem.getInstance(numElevators);
        elevatorSystem.start();
        System.out.println("Elevator system started. ConsoleDisplay is observing.\n");
        elevatorSystem.requestElevator(5, Direction.UP);
        Thread.sleep(100);
        elevatorSystem.selectFloor(1, 10);
        Thread.sleep(200);
        elevatorSystem.requestElevator(3, Direction.DOWN);
        Thread.sleep(300);
        elevatorSystem.selectFloor(2, 1);
        System.out.println("\n--- Letting simulation run for 1 second ---");
        Thread.sleep(1000);
        elevatorSystem.shutdown();
        System.out.println("\n--- SIMULATION END ---");
    }
}
