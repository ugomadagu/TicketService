


## Overview


## How to build and test source code
Please build before running tests.

### Build
```gradle build```

### Run tests and generate code coverage report
```gradle test jacocoTestReport```
After running the above command, the code coverage report can be accessed at "TickerService/build/reports/coverage/index.html"



## Class Design Decisions
In this section, I will explain why I took the design approaches that I did for the ***TicketServiceImpl** and **SeatHold** classes. I will only discuss these sections becasue I feel that the others are more self-explanitory. In order to make this section concise and to the point, I will only explain the processes that I thought were the most interesting. I will discuss my chosen: data structures, time complexities, and space complexities.


### TicketServiceImpl
- Assumptions
  * Upon creation, the **TicketServiceImpl** class would be given two parameters in its constructor: the number of maximum seats the venue has, and the time limit before a seat hold expires.
  * No matter what the shape of the venue would be, every seat would be sequentially numbered, with the lower numbers being closest to the stage.
  * All seats in a row were equally valuable. Meaning, seats in the front row that were on the far left or far right of the stage were just as valuable as front row seats in the middle of the stage.
  * The the number of seats in the venue would not change.
  * I was not entirley sure what the **customerEmail** feild was for, therefore, I used it as a form of authentication. A user must reserve seats with the same email that they used to put a hold on those seats.
  
- Data Structures:
  * **SeatStatus** is an enumeration used to make the status of a seat easier to read and set.
  * **seatArray** is an array of SeatStatus. Each index represnts the seat number for that particular seat and the value that the index points to represents that seat's current status. I chose an array because of it constant access and insertion time.
  * **beginningSeats** is a TreeSet that is used to keep track of the beginning of every section of open seats. For example, the 0th seat is the beginning of an open section (the open section being the entire venue) when we first start, since no one has reserved/held seats. If a customer grabs seats 0-10, 11 is now the beginning of an open section of seats. I chose a TreeSet to represent this data because I need to keep the beginnings of my open sections ordered for quick retrival (If there are open seats in the front row, we don't want to grab open seats in the back). We get a log(n) average time for insertion, deletion, and removal, which is good.
  * **idToSeatHoldMap** is a HashMap that maps an Id to a SeatHold. I used a HashMap becasue of thier O(1) access, insertion, and delition time. Also because of its reasonable O(n) space requirement.
  
- Methods:
  * **makeConfirmationCode** is the method that I made to create the confirmation code that is returned by **reserveSeats**. This method creates a 20 character alphanumeric. For every character, it randomly generates a digit, either 1 or 2, that determines whether the character will be a letter or number. With that information, we then generate a random digit that will be later converted into a _char_ based on ASCI conversion.


### SeatHold
 - Data Structures:
  * **seats** is a LinkedList that I used to store all the Id's allighned to a particular seatHold. I chose a LinkedList mostly becasue of its constant insertion time and O(n) space requirements. The O(n) access time does not negativley effect me since I only ever need to access the elements in **seats** in a linear fashion or by retreiving only first element.
   

