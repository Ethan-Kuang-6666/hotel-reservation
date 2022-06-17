package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;
    private String emailRegex = "^(.+)@(.+).com$";
    Pattern pattern = Pattern.compile(emailRegex);
    private List<Reservation> reservations = new ArrayList<Reservation>();

    // Constructor
    // note: it may throw exception.
    public Customer(String firstName, String lastName, String email) {
        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Error, Invalid email");
        } else {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    @Override
    public String toString() {
        return "First Name: " + firstName + " Last Name: " + lastName +
                " Email: " + email;
    }
}
