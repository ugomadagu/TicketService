package venue;

import java.util.HashMap;
import java.util.TreeSet;

public class ExpiredHoldChecker implements Runnable {
  private int seatHoldId;
  private int numOfSecondsUntilHoldExpires;
  private TicketServiceImpl ticketService;
  private HashMap<Integer, SeatHold> idToSeatHoldMap;
  TreeSet<Integer> beginningSeats;

   public ExpiredHoldChecker(int seatHoldId, int numOfSecondsUntilHoldExpires, TicketServiceImpl ticketService, HashMap<Integer, SeatHold> idToSeatHoldMap, TreeSet<Integer> beginningSeats) {
     this.seatHoldId = seatHoldId;
     this.numOfSecondsUntilHoldExpires = numOfSecondsUntilHoldExpires;
     this.ticketService = ticketService;
     this.idToSeatHoldMap = idToSeatHoldMap;
     this.beginningSeats = beginningSeats;
   }

   public void run() {
     try {
       Thread.sleep(numOfSecondsUntilHoldExpires * 1000);
     } catch(InterruptedException e) {
       System.out.println("Thread checking on seatHold " + seatHoldId + " was interrupted.");
     }

     synchronized(idToSeatHoldMap) {
       SeatHold hold = idToSeatHoldMap.get(seatHoldId);
       if(hold.getSeatStatus(ticketService.getSeatArray()) == SeatStatus.HELD) {
         hold.setSeatStatus(ticketService.getSeatArray(), SeatStatus.FREE);
         ticketService.addFreeSeats(hold.getNumOfSeats());
         beginningSeats.add(hold.getFirstSeat());
         idToSeatHoldMap.remove(seatHoldId);
       }
     }
   }
}
