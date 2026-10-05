package MovieBooking;

import MovieBooking.Booking.Booking;
import MovieBooking.Booking.BookingService;
import MovieBooking.Screen.Show;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("=== MOVIE TICKET BOOKING (BMS) SIMULATION ===");
        BookingService service=new BookingService(1500);
        Show avengersWeekend=new Show("Show-101", "Avengers: Endgame", true, 5,3,2);
        System.out.println("Show Created: " + avengersWeekend.getMovie() + " (Weekend: " + avengersWeekend.isWeekend() + ")");
        Booking bookingA=service.createBooking(avengersWeekend, Arrays.asList("S1","S2"));
        System.out.println("User A Reserved S1, S2 -> Booking ID: " + bookingA.getId() + ", Amount: $" + bookingA.getAmount());
        ExecutorService executor= Executors.newFixedThreadPool(2);
        Future<Booking> userBTask=executor.submit(() -> service.createBooking(avengersWeekend,Arrays.asList("S2","S3")));
        Booking bookingB=userBTask.get();
        System.out.println("User B Attempt to reserve S2, S3 -> Result: " + (bookingB != null ? bookingB.getId() : "FAILED (Conflict - Rollback worked)"));
        boolean confirmedA=service.confirmBooking(bookingA.getId());
        System.out.println("User A Payment Confirmation -> " + (confirmedA ? "SUCCESS" : "FAILED"));
        Booking bookingC=service.createBooking(avengersWeekend,Arrays.asList("S4","S5"));
        System.out.println("User C Reserved S4, S5 -> Booking ID: " + bookingC.getId() + ", Pending Payment...");
        System.out.println("Waiting 2 seconds for User C's hold to expire...");
        Thread.sleep(2000);
        service.cleanupExpiredBookings();
        System.out.println("Booking C State: " + bookingC.getState());
        Booking bookingD=service.createBooking(avengersWeekend,Arrays.asList("S4","S5"));
        System.out.println("User D tries S4, S5 (after C expired) -> Result: " + (bookingD != null ? "SUCCESS (Booking ID: " + bookingD.getId() + ")" : "FAILED"));
        executor.shutdown();
    }
}
