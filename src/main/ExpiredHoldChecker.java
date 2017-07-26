package main;

import java.util.HashMap;

public class ExpiredHoldChecker implements Runnable {
  private int seatHoldId;
  private int numOfSecondsUntilHoldExpires;
  private TicketServiceImpl ticketService;
  private HashMap<Integer, SeatHold> idToSeatHoldMap;

   public ExpiredHoldChecker(int seatHoldId, int numOfSecondsUntilHoldExpires, TicketServiceImpl ticketService, HashMap<Integer, SeatHold> idToSeatHoldMap) {
     this.seatHoldId = seatHoldId;
     this.numOfSecondsUntilHoldExpires = numOfSecondsUntilHoldExpires;
     this.ticketService = ticketService;
     this.idToSeatHoldMap = idToSeatHoldMap;
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
         idToSeatHoldMap.remove(seatHoldId);
       }
     }
   }
}
