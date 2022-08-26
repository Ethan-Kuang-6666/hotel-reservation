package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.RoomType;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    private static AdminResource adminResource = null;
    private static CustomerService customerService = CustomerService.getCustomerService();
    private static ReservationService reservationService = ReservationService.getReservationService();

    private AdminResource() {

    }

    public static AdminResource getAdminResource() {
        if (adminResource == null) {
            adminResource = new AdminResource();
        }
        return adminResource;
    }

    // note: may throw exception
    public Customer getCustomer(String email) {
        Customer c = customerService.getCustomer(email);
        if (c == null) {
            throw new IllegalArgumentException("Please enter a valid email address");
        }
        return c;
    }

    public void addRoom(List<IRoom> rooms) {
        for (IRoom room : rooms) {
            reservationService.addRoom(room);
        }
    }

    public void addARoom(String roomNumber, Double price, RoomType enumeration) {
        reservationService.addRoom(roomNumber,price,enumeration);
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        for (Reservation r : reservationService.getAllReservations()) {
            System.out.println(r);
        }
    }

}
