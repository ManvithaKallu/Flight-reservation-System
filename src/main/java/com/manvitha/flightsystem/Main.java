package com.manvitha.flightsystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final FlightService flightService = new FlightService();

    public static void main(String[] args) {

        seedFlights();
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Select option: ");

            switch (choice) {
                case 1:
                    handleSearchFlights();
                    break;
                case 2:
                    handleBookFlight();
                    break;
                case 3:
                    handleViewReservations();
                    break;
                case 4:
                    handleCancelReservation();
                    break;
                case 5:
                    System.out.println("Exiting system. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1–5.");
            }
        }
    }

    // ------------------ MENU ------------------
    private static void printMenu() {
        System.out.println("\n========== FLIGHT RESERVATION SYSTEM ==========");
        System.out.println("1. Search Flights");
        System.out.println("2. Book Flight");
        System.out.println("3. View Reservations");
        System.out.println("4. Cancel Reservation");
        System.out.println("5. Exit");
        System.out.println("===============================================");
    }

    // ------------------ INPUT HELPERS ------------------
    private static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static LocalDateTime readDateTime(String message) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        while (true) {
            try {
                System.out.print(message);
                String input = scanner.nextLine().trim();
                return LocalDateTime.parse(input, fmt);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Use yyyy-MM-dd HH:mm");
            }
        }
    }

    // ------------------ SEARCH FLIGHTS ------------------
    private static void handleSearchFlights() {
        System.out.print("Destination: ");
        String dest = scanner.nextLine().trim();

        LocalDateTime dt = readDateTime("Date (yyyy-MM-dd HH:mm): ");

        List<Flight> flights = flightService.searchFlights(dest, dt);

        if (flights.isEmpty()) {
            System.out.println("No flights found.");
            return;
        }

        System.out.println("\nAvailable Flights:");
        for (Flight f : flights) {
            System.out.println(f);
        }
    }

    // ------------------ BOOK FLIGHT ------------------
    private static void handleBookFlight() {
        System.out.print("Customer name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Flight number: ");
        String flightNum = scanner.nextLine().trim();

        Flight flight = flightService.getFlightByNumber(flightNum);

        if (flight == null) {
            System.out.println("Flight not found.");
            return;
        }

        int seats = readInt("Seats to book: ");
        if (seats <= 0) {
            System.out.println("Seat count must be positive.");
            return;
        }

        Reservation reservation = flightService.bookFlight(name, flight, seats);
        if (reservation == null) {
            System.out.println("Booking failed. Not enough seats.");
        } else {
            System.out.println("Booking successful!");
            System.out.println(reservation);
        }
    }

    // ------------------ VIEW RESERVATIONS ------------------
    private static void handleViewReservations() {
        System.out.print("Customer name: ");
        String name = scanner.nextLine().trim();

        List<Reservation> list = flightService.getReservationsForCustomer(name);

        if (list.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }

        System.out.println("\nReservations for " + name + ":");
        for (Reservation r : list) {
            System.out.println(r);
        }
    }

    // ------------------ CANCEL RESERVATION ------------------
    private static void handleCancelReservation() {

        System.out.print("Customer name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Flight number to cancel: ");
        String flightNum = scanner.nextLine().trim();

        boolean success = flightService.cancelReservation(name, flightNum);

        if (success) {
            System.out.println("Reservation cancelled.");
        } else {
            System.out.println("No matching reservation found.");
        }
    }

    // ------------------ SEED INITIAL FLIGHTS ------------------
    private static void seedFlights() {
        flightService.addFlight(new Flight("FL100", "New York", LocalDateTime.of(2025, 1, 12, 10, 0), 50));
        flightService.addFlight(new Flight("FL200", "Miami", LocalDateTime.of(2025, 1, 12, 12, 0), 20));
        flightService.addFlight(new Flight("FL300", "New York", LocalDateTime.of(2025, 1, 12, 8, 30), 10));
    }
}
