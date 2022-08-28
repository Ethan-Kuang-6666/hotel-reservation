package cli;

import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.RoomType;

import java.util.Collection;
import java.util.Scanner;

public class AdminMenu {

    private static void displayAdminMenu() {
        System.out.println();
        System.out.println("Admin Menu");
        System.out.println("----------------------------------------");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu");
        System.out.println("----------------------------------------");
        System.out.println("Please select a number for the menu option");
        System.out.println();
    }

    public static void adminMenuControl() {
        displayAdminMenu();
        Scanner inputReader = new Scanner(System.in);
        String userInput = inputReader.nextLine();
        AdminResource ar = AdminResource.getAdminResource();
        while (!userInput.equals("5")) {
            switch (userInput) {
                case "1":
                    seeAllCustomers();
                    break;
                case "2":
                    seeAllRooms();
                    break;
                case "3":
                    ar.displayAllReservations();
                    break;
                case "4":
                    addNewRoom();
                    break;
                default:
                    System.out.println("Error: Invalid Input!");
            }
            displayAdminMenu();
            userInput = inputReader.nextLine();
        }
    }


    private static void seeAllCustomers() {
        AdminResource ar = AdminResource.getAdminResource();
        Collection<Customer> customers = ar.getAllCustomers();
        for (Customer c : customers) {
            System.out.println(c);
        }
    }

    private static void seeAllRooms() {
        AdminResource ar = AdminResource.getAdminResource();
        Collection<IRoom> rooms = ar.getAllRooms();
        for (IRoom r : rooms) {
            System.out.println(r);
        }
    }

    private static void addNewRoom() {
        Scanner inputReader = new Scanner(System.in);
        AdminResource ar = AdminResource.getAdminResource();
        System.out.println("Enter room number");
        String roomNumber = inputReader.nextLine();
        Double price;
        while (true) {
            try {
                System.out.println("Enter price per night");
                String inputPrice = inputReader.nextLine();
                price = Double.parseDouble(inputPrice);
                break;
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        RoomType roomType;
        while (true) {
            System.out.println("Enter room type: 1 for single bed, 2 for double bed");
            String inputRoomType = inputReader.nextLine();
            if (inputRoomType.equals("1")) {
                roomType = RoomType.SINGLE;
                break;
            } else if (inputRoomType.equals("2")) {
                roomType = RoomType.DOUBLE;
                break;
            } else {
                System.out.println("Error: Invalid input!");
            }
        }
        ar.addARoom(roomNumber, price, roomType);
        System.out.println("Would you like to add another room y/n");
        while (true) {
            String addAnother = inputReader.nextLine();
            if (addAnother.equals("y")) {
                addNewRoom();
                break;
            } else if (addAnother.equals("n")) {
                break;
            } else {
                System.out.println("Please enter y (Yes) or n (No)");
            }
        }
    }
}
