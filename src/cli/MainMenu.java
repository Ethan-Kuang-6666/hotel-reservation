package cli;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {
    private static void displayMainMenu() {
        System.out.println();
        System.out.println("Welcome to the Hotel Reservation Application");
        System.out.println("----------------------------------------");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("----------------------------------------");
        System.out.println("Please select a number for the menu option");
        System.out.println();
    }

    public static void mainMenuControl() {
        displayMainMenu();
        Scanner inputReader = new Scanner(System.in);
        String userInput = inputReader.nextLine();
        HotelResource hr = HotelResource.getHotelResource();
        while (!userInput.equals("5")) {
            switch (userInput) {
                case "1":
                    findAndBookAvailableRooms();
                    break;
                case "2":
                    seeMyReservations();
                    break;
                case "3":
                    createAccount();
                    break;
                case "4":
                    AdminMenu.adminMenuControl();
                    break;
                default:
                    System.out.println("Error: Invalid Input!");
            }
            displayMainMenu();
            userInput = inputReader.nextLine();
        }
    }


    private static void createAccount() {
        Scanner inputReader = new Scanner(System.in);
        HotelResource hr = HotelResource.getHotelResource();
        String email;
        String firstName;
        String lastName;
        while (true) {
            try {
                System.out.println("Enter Email format: name@domain.com");
                email = inputReader.nextLine();
                System.out.println("First Name");
                firstName = inputReader.nextLine();
                System.out.println("Last Name");
                lastName = inputReader.nextLine();
                hr.createACustomer(email, firstName, lastName);
                break;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static void findAndBookAvailableRooms() {
        Scanner inputReader = new Scanner(System.in);
        HotelResource hr = HotelResource.getHotelResource();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date checkInDate;
        Date checkOutDate;
        String checkInDateString;
        String checkOutDateString;
        while (true) {
            try {
                System.out.println("Enter checkIn Date mm/dd/yyyy example 02/01/2020");
                checkInDateString = inputReader.nextLine();
                checkInDate = formatter.parse(checkInDateString);
                break;
            } catch (ParseException e) {
                e.printStackTrace();
                System.out.println("Error: Invalid Input");
            }
        }
        while (true) {
            try {
                System.out.println("Enter checkOut Date mm/dd/yyyy example 02/01/2020");
                checkOutDateString = inputReader.nextLine();
                checkOutDate = formatter.parse(checkOutDateString);
                if (checkOutDate.after(checkInDate)) {
                    break;
                } else {
                    System.out.println("CheckOut date should be after checkIn date!");
                }
            } catch (ParseException e) {
                e.printStackTrace();
                System.out.println("Error: Invalid Input");
            }
        }
        Collection<IRoom> availableRooms = hr.findARoom(checkInDate, checkOutDate);
        for (IRoom room : availableRooms) {
            System.out.println(room);
        }
        reserveARoom(availableRooms, checkInDate, checkOutDate);
    }

    private static void reserveARoom(Collection<IRoom> availableRooms, Date checkInDate, Date checkOutDate) {
        Scanner inputReader = new Scanner(System.in);
        HotelResource hr = HotelResource.getHotelResource();
        while (true) {
            System.out.println("Would you like to book a room? y/n");
            String wantBook = inputReader.nextLine();
            if (wantBook.equals("y")) {
                while (true) {
                    System.out.println("Do you have an account? y/n");
                    String hasAccount = inputReader.nextLine();
                    if (hasAccount.equals("n")) {
                        createAccount();
                        break;
                    } else if (hasAccount.equals("y")) {
                        break;
                    } else {
                        System.out.println("Please enter y(yes) or n(no)");
                    }
                }
                break;
            } else if (wantBook.equals("n")) {
                break;
            } else {
                System.out.println("Please enter y(yes) or n(no)");
            }
        }

        String email;
        String roomNumber;
        Customer customer;
        IRoom targetRoom;
        while (true) {
            System.out.println("Enter Email format: name@domain.com");
            email = inputReader.nextLine();
            try {
                customer = hr.getCustomer(email);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        while (true) {
            System.out.println("What room number would you like to reserve");
            roomNumber = inputReader.nextLine();
            try {
                targetRoom = hr.getRoom(roomNumber);
                if (!availableRooms.contains(targetRoom)) {
                    throw new IllegalArgumentException("Please choose a valid room!");
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println(hr.bookARoom(customer, targetRoom, checkInDate, checkOutDate));
    }


    private static void seeMyReservations() {
        Scanner inputReader = new Scanner(System.in);
        HotelResource hr = HotelResource.getHotelResource();
        System.out.println("Enter Email format: name@domain.com");
        String email;
        Customer customer;
        while (true) {
            email = inputReader.nextLine();
            try {
                customer = hr.getCustomer(email);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        for (Reservation r : hr.getCustomerReservation(customer)) {
            System.out.println(r);
        }
    }
}
