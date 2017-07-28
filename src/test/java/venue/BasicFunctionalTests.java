package venue;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import venue.*;
import util.TestTools;

public class BasicFunctionalTests {
   TicketServiceImpl ticketService;

   @Test
   public void ReservationSuccess() {
      ticketService = new TicketServiceImpl(100, 3);
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
      ticketService = new TicketServiceImpl(100, 3);
      SeatStatus[] seatArray = ticketService.getSeatArray();

      SeatHold hold = ticketService.findAndHoldSeats(4, "ugo@gmail.com");
      int holdId = hold.getId();

      TestTools.confirmSeatSegmentStatus(seatArray, 0, 3, SeatStatus.HELD);

      TestTools.wait(4);

      ticketService.reserveSeats(holdId, "ugo@gmail.com");
      TestTools.confirmSeatSegmentStatus(seatArray, 0, 3, SeatStatus.FREE);
   }

   @Test
   public void NoFreeSeats() {
     ticketService = new TicketServiceImpl(100, 3);
     SeatHold hold = ticketService.findAndHoldSeats(101, "ugo@gmail.com");
     assertEquals(hold, null);
   }

   @Test
   public void CheckAvailableSeatsTestSimple() {
     ticketService = new TicketServiceImpl(100, 2);
     SeatStatus[] seatArray = ticketService.getSeatArray();

     SeatHold hold = ticketService.findAndHoldSeats(10, "ugo@gmail.com");
     int holdId1 = hold.getId();

     hold = ticketService.findAndHoldSeats(5, "ugo@gmail.com");
     int holdId2 = hold.getId();

     ticketService.reserveSeats(holdId1, "ugo@gmail.com");
     TestTools.wait(3);

     assertEquals(ticketService.numSeatAvailable(), 90);

   }

   @Test
   public void CheckAvailableSeatsTestComplicated() {
     ticketService = new TicketServiceImpl(100, 2);
     SeatStatus[] seatArray = ticketService.getSeatArray();

     SeatHold hold = ticketService.findAndHoldSeats(5, "ugo@gmail.com");
     int holdId1 = hold.getId();

     hold = ticketService.findAndHoldSeats(10, "ugo@gmail.com");
     int holdId2 = hold.getId();

     hold = ticketService.findAndHoldSeats(6, "ugo@gmail.com");
     int holdId3 = hold.getId();

     hold = ticketService.findAndHoldSeats(2, "ugo@gmail.com");
     int holdId4 = hold.getId();

     hold = ticketService.findAndHoldSeats(12, "ugo@gmail.com");
     int holdId5 = hold.getId();

     ticketService.reserveSeats(holdId2, "ugo@gmail.com");
     ticketService.reserveSeats(holdId5, "ugo@gmail.com");

     TestTools.wait(3);

     assertEquals(ticketService.numSeatAvailable(), 78);

   }

   @Test
   public void CheckConfirmationCodeTest() {
     ticketService = new TicketServiceImpl(100, 2);
     SeatStatus[] seatArray = ticketService.getSeatArray();

     SeatHold hold = ticketService.findAndHoldSeats(5, "ugo@gmail.com");
     int holdId = hold.getId();

     String code = ticketService.reserveSeats(holdId, "ugo@gmail.com");

     assertNotEquals(code, null);
   }

}
