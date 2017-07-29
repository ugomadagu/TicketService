package venue;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import venue.*;
import util.TestTools;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CliMainTests {

  @Test
  public void newTicketServiceCreationTest() {
    String command = "createNewService 350 6";
    TicketServiceImpl ticketService =  CliMain.createNewService(command, null);
    assertTrue(ticketService != null);


    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outStream));

    command = "getNumSeatAvailable";
    CliMain.proccessInput(command, ticketService);

    System.setOut(System.out);

    assertEquals(getLastLineOfStandartOutput(outStream.toString()), "Number of available seats: 350");
  }

  @Test
  public void createNewServiceOverFlowDetectionTest() {
    String command = "createNewService 6376783684263826 6";
    TicketServiceImpl ticketService =  CliMain.createNewService(command, null);
    assertTrue(ticketService == null);
  }

  @Test
  public void findAndHoldSeatsOverFlowDetectionTest() {
    String command = "createNewService 100 6";
    TicketServiceImpl ticketService =  CliMain.createNewService(command, null);

    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outStream));

    command = "findAndHoldSeats 3723287438798237298 ugo@gmail.com";
    CliMain.proccessInput(command, ticketService);

    System.setOut(System.out);

    assertEquals(getLastLineOfStandartOutput(outStream.toString()), "The number you entered was too large. Only use numbers less than or equal to 2147483647");
  }

  @Test
  public void findAndHoldSeatsSuccessTest() {
    String command = "createNewService 100 6";
    TicketServiceImpl ticketService =  CliMain.createNewService(command, null);

    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outStream));

    command = "findAndHoldSeats 10 ugo@gmail.com";
    CliMain.proccessInput(command, ticketService);

    System.setOut(System.out);

    assertEquals(getLastLineOfStandartOutput(outStream.toString()), "Seats have been put on hold. SeatHold Id is 0");
  }

  @Test
  public void findAndHoldSeatsFailureTest() {
    String command = "createNewService 100 6";
    TicketServiceImpl ticketService =  CliMain.createNewService(command, null);

    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outStream));

    command = "findAndHoldSeats 200 ugo@gmail.com";
    CliMain.proccessInput(command, ticketService);

    System.setOut(System.out);

    assertEquals(getLastLineOfStandartOutput(outStream.toString()), "I'm sorry, we only have 100 seats available.");
  }

  @Test
  public void reserveSeatsFailureTest() {
    String command = "createNewService 100 6";
    TicketServiceImpl ticketService =  CliMain.createNewService(command, null);

    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outStream));

    command = "reserveSeats 0 ugo@gmail.com";
    CliMain.proccessInput(command, ticketService);

    System.setOut(System.out);

    assertEquals(getLastLineOfStandartOutput(outStream.toString()), "Not a valid seatHoldId, Seathold no longer exists, or email address is invalid.");
  }

  @Test
  public void reserveSeatsSuccessTest() {
    String command = "createNewService 100 6";
    TicketServiceImpl ticketService =  CliMain.createNewService(command, null);

    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outStream));

    command = "findAndHoldSeats 10 ugo@gmail.com";
    CliMain.proccessInput(command, ticketService);

    command = "reserveSeats 0 ugo@gmail.com";
    CliMain.proccessInput(command, ticketService);

    System.setOut(System.out);

    System.out.println(outStream.toString());

    assertTrue(getLastLineOfStandartOutput(outStream.toString()).contains("Seats have been put on hold. SeatHold Id is "));
  }

  @Test
  public void reserveSeatsOverFlowDetectionTest() {
    String command = "createNewService 100 6";
    TicketServiceImpl ticketService =  CliMain.createNewService(command, null);

    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outStream));

    command = "reserveSeats 3723287438798237298 ugo@gmail.com";
    CliMain.proccessInput(command, ticketService);

    System.setOut(System.out);

    assertEquals(getLastLineOfStandartOutput(outStream.toString()), "The number you entered was too large. Only use numbers less than or equal to 2147483647");
  }

  @Test
  public void badCommandTest() {
    String command = "createNewService 100 6";
    TicketServiceImpl ticketService =  CliMain.createNewService(command, null);

    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outStream));

    command = "foobar 200 ugo@gmail.com";
    CliMain.proccessInput(command, ticketService);

    System.setOut(System.out);

    assertEquals(getLastLineOfStandartOutput(outStream.toString()), "I could not understand your command, please try again.");

  }


  private String getLastLineOfStandartOutput(String output) {
    String regex = "\\A(.*)";
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(output);

    if(m.find()) {
      return m.group(1);
    } else {
      return null;
    }
  }
}
