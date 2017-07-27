package venue;

import java.util.TreeSet;
import java.util.HashMap;
import java.util.Arrays;

public class TicketServiceImpl implements TicketService {
  public final int CAPACITY;
  private int freeSeats;
  private int numOfSecondsUntilHoldExpires;
  private SeatStatus[] seatArray;
  private TreeSet<Integer> beginningSeats;
  private HashMap<Integer, SeatHold> idToSeatHoldMap;

  public TicketServiceImpl(int freeSeats, int numOfSecondsUntilHoldExpires) {
    CAPACITY = this.freeSeats = freeSeats;
    this.numOfSecondsUntilHoldExpires = numOfSecondsUntilHoldExpires;
    seatArray = new SeatStatus[freeSeats];
    Arrays.fill(seatArray, SeatStatus.FREE);
    beginningSeats = new TreeSet<Integer>();
    beginningSeats.add(0);
    idToSeatHoldMap = new HashMap<Integer, SeatHold>();
  }

  public int numSeatAvailable() {
    int result;
    synchronized(idToSeatHoldMap) {
      result = freeSeats;
    }
    return result;
  }

  public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
    if(numSeats == 0 || !isValidEmail(customerEmail)) {
      System.out.println("Please provide a valid email address and a request for 1 or more seats.");
      return null;
    }

    SeatHold hold;
    synchronized(idToSeatHoldMap) {
      if(freeSeats < numSeats) {
        System.out.println("I'm sorry, we only have " + freeSeats + " seats available.");
        return null;
      }
      freeSeats -= numSeats; // Since we already checked that we have enough seats, we can preemptivley adjust our number of free seats.
      hold = new SeatHold(customerEmail);
      int currSeat = -1;
      while(numSeats > 0) {
        currSeat = beginningSeats.pollFirst();
        while(numSeats > 0) {
          if(seatArray[currSeat] == SeatStatus.FREE) {
            hold.addSeat(currSeat);
            seatArray[currSeat++] = SeatStatus.HELD;
            numSeats--;
          } else {
            break; // break so we can find the next section of free seats
          }
        }
      }

      if(currSeat < CAPACITY && seatArray[currSeat] == SeatStatus.FREE) {
        beginningSeats.add(currSeat);
      }

      idToSeatHoldMap.put(hold.getId(), hold);
      Runnable r = new ExpiredHoldChecker(hold.getId(), numOfSecondsUntilHoldExpires, this, idToSeatHoldMap, beginningSeats);
      new Thread(r).start();
    }



    return hold;
  }

  public String reserveSeats(int seatHoldId, String customerEmail) {
    synchronized(idToSeatHoldMap) {
      if(!idToSeatHoldMap.containsKey(seatHoldId) || !isValidEmail(customerEmail)) {
        System.out.println("Not a valid seatHoldId or email address.");
        return null;
      }

      SeatHold hold = idToSeatHoldMap.get(seatHoldId);
      if(hold.getCustomerEmail().equals(customerEmail)) { //if correct email was given
        if(hold.getSeatStatus(seatArray) == SeatStatus.FREE) {
          System.out.println("Sorry, but this hold has expired");
          return null;
        } else if(hold.getSeatStatus(seatArray) == SeatStatus.RESERVED) { // Shouldn't be possible to get here, but just covering bases
          System.out.println("These seats have already been reserved.");
          return null;
        } else if(hold.getSeatStatus(seatArray) == SeatStatus.HELD){
          hold.setSeatStatus(seatArray, SeatStatus.RESERVED);
        }
        return makeConfirmationCode(seatHoldId, customerEmail);
      } else {
        System.out.println("That is not the email address we have on record for this hold. Please try again.");
        return null;
      }
    }
  }

  public void addFreeSeats(int num) {
    freeSeats += num;
  }

  public SeatStatus[] getSeatArray() {
    return seatArray;
  }


  private String makeConfirmationCode(int seatHoldId, String customerEmail) {
    return "Do later";
  }

  private boolean isValidEmail(String email) {
    return true;
  }
}
