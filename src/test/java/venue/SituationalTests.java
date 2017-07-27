package venue;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import venue.*;
import util.TestTools;

public class SituationalTests {
   TicketServiceImpl ticketService;

   @Test
   public void FreeUpSeatsInTheBeginning() {
     ticketService = new TicketServiceImpl(100, 5);
     SeatStatus[] seatArray = ticketService.getSeatArray();

     SeatHold hold = ticketService.findAndHoldSeats(5, "ugo@gmail.com");
     int holdId1 = hold.getId();

     hold = ticketService.findAndHoldSeats(10, "ugo@gmail.com");
     int holdId2 = hold.getId();

     ticketService.reserveSeats(holdId2, "ugo@gmail.com");

     TestTools.wait(6);

     TestTools.confirmSeatSegmentStatus(seatArray, 0, 4, SeatStatus.FREE);
     TestTools.confirmSeatSegmentStatus(seatArray, 5, 14, SeatStatus.RESERVED);
   }

   @Test
   public void FreeUpSeatsInTheMiddle() {
     ticketService = new TicketServiceImpl(100, 5);
     SeatStatus[] seatArray = ticketService.getSeatArray();

     SeatHold hold = ticketService.findAndHoldSeats(5, "ugo@gmail.com");
     int holdId1 = hold.getId();

     hold = ticketService.findAndHoldSeats(10, "ugo@gmail.com");
     int holdId2 = hold.getId();

     hold = ticketService.findAndHoldSeats(6, "ugo@gmail.com");
     int holdId3 = hold.getId();


     ticketService.reserveSeats(holdId1, "ugo@gmail.com");
     ticketService.reserveSeats(holdId3, "ugo@gmail.com");

     TestTools.wait(6);

     TestTools.confirmSeatSegmentStatus(seatArray, 0, 4, SeatStatus.RESERVED);
     TestTools.confirmSeatSegmentStatus(seatArray, 5, 14, SeatStatus.FREE);
     TestTools.confirmSeatSegmentStatus(seatArray, 15, 20, SeatStatus.RESERVED);
   }

}
