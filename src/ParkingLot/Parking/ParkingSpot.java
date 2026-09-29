package ParkingLot.Parking;

import ParkingLot.Vehicles.Vehicle;
import ParkingLot.enums.SpotType;

public class ParkingSpot {
    private final String id;
    private final SpotType type;
    private Vehicle currentVehicle;
    private boolean isFree=true;

    public ParkingSpot(String id, SpotType type){
        this.id=id;
        this.type=type;
    }

    public synchronized boolean isAvailable(){
        return isFree;
    }

    public synchronized boolean reserve(Vehicle vehicle){
        if(!isFree){
            return false;
        }
        this.currentVehicle=vehicle;
        isFree=false;
        return true;
    }

    public synchronized void release(){
        this.currentVehicle=null;
        this.isFree=true;
    }

    public String getId(){
        return id;
    }

    public SpotType getType(){
        return type;
    }

    public Vehicle getCurrentVehicle(){
        return currentVehicle;
    }
}
