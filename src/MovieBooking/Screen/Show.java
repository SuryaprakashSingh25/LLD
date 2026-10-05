package MovieBooking.Screen;

import MovieBooking.enums.SeatCategory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Show {
    private final String id;
    private final String movie;
    private final boolean isWeekend;
    private final Map<String,Seat> seats=new ConcurrentHashMap<>();

    public Show(String id,String movie,boolean isWeekend,int silverCount,int goldCount,int vipCount){
        this.id=id;
        this.movie=movie;
        this.isWeekend=isWeekend;
        int ind=1;
        for(int i=0;i<silverCount;i++){
            String sid="S"+ind++;
            seats.put(sid,new Seat(sid, SeatCategory.SILVER));
        }
        for(int i=0;i<goldCount;i++){
            String sid="S"+ind++;
            seats.put(sid,new Seat(sid,SeatCategory.GOLD));
        }
        for(int i=0;i<vipCount;i++){
            String sid="S"+ind++;
            seats.put(sid,new Seat(sid,SeatCategory.VIP));
        }
    }

    public String getId() {
        return id;
    }

    public String getMovie() {
        return movie;
    }

    public boolean isWeekend() {
        return isWeekend;
    }

    public Map<String, Seat> getSeats() {
        return seats;
    }
}
