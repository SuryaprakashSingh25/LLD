package MovieBooking.Booking;

import MovieBooking.Screen.Seat;
import MovieBooking.Screen.Show;
import MovieBooking.enums.BookingState;

import java.util.List;

public class Booking {
    private final String id;
    private final Show show;
    private final List<Seat> seats;
    private final double amount;
    private BookingState state=BookingState.PENDING;
    private final long createdAt;

    public Booking(String id,Show show,List<Seat> seats,double amount){
        this.id=id;
        this.show=show;
        this.seats=seats;
        this.amount=amount;
        this.createdAt=System.currentTimeMillis();
    }

    public synchronized void confirm(){
        if(state==BookingState.PENDING){
            state=BookingState.CONFIRMED;
            for(Seat seat:seats){
                seat.book();
            }
        }
    }

    public synchronized void cancel(BookingState reason){
        if(state==BookingState.PENDING){
            state=reason;
            for(Seat seat:seats){
                seat.release();
            }
        }
    }

    public String getId() {
        return id;
    }

    public Show getShow() {
        return show;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public double getAmount() {
        return amount;
    }

    public BookingState getState() {
        return state;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}
