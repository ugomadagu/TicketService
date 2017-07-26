package main;

import java.util.LinkedList;

public class SeatHold {
  private LinkedList<Integer> seats;
  private String customerEmail;

  public SeatHold(String customerEmail) {
    seats = new LinkedList<Integer>();
    this.customerEmail = customerEmail;
  }

  public void addSeat(int seatNumber) {
    seats.add(seatNumber);
  }

  public int getId() { // Use the first seat number as the ID since only one SeatHold can be assoaicated to a seat number
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

}
