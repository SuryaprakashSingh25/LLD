package MovieBooking.Screen;

import MovieBooking.enums.SeatCategory;
import MovieBooking.enums.SeatStatus;

import java.util.concurrent.locks.ReentrantLock;

public class Seat {
    private final String id;
    private final SeatCategory category;
    private SeatStatus status=SeatStatus.AVAILABLE;
    private long reservationTime=0;
    private final ReentrantLock lock=new ReentrantLock();

    public Seat(String id, SeatCategory category){
        this.id=id;
        this.category=category;
    }

    public String getId(){
        return id;
    }

    public SeatCategory getCategory(){
        return category;
    }

    public ReentrantLock getLock(){
        return lock;
    }

    public SeatStatus getStatus(long ttlMs){
        if(status==SeatStatus.RESERVED && (System.currentTimeMillis()-reservationTime)>ttlMs){
            status=SeatStatus.AVAILABLE;
            reservationTime=0;
        }
        return status;
    }

    public boolean reserve(long ttlMs){
        if(getStatus(ttlMs)==SeatStatus.AVAILABLE){
            status=SeatStatus.RESERVED;
            reservationTime=System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public void book(){
        if(status==SeatStatus.RESERVED){
            status=SeatStatus.BOOKED;
            reservationTime=0;
        }
    }

    public void release(){
        if(status==SeatStatus.RESERVED){
            status=SeatStatus.AVAILABLE;
            reservationTime=0;
        }
    }
}
