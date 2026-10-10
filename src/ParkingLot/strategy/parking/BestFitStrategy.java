package ParkingLot.strategy.parking;

import ParkingLot.entities.ParkingFloor;
import ParkingLot.entities.ParkingSpot;
import ParkingLot.vehicle.Vehicle;

import java.util.List;
import java.util.Optional;

public class BestFitStrategy implements ParkingStrategy{
    @Override
    public Optional<ParkingSpot> findSpot(List<ParkingFloor> floors, Vehicle vehicle) {
        Optional<ParkingSpot> bestSpot=Optional.empty();

        for(ParkingFloor floor: floors){
            Optional<ParkingSpot> spotOnThisFloor=floor.findAvailableSpot(vehicle);
            if(spotOnThisFloor.isPresent()){
                if(bestSpot.isEmpty()){
                    bestSpot=spotOnThisFloor;
                }
                else{
                    if(spotOnThisFloor.get().getSpotSize().ordinal()<bestSpot.get().getSpotSize().ordinal()){
                        bestSpot=spotOnThisFloor;
                    }
                }
            }
        }
        return bestSpot;
    }
}
