import java.util.Objects;

public class Room {
    private final int roomID;
    private final String roomType;
    private double roomPrice;
    public final static int maxOfRooms = 60;

    // Constructor cho hàm thêm phòng
    public Room(int roomID, double roomPrice) {
        this.roomID = roomID;
        this.roomType = checkRoomType(roomID, roomPrice);
        this.roomPrice = roomPrice;
    }

    // Constructor cho hàm đọc file
    public Room(int roomID, String roomType, double roomPrice) {
        this.roomID = roomID;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
    }

    public int getRoomID() { return roomID;}

    public String getRoomType() { return roomType;}

    public double getRoomPrice() { return roomPrice;}

    public void setRoomPrice(double roomPrice) { this.roomPrice = roomPrice;}

    public void showRoomInfo() {
            System.out.printf("ID Phòng: %d | %s | Giá: %.0f\n", roomID, roomType, roomPrice);
    }

    public static String checkRoomType(int roomID, double roomPrice) {
        if (Objects.equals(checkValidID(roomID), checkValidPrice(roomPrice))) {
            return checkValidID(roomID);
        }
        else
            return "ID và giá phòng không hợp lệ";
    }

    public static String checkValidPrice(double roomPrice) {
        if (roomPrice > 0 && roomPrice <= 500000)
            return "Standard";
        else
            return "Suite";
    }

    public static String checkValidID(int roomID) {
        int flour = roomID/100;
        return switch (flour) {
            case 1, 2 -> "Standard";
            case 3, 4 -> "Suite";
            default -> null;
        };
    }
}