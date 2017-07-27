package venue;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import venue.*;
import util.TestTools;

public class BasicFunctionalTests {
   TicketServiceImpl ticketService;

   @Test
   public void ReservationSuccess() {
      ticketService = new TicketServiceImpl(100, 5);
      SeatStatus[] seatArray = ticketService.getSeatArray();

      SeatHold hold = ticketService.findAndHoldSeats(4, "ugo@gmail.com");
      int holdId = hold.getId();

      TestTools.wait(1);

      TestTools.confirmSeatSegmentStatus(seatArray, 0, 3, SeatStatus.HELD);
      ticketService.reserveSeats(holdId, "ugo@gmail.com");
      TestTools.confirmSeatSegmentStatus(seatArray, 0, 3, SeatStatus.RESERVED);
   }

   @Test
   public void ReservationFailureAfterWaitingTooLongToConfirm() {
      ticketService = new TicketServiceImpl(100, 5);
      SeatStatus[] seatArray = ticketService.getSeatArray();

      SeatHold hold = ticketService.findAndHoldSeats(4, "ugo@gmail.com");
      int holdId = hold.getId();

      TestTools.confirmSeatSegmentStatus(seatArray, 0, 3, SeatStatus.HELD);

      TestTools.wait(6);

      ticketService.reserveSeats(holdId, "ugo@gmail.com");
      TestTools.confirmSeatSegmentStatus(seatArray, 0, 3, SeatStatus.FREE);
   }

   @Test
   public void NoFreeSeats() {
     ticketService = new TicketServiceImpl(100, 5);
     SeatHold hold = ticketService.findAndHoldSeats(101, "ugo@gmail.com");
     assertEquals(hold, null);
   }
}
