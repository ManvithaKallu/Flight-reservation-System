Flight Reservation System

This project is a simplified flight reservation system implemented in Java. It simulates common airline booking operations such as searching for flights, booking seats, viewing reservations, and canceling reservations. All data is stored in memory, and the system uses a console-based interface for user interaction.

Features

Flight Management
Each flight contains a flight number, destination, departure date and time, and the number of available seats.

Reservation Management
A reservation stores the customer name, the flight being reserved, and the number of seats booked.

Core Operations
The system allows users to search for flights by destination and date, book seats on available flights, view all reservations for a customer, and cancel an existing reservation. Seat availability is checked during booking to prevent overbooking, and canceled reservations restore seats automatically.

Data Handling
All flights and reservations are managed using in-memory lists. No external files or databases are used.

Running the Application

Prerequisites: Java 17 or later and Maven.

To build the project:
mvn clean install

To run the console interface:
mvn -q exec:java -Dexec.mainClass="com.manvitha.flightsystem.Main"

Running Tests

The project includes unit tests for the FlightService class, covering:

Flight search

Booking behavior

Overbooking prevention

Seat deduction after booking

Reservation lookup per customer

Cancellation behavior and seat restoration

Searching across multiple flights

Run tests using:
mvn test

Design Decisions

In-memory lists were chosen to keep the project simple, fast to run, and easy to reason about during testing.

Input validation was added for dates, seat amounts, and user selections to make the console experience more reliable.

Search results are sorted by departure time to mimic a real airline search interface.

The system was divided into distinct components:

Flight (data model)

Reservation (data model)

FlightService (handles business logic)

Main (handles user interface and input)
This separation improves readability and makes the logic easier to test.

Real-Life Considerations

A production-ready version of this system would require:

A persistent database

Authentication and user accounts

REST APIs for booking and flight search

Concurrency control to prevent race conditions in seat booking

Logging for audit and support

Proper time-zone handling for flight times

This implementation focuses on the core functionality needed for the technical exercise while reflecting realistic engineering decisions.
