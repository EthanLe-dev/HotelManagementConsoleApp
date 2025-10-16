import java.util.Objects;

public class Room {
    private final int roomID;
    private final String roomType;
    private double roomPrice;
    private boolean isAvailable;
    public final static int maxOfRooms = 20;

    // Constructor cho hàm thêm phòng
    public Room(int roomID, String roomType, double roomPrice) {
        this.roomID = roomID;
        this.roomType = checkRoomType(roomID, roomPrice);
        this.roomPrice = roomPrice;
        this.isAvailable = true;
    }

    // Constructor cho hàm đọc file
    public Room(int roomID, String roomType, double roomPrice, boolean isAvailable) {
        this.roomID = roomID;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.isAvailable = isAvailable;
    }

    public int getRoomID() { return roomID;}

    public String getRoomType() { return roomType;}

    public double getRoomPrice() { return roomPrice;}

    public boolean getIsAvailable() { return isAvailable;}

    public void setRoomPrice(double roomPrice) { this.roomPrice = roomPrice;}

    public void reverseRoomStatus() { this.isAvailable = !this.isAvailable;}

    public static boolean roomIsMax() {
        if (RoomManager.getRoomListSize() >= Room.maxOfRooms) {
            System.out.println("Số lượng phòng đạt tối đa (20 phòng)");
            return true;
        }
        return false;
    }

    public static String checkRoomType(int roomID, double roomPrice) {
        if (Objects.equals(checkValidID(roomID), checkValidPrice(roomPrice))) {
            return checkValidID(roomID);
        }
        else
            return "ID và giá phòng không hợp lệ";
    }

    public static String checkValidID(int roomID) {
        int flour = roomID/100;
        return switch (flour) {
            case 1, 2 -> "Standard";
            case 3, 4 -> "Suite";
            default -> null;
        };
    }

    public static String checkValidPrice(double roomPrice) {
        if (roomPrice > 0 && roomPrice <= 500000)
            return "Standard";
        else
            return "Suite";
    }

    public void showRoomInfo() {
        if (isAvailable)
            System.out.printf("ID Phòng: %d | %s | Giá: %.0f | Còn trống\n", roomID, roomType, roomPrice);
        else
            System.out.printf("ID Phòng: %d | %s | Giá: %.0f | Đã đặt\n", roomID, roomType, roomPrice);
    }
}