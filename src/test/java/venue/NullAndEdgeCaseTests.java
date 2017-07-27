package venue;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import venue.*;
import util.TestTools;

public class NullAndEdgeCaseTests {
   TicketServiceImpl ticketService;

   @Test
   public void ReserveAllSeatsSuccess() {
     ticketService = new TicketServiceImpl(100, 5);
     SeatStatus[] seatArray = ticketService.getSeatArray();

     SeatHold hold = ticketService.findAndHoldSeats(100, "ugo@gmail.com");
     int holdId = hold.getId();

     TestTools.confirmSeatSegmentStatus(seatArray, 0, 99, SeatStatus.HELD);

     TestTools.wait(1);

     ticketService.reserveSeats(holdId, "ugo@gmail.com");
     TestTools.confirmSeatSegmentStatus(seatArray, 0, 99, SeatStatus.RESERVED);
   }

   @Test
   public void ReserveAllSeatsFailure() {
     ticketService = new TicketServiceImpl(100, 5);
     SeatStatus[] seatArray = ticketService.getSeatArray();

     SeatHold hold = ticketService.findAndHoldSeats(100, "ugo@gmail.com");
     int holdId = hold.getId();

     TestTools.confirmSeatSegmentStatus(seatArray, 0, 99, SeatStatus.HELD);

     TestTools.wait(6);

     ticketService.reserveSeats(holdId, "ugo@gmail.com");
     TestTools.confirmSeatSegmentStatus(seatArray, 0, 99, SeatStatus.FREE);
   }

   @Test
   public void RequestHoldWithZeroSeats() {
     ticketService = new TicketServiceImpl(100, 5);
     SeatStatus[] seatArray = ticketService.getSeatArray();

     SeatHold hold = ticketService.findAndHoldSeats(0, "ugo@gmail.com");
     assertEquals(hold, null);
   }
}
