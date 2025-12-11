package com.manvitha.flightsystem;

import java.time.LocalDateTime;

public class Flight {
    private String flightNumber;
    private String destination;
    private LocalDateTime departureTime;
    private int availableSeats;

    public Flight(String flightNumber, String destination, LocalDateTime departureTime, int availableSeats) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.departureTime = departureTime;
        this.availableSeats = availableSeats;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    // 🔥 REQUIRED FOR BOOKING & CANCELLATION
    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public String toString() {
        return flightNumber + " | " + destination + " | " + departureTime + " | Seats: " + availableSeats;
    }
}
