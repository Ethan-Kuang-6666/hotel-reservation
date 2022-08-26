package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    private static HotelResource hotelResource = null;
    private static CustomerService customerService = CustomerService.getCustomerService();
    private static ReservationService reservationService = ReservationService.getReservationService();

    private HotelResource() {

    }

    public static HotelResource getHotelResource() {
        if (hotelResource == null) {
            hotelResource = new HotelResource();
        }
        return hotelResource;
    }

    // note: may throw exception
    public Customer getCustomer(String email) {
        Customer c = customerService.getCustomer(email);
        if (c == null) {
            throw new IllegalArgumentException("Please enter a valid customer email");
        }
        return c;
    }

    // note: may throw exception
    public void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    // note: may throw exception
    public IRoom getRoom(String roomNumber) {
        IRoom r = reservationService.getARoom(roomNumber);
        if (r == null) {
            throw new IllegalArgumentException("Please enter a valid customer email");
        }
        return r;
    }

    // note: customer should already have an account.
    // note: may throw exception
    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer c = getCustomer(customerEmail);
        if (c == null) {
            throw new IllegalArgumentException("Please enter a valid customer email");
        }
        return reservationService.reserveRoom(c, room, checkInDate, checkOutDate);
    }

    // note: may throw exception
    public Collection<Reservation> getCustomerReservation(String customerEmail) {
        Customer c = getCustomer(customerEmail);
        if (c == null) {
            throw new IllegalArgumentException("Please enter a valid customer email");
        }
        return c.getReservations();
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }
}
