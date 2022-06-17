package cli;

import api.HotelResource;
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
                    findAndBookAvailableRooms(inputReader,hr);
                    break;
                case "2":
                    seeMyReservations(inputReader, hr);
                    break;
                case "3":
                    createAccount(inputReader, hr);
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


    private static void createAccount(Scanner inputReader, HotelResource hr) {
        System.out.println("Enter Email format: name@domain.com");
        String email = inputReader.nextLine();
        System.out.println("First Name");
        String firstName = inputReader.nextLine();
        System.out.println("Last Name");
        String lastName = inputReader.nextLine();
        while (true) {
            try {
                hr.createACustomer(email, firstName, lastName);
                break;
            } catch (IllegalArgumentException ex) {
                ex.getLocalizedMessage();
            }
        }
    }

    private static void findAndBookAvailableRooms(Scanner inputReader, HotelResource hr) {
        SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");
        Date checkInDate;
        Date checkOutDate;
        System.out.println("Enter checkIn Date mm/dd/yyyy example 02/01/2020");
        String checkInDateString = inputReader.nextLine();
        while (true) {
            try {
                checkInDate = formatter.parse(checkInDateString);
                break;
            } catch (ParseException e) {
                e.printStackTrace();
                System.out.println("Error: Invalid Input");
            }
        }
        System.out.println("Enter checkOut Date mm/dd/yyyy example 02/01/2020");
        String checkOutDateString = inputReader.nextLine();
        while (true) {
            try {
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

        reserveARoom(inputReader, hr, availableRooms, checkInDate, checkOutDate);
    }

    private static void reserveARoom(Scanner inputReader, HotelResource hr,
                                     Collection<IRoom> availableRooms, Date checkInDate, Date checkOutDate) {
        while (true) {
            System.out.println("Would you like to book a room? y/n");
            String wantBook = inputReader.nextLine();
            if (wantBook.equals("y")) {
                while (true) {
                    System.out.println("Do you have an account? y/n");
                    String hasAccount = inputReader.nextLine();
                    if (hasAccount.equals("n")) {
                        createAccount(inputReader,hr);
                        break;
                    } else if (hasAccount.equals("y")) {
                        break;
                    }
                }
                break;
            } else if (wantBook.equals("n")) {
                break;
            }
        }

        System.out.println("Enter Email format: name@domain.com");
        String email;
        String roomNumber;
        while (true) {
            email = inputReader.nextLine();
            if (hr.getCustomer(email) != null) {
                break;
            }
            System.out.println("Error: Invalid Input");
        }
        System.out.println("What room number would you like to reserve");
        while (true) {
            roomNumber = inputReader.nextLine();
            if (hr.getRoom(roomNumber) != null) {
                break;
            }
            System.out.println("Error: Invalid Input");
        }

        System.out.println(hr.bookARoom(email, hr.getRoom(roomNumber), checkInDate, checkOutDate));
    }


    private static void seeMyReservations(Scanner inputReader, HotelResource hr) {
        System.out.println("Enter Email format: name@domain.com");
        String email;
        while (true) {
            email = inputReader.nextLine();
            if (hr.getRoom(email) != null) {
                break;
            }
            System.out.println("Error: Invalid Input");
        }
        for (Reservation r : hr.getCustomerReservation(email)) {
            System.out.println(r);
        }
    }
}
