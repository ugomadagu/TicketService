package util;

import venue.*;
import static org.junit.Assert.assertEquals;

public class TestTools {
  public static void confirmSeatSegmentStatus(SeatStatus[] seatArray, int startSeat, int endSeat, SeatStatus targetStatus) {
    for(int i = startSeat; i <= endSeat; i++) {
      assertEquals(seatArray[i], targetStatus);
    }
  }

  public static void wait(int x) {
    try {
      Thread.sleep(x * 1000);
    } catch (InterruptedException e) {
      System.out.println("Test Thread Interrupted.");
    }
  }
}
