import org.junit.Test;
import static org.junit.Assert.assertEquals;
import venue.*;

public class HappyPathTests {
   TicketServiceImpl ticketService;

   @Test
   public void ReservationSuccess() {
      ticketService = new TicketServiceImpl(100, 5);
      SeatHold hold = ticketService.findAndHoldSeats(4, "ugo@gmail.com");
      int holdId = hold.getId();

      try {
        Thread.sleep(1 * 1000);
      } catch (InterruptedException e) {
        System.out.println("Test Thread Interrupted.");
      }

      SeatStatus[] seatArray = ticketService.getSeatArray();
      assertEquals(seatArray[0], SeatStatus.HELD);

      ticketService.reserveSeats(holdId, "ugo@gmail.com");
      assertEquals(seatArray[0], SeatStatus.RESERVED);
   }

   @Test
   public void ReservationFailureAfterWaitingTooLongToConfirm() {
      ticketService = new TicketServiceImpl(100, 5);
      SeatHold hold = ticketService.findAndHoldSeats(4, "ugo@gmail.com");
      int holdId = hold.getId();

      SeatStatus[] seatArray = ticketService.getSeatArray();
      assertEquals(seatArray[0], SeatStatus.HELD);

      try {
        Thread.sleep(6 * 1000);
      } catch (InterruptedException e) {
        System.out.println("Test Thread Interrupted.");
      }

      ticketService.reserveSeats(holdId, "ugo@gmail.com");
      assertEquals(seatArray[0], SeatStatus.FREE);
   }
}
