public class Booking {
    private int bookingID;
    private String customerName;
    private int roomID;
    private String checkInDate;
    private int numOfDays;
    private double totalPrice;

    public Booking(int bookingID, String customerName, int roomID, String checkInDate, int numOfDays, double totalPrice) {
        this.bookingID = bookingID;
        this.customerName = customerName;
        this.roomID = roomID;
        this.checkInDate = checkInDate;
        this.numOfDays = numOfDays;
        this.totalPrice = totalPrice;
    }

    // getter/setter
    public int getBookingID() { return bookingID; }
    public void setBookingID(int bookingID) { this.bookingID = bookingID; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public int getRoomID() { return roomID; }
    public void setRoomID(int roomID) { this.roomID = roomID; }

    public String getCheckInDate() { return checkInDate; }
    public void setCheckInDate(String checkInDate) { this.checkInDate = checkInDate; }

    public int getNumOfDays() { return numOfDays; }
    public void setNumOfDays(int numOfDays) { this.numOfDays = numOfDays; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    @Override
    public String toString() {
        return String.format("%-5d | %-15s | %-5d | %-12s | %-5d | %.2f",
                bookingID, customerName, roomID, checkInDate, numOfDays, totalPrice);
    }
}