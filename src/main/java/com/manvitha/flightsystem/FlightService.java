package com.manvitha.flightsystem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FlightService {

    private final List<Flight> flights = new ArrayList<>();
    private final List<Reservation> reservations = new ArrayList<>();

    // ------------------ ADD FLIGHT ------------------
    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    // ------------------ GET ALL FLIGHTS ------------------
    public List<Flight> getAllFlights() {
        return flights;
    }

    // ------------------ SEARCH FLIGHTS ------------------
    public List<Flight> searchFlights(String destination, LocalDateTime date) {
        List<Flight> result = new ArrayList<>();

        if (destination == null || destination.isBlank()) {
            return result;
        }

        LocalDate searchDate = date.toLocalDate();

        for (Flight f : flights) {
            boolean destMatch = f.getDestination().equalsIgnoreCase(destination);
            boolean sameDay = f.getDepartureTime().toLocalDate().equals(searchDate);

            if (destMatch && sameDay) {
                result.add(f);
            }
        }

        // Sort by earliest departure
        result.sort((a, b) -> a.getDepartureTime().compareTo(b.getDepartureTime()));

        return result;
    }

    // ------------------ BOOK FLIGHT ------------------
    public Reservation bookFlight(String customerName, Flight flight, int seats) {

        if (flight.getAvailableSeats() < seats) {
            return null; // Not enough seats → booking fails
        }

        // Deduct seats
        flight.setAvailableSeats(flight.getAvailableSeats() - seats);

        // Create reservation
        Reservation reservation = new Reservation(customerName, flight, seats);
        reservations.add(reservation);

        return reservation;
    }

    // ------------------ GET RESERVATIONS BY NAME ------------------
    public List<Reservation> getReservationsForCustomer(String customerName) {
        List<Reservation> list = new ArrayList<>();

        for (Reservation r : reservations) {
            if (r.getCustomerName().equalsIgnoreCase(customerName)) {
                list.add(r);
            }
        }

        return list;
    }

    // ------------------ CANCEL RESERVATION ------------------
    public boolean cancelReservation(String customerName, String flightNumber) {

        Iterator<Reservation> iterator = reservations.iterator();

        while (iterator.hasNext()) {
            Reservation r = iterator.next();

            boolean matchCustomer = r.getCustomerName().equalsIgnoreCase(customerName);
            boolean matchFlight = r.getFlight().getFlightNumber().equalsIgnoreCase(flightNumber);

            if (matchCustomer && matchFlight) {

                // Restore seats
                Flight fl = r.getFlight();
                fl.setAvailableSeats(fl.getAvailableSeats() + r.getSeatsBooked());

                iterator.remove();
                return true;
            }
        }

        return false; // Nothing removed
    }

    // ------------------ FIND FLIGHT BY NUMBER ------------------
    public Flight getFlightByNumber(String flightNumber) {
        if (flightNumber == null || flightNumber.isBlank()) return null;

        for (Flight f : flights) {
            if (f.getFlightNumber().equalsIgnoreCase(flightNumber)) {
                return f;
            }
        }
        return null;
    }
}
