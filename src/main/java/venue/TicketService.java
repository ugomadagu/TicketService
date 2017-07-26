package venue;

public interface TicketService {
  int numSeatAvailable();
  SeatHold findAndHoldSeats(int numSeats, String customerEmail);
  String reserveSeats(int seatHoldId, String customerEmail);
}
