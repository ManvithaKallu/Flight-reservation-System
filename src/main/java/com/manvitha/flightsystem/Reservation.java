package com.manvitha.flightsystem;

public class Reservation {

    private final String customerName;
    private final Flight flight;
    private final int seatsBooked;

    public Reservation(String customerName, Flight flight, int seatsBooked) {
        this.customerName = customerName;
        this.flight = flight;
        this.seatsBooked = seatsBooked;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Flight getFlight() {
        return flight;
    }

    public int getSeatsBooked() {
        return seatsBooked;
    }

    @Override
    public String toString() {
        return "Reservation{customer='" + customerName + "', flight=" +
                flight.getFlightNumber() + ", seats=" + seatsBooked + "}";
    }
}
