public class Room {
    private String roomId;
    private double price;

    public Room(String roomId, double price) {
        this.roomId = roomId;
        this.price = price;
    }

    public String getRoomId() { return roomId; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return "Room{" + "roomId='" + roomId + "', price=" + price + "}";
    }
}
