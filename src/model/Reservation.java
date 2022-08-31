package model;

import java.util.Date;
import java.util.Objects;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;

    // Constructor
    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString() {
        return "Reservation\n" + customer.getName() + "\n" +
                "Room: " + room.getRoomNumber() +
                " - " + room.getRoomType() + "\n" +
                "Price: $" + room.getRoomPrice() + " price per night" +
                "\n" + "Check-in Date: " + checkInDate + "\n" +
                "Checkout Date: " + checkOutDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation that)) return false;
        return customer.equals(that.customer) && getRoom().equals(that.getRoom()) && getCheckInDate().equals(that.getCheckInDate()) && getCheckOutDate().equals(that.getCheckOutDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, getRoom(), getCheckInDate(), getCheckOutDate());
    }
}
