package com.manvitha.flightsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FlightServiceTest {

    private FlightService service;

    @BeforeEach
    void setup() {
        service = new FlightService();
    }

    // ---------------------------------------------------------
    // 1. SEARCH SHOULD RETURN MATCHING FLIGHTS
    // ---------------------------------------------------------
    @Test
    void testSearchFlightsReturnsMatches() {
        Flight f1 = new Flight("FL100", "New York",
                LocalDateTime.of(2025, 1, 12, 10, 0), 50);

        Flight f2 = new Flight("FL200", "Miami",
                LocalDateTime.of(2025, 1, 12, 12, 0), 30);

        service.addFlight(f1);
        service.addFlight(f2);

        List<Flight> results = service.searchFlights(
                "New York",
                LocalDateTime.of(2025, 1, 12, 5, 0)
        );

        assertEquals(1, results.size());
        assertEquals("FL100", results.get(0).getFlightNumber());
    }

    // ---------------------------------------------------------
    // 2. SEARCH SHOULD RETURN EMPTY IF NO MATCHES
    // ---------------------------------------------------------
    @Test
    void testSearchFlightsNoMatches() {
        service.addFlight(new Flight("FL300", "Miami",
                LocalDateTime.of(2025, 1, 5, 15, 0), 20));

        List<Flight> results = service.searchFlights(
                "New York",
                LocalDateTime.of(2025, 1, 5, 15, 0)
        );

        assertTrue(results.isEmpty());
    }

    // ---------------------------------------------------------
    // 3. BOOKING SHOULD REDUCE AVAILABLE SEATS
    // ---------------------------------------------------------
    @Test
    void testBookFlightReducesSeats() {
        Flight flight = new Flight("FL100", "NYC",
                LocalDateTime.now(), 10);

        service.addFlight(flight);

        Reservation res = service.bookFlight("John", flight, 3);

        assertNotNull(res);
        assertEquals(7, flight.getAvailableSeats());
    }

    // ---------------------------------------------------------
    // 4. BOOKING SHOULD FAIL IF NOT ENOUGH SEATS
    // ---------------------------------------------------------
    @Test
    void testBookFlightInsufficientSeats() {
        Flight flight = new Flight("FL200", "Dallas",
                LocalDateTime.now(), 2);

        service.addFlight(flight);

        Reservation res = service.bookFlight("Alice", flight, 5);

        assertNull(res);
        assertEquals(2, flight.getAvailableSeats(), "Seats should remain unchanged");
    }

    // ---------------------------------------------------------
    // 5. GET RESERVATIONS FOR CUSTOMER
    // ---------------------------------------------------------
    @Test
    void testGetReservationsForCustomer() {
        Flight f1 = new Flight("FL101", "Boston",
                LocalDateTime.now(), 10);

        service.addFlight(f1);

        service.bookFlight("John", f1, 2);

        List<Reservation> list = service.getReservationsForCustomer("John");

        assertEquals(1, list.size());
        assertEquals("FL101", list.get(0).getFlight().getFlightNumber());
    }

    // ---------------------------------------------------------
    // 6. GET RESERVATIONS FOR CUSTOMER — NONE FOUND
    // ---------------------------------------------------------
    @Test
    void testGetReservationsForCustomerNone() {
        List<Reservation> list = service.getReservationsForCustomer("Nobody");
        assertTrue(list.isEmpty());
    }

    // ---------------------------------------------------------
    // 7. CANCEL RESERVATION — SUCCESS
    // ---------------------------------------------------------
    @Test
    void testCancelReservationRestoresSeats() {
        Flight flight = new Flight("FL555", "Chicago",
                LocalDateTime.now(), 5);

        service.addFlight(flight);

        service.bookFlight("Bob", flight, 2);

        boolean cancelled = service.cancelReservation("Bob", "FL555");

        assertTrue(cancelled);
        assertEquals(5, flight.getAvailableSeats(), "Seats must be restored");
    }

    // ---------------------------------------------------------
    // 8. CANCEL RESERVATION — FAILS IF NOT FOUND
    // ---------------------------------------------------------
    @Test
    void testCancelReservationNotFound() {
        Flight flight = new Flight("FL777", "Dallas",
                LocalDateTime.now(), 20);

        service.addFlight(flight);

        // Booking by someone else
        service.bookFlight("John", flight, 3);

        boolean cancelled = service.cancelReservation("Bob", "FL777");

        assertFalse(cancelled);
        assertEquals(17, flight.getAvailableSeats(), "Seats should not change");
    }
}
