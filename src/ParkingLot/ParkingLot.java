package ParkingLot;

import ParkingLot.Parking.ParkingFloor;
import ParkingLot.Parking.ParkingSpot;
import ParkingLot.Payment.Payment;
import ParkingLot.Payment.PaymentFactory;
import ParkingLot.Price.HourlyPricing;
import ParkingLot.Price.PricingStrategy;
import ParkingLot.Vehicles.Vehicle;
import ParkingLot.enums.PaymentType;
import ParkingLot.enums.SpotType;
import ParkingLot.enums.VehicleType;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static ParkingLot.enums.VehicleType.*;

public class ParkingLot {
    private static volatile ParkingLot instance;
    private final String name;
    private final List<ParkingFloor> floors=new CopyOnWriteArrayList<>();
    private final ConcurrentHashMap<String,Ticket> activeTickets=new ConcurrentHashMap<>();
    private PricingStrategy pricingStrategy=new HourlyPricing(5.0);

    private ParkingLot(String name){
        this.name=name;
    }

    public static ParkingLot getInstance(String name){
        if(instance==null){
            synchronized (ParkingLot.class){
                if(instance==null){
                    instance=new ParkingLot(name);
                }
            }
        }
        return instance;
    }

    public void addFloor(ParkingFloor floor){
        floors.add(floor);
    }

    public void setPricingStrategy(PricingStrategy strategy){
        this.pricingStrategy=strategy;
    }

    private boolean isSpotCompatible(VehicleType vehicleType, SpotType spotType){
        switch (vehicleType){
            case MOTORCYCLE: return spotType==SpotType.MOTORCYCLE || spotType==SpotType.COMPACT || spotType==SpotType.LARGE;
            case CAR: return spotType==SpotType.COMPACT || spotType==SpotType.LARGE;
            case TRUCK: return spotType==SpotType.LARGE;
            default: return false;
        }
    }

    public synchronized Ticket parkVehicle(Vehicle vehicle){
        for(ParkingFloor floor:floors){
            for(ParkingSpot spot: floor.getSpots()){
                if(spot.isAvailable() && isSpotCompatible(vehicle.getType(), spot.getType())){
                    if(spot.reserve(vehicle)){
                        Ticket ticket=new Ticket(vehicle,spot);
                        activeTickets.put(ticket.getTicketId(),ticket);
                        System.out.printf("[ParkingSuccess] %s (%s) parked at spot %s on floor %s. Ticket: %s\n",
                                vehicle.getType(), vehicle.getLicensePlate(), spot.getId(), floor.getName(), ticket.getTicketId());
                        return ticket;
                    }
                }
            }
        }
        System.out.printf("[ParkingFailed] No compatible spot available for %s (%s)\n", vehicle.getType(), vehicle.getLicensePlate());
        return null;
    }

    public synchronized boolean checkoutVehicle(String ticketId, PaymentType paymentType){
        Ticket ticket=activeTickets.get(ticketId);
        if(ticket==null){
            System.out.println("[CheckoutError] Ticket ID " + ticketId + " not found.");
            return false;
        }

        ticket.setExitTime(System.currentTimeMillis()+7200000);
        long duration=ticket.getExitTime()-ticket.getEntryTime();
        double fee=pricingStrategy.calculateFee(duration);
        ticket.setFee(fee);

        Payment payment= PaymentFactory.createPayment(paymentType,fee);
        if(payment.process()){
            ticket.pay();
            ticket.getSpot().release();
            activeTickets.remove(ticketId);
            System.out.printf("[CheckoutSuccess] Ticket %s resolved. Fee: $%.2f. Spot %s is vacant.\n",
                    ticketId, fee, ticket.getSpot().getId());
            return true;
        }
        return false;
    }
}
