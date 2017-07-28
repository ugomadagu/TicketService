Table of Contents
- [Overview](#overview)
- [How to build and test source code](#how-to-build-and-test-source-code)
  * [Build](#build)
  * [Run tests and generate code coverage report](#run-tests-and-generate-code-coverage-report)
- [Class Design Decisions](#class-design-decisions)
  * [Assumptions](#assumptions)
  * [TicketServiceImpl Class](#ticketserviceimpl-class)
  * [SeatHold Class](#seathold-class)




## Overview
This is a multithreaded application that simulates an efficient ticket distribution service for a venue. Here is a high level overview of my implementation:
1. A customer will provide thier email address and the number of seats that they wish to place on hold. If there are enough free seats available, a SeatHold object is created that contains the free seats that are closest to the stage. 
2. After the SeatHold object is created and the seats officailly put in a "HELD" status, a thread is started and passed the Id of the SeatHold object that was just created. This new thread will wait for X seconds and then if the status of the seats in that SeatHold are still "HELD", then the thread frees those seats and gets rid of the SeatHold object, since it has expired.
3. A customer will next try to reserve a hold by passing in the Id of thier hold and thier email address. If the SeatHold object corresponding to the Id cannot be found or if the email is invalid/incorrect, then we do not move forward. If all is well, we move forward. If the status of the seats in the SeatHold object is "HELD", we change these statuses to "RESERVED". If the status is already "RESERVED", we do nothing because the seats have already been reserved by the user. 

## How to build and test source code
Please build before running tests.

### Build
```./gradlew build```

### Run tests and generate code coverage report
```./gradlew test jacocoTestReport```   
After running the above command, the code coverage report can be accessed at "TickerService/build/reports/coverage/index.html". Current code coverage is **91%**.



## Class Design Decisions
In this section, I will explain why I took the design approaches that I did for the **TicketServiceImpl** and **SeatHold** classes. I will only discuss these classes because I feel that the others are more self-explanitory. In order to make this section concise and to the point, I will only explain the processes that I thought were the most interesting, but I am more than willing to discuss any other part of the codebase. I will discuss my: assumptions, data structures, time complexities, and space complexities.


### Assumptions
* Upon creation, the **TicketServiceImpl** class would be given two parameters in its constructor: the number of maximum seats the venue has, and the time limit before a seat hold expires.
* No matter what the shape of the venue would be, every seat would be sequentially numbered, with the lower numbers being closest to the stage.
* All seats in a row were equally valuable. Meaning, seats in the front row that were on the far left or far right of the stage were just as valuable as front row seats in the middle of the stage. Therefore, it makes the most sense to fill seats from left to right, or, lowest seat id to highest seat id.
* The the number of seats in the venue would not change.
* I was not entirley sure what the **customerEmail** feild was for, therefore, I used it as a form of authentication. A user must reserve seats with the same email that they used to put a hold on those seats.
* It is acceptable to simply return null when methods were not provided with acceptable parameters.


### TicketServiceImpl Class
- Data Structures:
  * **SeatStatus** is an enumeration used to make the status of a seat easier to read and set.
  * **seatArray** is an array of SeatStatus. Each index represnts the seat number for that particular seat and the value that the index points to represents that seat's current status. I chose an array because of it constant access and insertion time.
  * **beginningSeats** is a TreeSet that is used to keep track of the beginning of every section of open seats. For example, the 0th seat is the beginning of an open section (the open section being the entire venue) when we first start, since no one has reserved/held seats. If a customer grabs seats 0-10, 11 is now the beginning of an open section of seats. I chose a TreeSet to represent this data because I need to keep the beginnings of my open sections ordered for quick retrival (If there are open seats in the front row, we don't want to grab open seats in the back). We get a log(n) average time for insertion, deletion, and removal, which is acceptable.
  * **idToSeatHoldMap** is a HashMap that maps an Id to a SeatHold. I used a HashMap becasue of its O(1) access, insertion, and deletion time. Also because of its reasonable O(n) space requirement.
  
- Methods:
  * **makeConfirmationCode** is the method that I made to create the confirmation code that is returned by **reserveSeats**. This method creates a 20 character alphanumeric. For every character, it randomly generates a digit, either 1 or 2, that determines whether the character will be a letter or number. With that information, we then generate a random digit that will be later converted into a _char_ based on ASCI conversion.


### SeatHold Class
- Data Structures:
  * **seats** is a LinkedList that I used to store all the Id's aligned to a particular seatHold. I chose a LinkedList because of its constant insertion time and O(n) space requirements. The O(n) access time does not negativley effect me since I only ever need to access the elements of **seats** in a linear fashion or by retreiving only first element.
   

