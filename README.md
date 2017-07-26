



SeatHold
  1. Data Structures:
    - Used LinkedList to hold the seat numbers since I can take advantage of constant time insertion and O(n) space usage. The seats will only ever need to be accessed in a linear fashion, starting from the beginning, so the O(n) look up time is irrelevant.



Things to do:
- Make a method for removing holds that have been active for more than X seconds
- Figure out a way to represnt that a seat has gone from "held" status to "reserved" status
- Implement functions at the bottom of TicketServiceImpl class
