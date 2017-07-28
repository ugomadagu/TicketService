package venue;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import venue.*;
import util.TestTools;

public class SituationalTests {
   TicketServiceImpl ticketService;

   @Test
   public void FreeUpSeatsInTheBeginning() {
     ticketService = new TicketServiceImpl(100, 2);
     SeatStatus[] seatArray = ticketService.getSeatArray();

     SeatHold hold = ticketService.findAndHoldSeats(5, "ugo@gmail.com");
     int holdId1 = hold.getId();

     hold = ticketService.findAndHoldSeats(10, "ugo@gmail.com");
     int holdId2 = hold.getId();

     ticketService.reserveSeats(holdId2, "ugo@gmail.com");

     TestTools.wait(3);

     TestTools.confirmSeatSegmentStatus(seatArray, 0, 4, SeatStatus.FREE);
     TestTools.confirmSeatSegmentStatus(seatArray, 5, 14, SeatStatus.RESERVED);
   }

   @Test
   public void FreeUpSeatsInTheMiddle() {
     ticketService = new TicketServiceImpl(100, 2);
     SeatStatus[] seatArray = ticketService.getSeatArray();

     SeatHold hold = ticketService.findAndHoldSeats(5, "ugo@gmail.com");
     int holdId1 = hold.getId();

     hold = ticketService.findAndHoldSeats(10, "ugo@gmail.com");
     int holdId2 = hold.getId();

     hold = ticketService.findAndHoldSeats(6, "ugo@gmail.com");
     int holdId3 = hold.getId();


     ticketService.reserveSeats(holdId1, "ugo@gmail.com");
     ticketService.reserveSeats(holdId3, "ugo@gmail.com");

     TestTools.wait(3);

     TestTools.confirmSeatSegmentStatus(seatArray, 0, 4, SeatStatus.RESERVED);
     TestTools.confirmSeatSegmentStatus(seatArray, 5, 14, SeatStatus.FREE);
     TestTools.confirmSeatSegmentStatus(seatArray, 15, 20, SeatStatus.RESERVED);
   }

   @Test
   public void FreeUpSeatsAtTheEnd(){
     ticketService = new TicketServiceImpl(100, 2);
     SeatStatus[] seatArray = ticketService.getSeatArray();

     SeatHold hold = ticketService.findAndHoldSeats(5, "ugo@gmail.com");
     int holdId1 = hold.getId();

     hold = ticketService.findAndHoldSeats(10, "ugo@gmail.com");
     int holdId2 = hold.getId();

     hold = ticketService.findAndHoldSeats(6, "ugo@gmail.com");
     int holdId3 = hold.getId();


     ticketService.reserveSeats(holdId1, "ugo@gmail.com");
     ticketService.reserveSeats(holdId2, "ugo@gmail.com");

     TestTools.wait(3);

     TestTools.confirmSeatSegmentStatus(seatArray, 0, 4, SeatStatus.RESERVED);
     TestTools.confirmSeatSegmentStatus(seatArray, 5, 14, SeatStatus.RESERVED);
     TestTools.confirmSeatSegmentStatus(seatArray, 15, 20, SeatStatus.FREE);
   }

   @Test
   public void ReserveAHoldMultipleTimes() {
     ticketService = new TicketServiceImpl(100, 2);
     SeatStatus[] seatArray = ticketService.getSeatArray();

     SeatHold hold = ticketService.findAndHoldSeats(5, "ugo@gmail.com");
     int holdId = hold.getId();

     String result1 = ticketService.reserveSeats(holdId, "ugo@gmail.com");
     String result2 = ticketService.reserveSeats(holdId, "ugo@gmail.com");

     assertNotEquals(result1, null);
     assertEquals(result2, null);
   }

   @Test
   public void MakeReservationsAcrossDifferentOpenSeatSections() {
     ticketService = new TicketServiceImpl(100, 2);
     SeatStatus[] seatArray = ticketService.getSeatArray();

     SeatHold hold = ticketService.findAndHoldSeats(10, "ugo@gmail.com");
     int holdId1 = hold.getId();

     hold = ticketService.findAndHoldSeats(10, "ugo@gmail.com");
     int holdId2 = hold.getId();

     hold = ticketService.findAndHoldSeats(10, "ugo@gmail.com");
     int holdId3 = hold.getId();

     hold = ticketService.findAndHoldSeats(10, "ugo@gmail.com");
     int holdId4 = hold.getId();

     ticketService.reserveSeats(holdId1, "ugo@gmail.com");
     ticketService.reserveSeats(holdId3, "ugo@gmail.com");

     TestTools.wait(3);

     hold = ticketService.findAndHoldSeats(15, "ugo@gmail.com");
     int holdId5 = hold.getId();
     ticketService.reserveSeats(holdId5, "ugo@gmail.com");

     TestTools.confirmSeatSegmentStatus(seatArray, 0, 9, SeatStatus.RESERVED);
     TestTools.confirmSeatSegmentStatus(seatArray, 10, 34, SeatStatus.RESERVED);
     TestTools.confirmSeatSegmentStatus(seatArray, 35, 39, SeatStatus.FREE);
   }

}
