package venue;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import venue.*;
import util.TestTools;

public class NullAndEdgeCaseTests {
   TicketServiceImpl ticketService;

   @Test
   public void ReserveAllSeatsSuccess() {
     ticketService = new TicketServiceImpl(100, 2);
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
     ticketService = new TicketServiceImpl(100, 2);
     SeatStatus[] seatArray = ticketService.getSeatArray();

     SeatHold hold = ticketService.findAndHoldSeats(100, "ugo@gmail.com");
     int holdId = hold.getId();

     TestTools.confirmSeatSegmentStatus(seatArray, 0, 99, SeatStatus.HELD);

     TestTools.wait(3);

     ticketService.reserveSeats(holdId, "ugo@gmail.com");
     TestTools.confirmSeatSegmentStatus(seatArray, 0, 99, SeatStatus.FREE);
   }

   @Test
   public void RequestHoldWithZeroSeats() {
     ticketService = new TicketServiceImpl(100, 1);
     SeatStatus[] seatArray = ticketService.getSeatArray();

     SeatHold hold = ticketService.findAndHoldSeats(0, "ugo@gmail.com");
     assertEquals(hold, null);
   }

   @Test
   public void RequestHoldWithBadEmail() {
     ticketService = new TicketServiceImpl(100, 1);
     SeatStatus[] seatArray = ticketService.getSeatArray();

     SeatHold hold = ticketService.findAndHoldSeats(20, "Badman45@googleorg");
     assertEquals(hold, null);
   }

   @Test
   public void ReserveSeatsWithWrongEmail() {
     ticketService = new TicketServiceImpl(100, 1);
     SeatStatus[] seatArray = ticketService.getSeatArray();

     SeatHold hold = ticketService.findAndHoldSeats(20, "ugo@gmail.com");
     int holdId = hold.getId();

     String confirmationCode = ticketService.reserveSeats(holdId, "notme@hotmail.com");
     assertEquals(confirmationCode, null);
   }
}
