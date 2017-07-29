package venue;

import java.io.Console;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CliMain {
  public static void main(String[] args) {
    Console c = System.console();
    TicketServiceImpl ticketService = new TicketServiceImpl(100, 10);

    System.out.println("Welcome to the ticket reservation service just for developers! Please enter commands below:");
    while(true) {
      String line = c.readLine().trim();
      if(line.matches("createNewService\\s\\d+\\s\\d+\\z")) {
        ticketService = createNewService(line, ticketService);
      } else if(line.equals("exit")) {
        break;
      } else {
        proccessInput(line, ticketService);
      }
    }
  }

  public static TicketServiceImpl createNewService(String line, TicketServiceImpl currTicketService) {
    String regex = "createNewService\\s(\\d+)\\s(\\d+)\\z";
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(line);
    m.find();

    int freeSeats = 0;
    int numOfSecondsUntilHoldExpires = 0;
    try {
      freeSeats = Integer.parseInt(m.group(1));
      numOfSecondsUntilHoldExpires = Integer.parseInt(m.group(2));
    } catch(java.lang.NumberFormatException e) {
      System.out.println("The number(s) you entered were too large. Only use numbers less than or equal to " + Integer.MAX_VALUE);
      return currTicketService;
    }

    TicketServiceImpl newTicketService = new TicketServiceImpl(freeSeats, numOfSecondsUntilHoldExpires);
    System.out.println("Created New TicketService");
    return newTicketService;
  }

  public static void proccessInput(String line, TicketServiceImpl ticketService) {
    String numSeatAvailableRegex = "getNumSeatAvailable\\z";
    String findAndHoldSeatsRegex = "findAndHoldSeats\\s(\\d+)\\s(\\S+)\\z";
    String reserveSeatsRegex = "reserveSeats\\s(\\d+)\\s(\\S+)\\z";

    Pattern numSeatAvailablePattern = Pattern.compile(numSeatAvailableRegex);
    Pattern findAndHoldSeatsPattern = Pattern.compile(findAndHoldSeatsRegex);
    Pattern reserveSeatsPattern = Pattern.compile(reserveSeatsRegex);

    Matcher m;

    m = numSeatAvailablePattern.matcher(line);
    if(m.find()) {
      System.out.println("Number of available seats: " + ticketService.numSeatAvailable());
      return;
    }

    m = findAndHoldSeatsPattern.matcher(line);
    if(m.find()) {
      int numSeats  = 0;
      String customerEmail = m.group(2);

      try {
        numSeats = Integer.parseInt(m.group(1));
      } catch(java.lang.NumberFormatException e) {
        System.out.println("The number you entered was too large. Only use numbers less than or equal to " + Integer.MAX_VALUE);
        return;
      }

      SeatHold hold = ticketService.findAndHoldSeats(numSeats, customerEmail);
      int seatHoldId;
      if(hold == null) {
        return;
      } else {
        seatHoldId = hold.getId();
      }
      System.out.println("Seats have been put on hold. SeatHold Id is " + seatHoldId);
      return;
    }

    m = reserveSeatsPattern.matcher(line);
    if(m.find()) {
      int seatHoldId  = 0;
      String customerEmail = m.group(2);

      try {
        seatHoldId = Integer.parseInt(m.group(1));
      } catch(java.lang.NumberFormatException e) {
        System.out.println("The number you entered was too large. Only use numbers less than or equal to " + Integer.MAX_VALUE);
        return;
      }
      String confirmationCode = ticketService.reserveSeats(seatHoldId, customerEmail);
      if(confirmationCode != null) {
        System.out.println("Reservation confirmed. You confirmation code is: " + confirmationCode);
      }
      return;
    }

    System.out.println("I could not understand your command, please try again.");
  }
}
