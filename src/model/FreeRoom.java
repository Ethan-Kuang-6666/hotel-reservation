package model;

public class FreeRoom extends Room {

    // Constructor
    public FreeRoom(String roomNumber, RoomType enumeration) {
        super(roomNumber, 0.0, enumeration);
    }

    @Override
    public boolean isFree() {
        return true;
    }

    @Override
    public String toString() {
        return "Room Number: " + getRoomNumber() +
                " " + getRoomType() + " Room Price: $" + getRoomPrice() +
                " Free Room!";
    }
}
