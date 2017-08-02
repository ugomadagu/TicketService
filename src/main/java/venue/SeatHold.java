package venue;

import java.util.LinkedList;

public class SeatHold {
  private LinkedList<Integer> seats;
  private String customerEmail;
  private LinkedList<Integer> previousBeginningSeats;

  public SeatHold(String customerEmail) {
    seats = new LinkedList<Integer>();
    previousBeginningSeats = new LinkedList<Integer>();
    this.customerEmail = customerEmail;
  }

  public void addPreviousBeginningSeat(int previousBeginningSeat) {
    previousBeginningSeats.add(previousBeginningSeat);
  }

  public LinkedList<Integer> getPreviousBeginningSeats() {
    return previousBeginningSeats;
  }

  public void addSeat(int seatNumber) {
    seats.add(seatNumber);
  }

  // Use the first seat number as the ID since only one SeatHold can be assoaicated to a seat number
  public int getId() {
    return seats.peekFirst();
  }

  public String getCustomerEmail() {
    return customerEmail;
  }

  public SeatStatus getSeatStatus(SeatStatus[] seatArray) {
    return seatArray[seats.peekFirst()]; // We can use the first seat status to represent the rest since they are all the same
  }

  public void setSeatStatus(SeatStatus[] seatArray, SeatStatus status) {
    for(int seat : seats) {
      seatArray[seat] = status;
    }
  }

  public int getNumOfSeats() {
    return seats.size();
  }

  public int getFirstSeat() {
    return seats.peekFirst();
  }

  public int getLastSeat() {
    return seats.peekLast();
  }

}
