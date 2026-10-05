package MovieBooking.Booking;

import MovieBooking.Payment.DynamicPricingStrategy;
import MovieBooking.Payment.PricingStrategy;
import MovieBooking.Screen.Seat;
import MovieBooking.Screen.Show;
import MovieBooking.enums.BookingState;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BookingService {
    private final Map<String, Booking> bookings=new ConcurrentHashMap<>();
    private final PricingStrategy pricingStrategy=new DynamicPricingStrategy();
    private final long ttlMs;

    public BookingService(long ttlMs){
        this.ttlMs=ttlMs;
    }

    public Booking createBooking(Show show, List<String> seatIds){
        List<Seat> targetSeats=new ArrayList<>();
        for(String id:seatIds){
            Seat s=show.getSeats().get(id);
            if(s==null){
                return null;
            }
            targetSeats.add(s);
        }
        targetSeats.sort(Comparator.comparing(Seat::getId));
        List<Seat> acquired=new ArrayList<>();
        boolean success=true;
        for(Seat seat:targetSeats){
            seat.getLock().lock();
            try{
                if(seat.reserve(ttlMs)){
                    acquired.add(seat);
                }
                else{
                    success=false;
                    break;
                }
            }
            finally {
                seat.getLock().unlock();
            }
        }
        if(!success){
            for(Seat seat:acquired){
                seat.getLock().lock();
                try{
                    seat.release();
                }
                finally {
                    seat.getLock().unlock();
                }
            }
            return null;
        }
        double totalAmount=0;
        for(Seat seat: targetSeats){
            totalAmount+=pricingStrategy.calculatePrice(seat.getCategory(),show.isWeekend());
        }
        String bookingId="BKG-"+ UUID.randomUUID().toString().substring(0,8).toUpperCase();
        Booking booking=new Booking(bookingId,show,targetSeats,totalAmount);
        bookings.put(bookingId,booking);
        return booking;
    }

    public boolean confirmBooking(String bookingId){
        Booking booking=bookings.get(bookingId);
        if(booking==null){
            return false;
        }
        synchronized (booking){
            if(booking.getState()!= BookingState.PENDING){
                return false;
            }
            if(System.currentTimeMillis()-booking.getCreatedAt()>ttlMs){
                booking.cancel(BookingState.EXPIRED);
                return false;
            }
            booking.confirm();
            return true;
        }
    }

    public void cancelBooking(String bookingId){
        Booking booking=bookings.get(bookingId);
        if(booking==null){
            return;
        }
        synchronized (booking){
            booking.cancel(BookingState.CANCELLED);
        }
    }

    public void cleanupExpiredBookings(){
        for(Booking booking:bookings.values()){
            synchronized (booking){
                if(booking.getState()==BookingState.PENDING && (System.currentTimeMillis()-booking.getCreatedAt()>ttlMs)){
                    booking.cancel(BookingState.EXPIRED);
                }
            }
        }
    }
}
