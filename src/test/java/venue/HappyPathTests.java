import org.junit.Test;
import static org.junit.Assert.assertEquals;
import venue.*;

public class HappyPathTests {
   TicketService ticketService;

   @Test
   public void TestTicketServiceCreation() {
      ticketService = new TicketServiceImpl(100, 5);
      assertEquals(ticketService.numSeatAvailable(), 100);
   }
}
