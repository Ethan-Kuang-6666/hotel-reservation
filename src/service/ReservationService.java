package service;
import model.*;

import java.util.*;

public class ReservationService {
    private static ReservationService reservationService = null;
    private static List<Reservation> reservations = new ArrayList<Reservation>();
    private static Map<String, IRoom> rooms = new HashMap<String, IRoom>();

    // private constructor
    private ReservationService() {

    }

    // get the instance
    public static ReservationService getReservationService() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public void addRoom(String roomNumber, Double price, RoomType enumeration) {
        rooms.put(roomNumber, new Room(roomNumber, price, enumeration));
    }

    public Collection<IRoom> getRooms() {
        return rooms.values();
    }

    // return null if room not found.
    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }

    // We have to make sure that the room is free.
    public Reservation reserveRoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        customer.addReservation(reservation);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> results = rooms.values();
        for (Reservation reservation : reservations) {
            if (results.contains(reservation.getRoom())) {
                if (checkInDate.before(reservation.getCheckOutDate()) &&
                        checkOutDate.after(reservation.getCheckInDate())) {
                    results.remove(reservation.getRoom());
                }
            }
        }
        return results;
    }

    Collection<Reservation> getCustomersReservation(Customer customer) {
        return customer.getReservations();
    }

    public Collection<Reservation> getAllReservations() {
        return reservations;
    }

    void printAllReservation() {
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }
}
