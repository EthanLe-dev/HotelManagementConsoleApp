import java.util.*;

public class Bill {
    private int billID;
    private Booking booking;    // Tham chiếu đến tên khách, ID phòng và Tổng tiền phòng
    private ArrayList<Service> services;
    private double finalPrice;
    private Promotion appliedPromotion;
    private String employeeName;
    private static int billCount = 0;

    // Constructor cho hàm thêm Bill mới
    public Bill(Booking booking, Promotion appliedPromotion, String employeeName) {
        ++billCount;
        this.billID = billCount;
        this.booking = booking;
        services = new ArrayList<>();
        this.appliedPromotion = appliedPromotion;
        this.employeeName = employeeName;
    }

    // Constructor cho hàm đọc/ghi file
    public Bill(Booking booking, ArrayList<Service> services, double finalPrice, Promotion appliedPromotion, String employeeName) {
        ++billCount;
        this.billID = billCount;
        this.booking = booking;
        this.services = services;
        this.finalPrice = finalPrice;
        this.appliedPromotion = appliedPromotion;
        this.employeeName = employeeName;
    }

    public int getBillID() {
        return billID;
    }

    public Booking getBooking() {
        return booking;
    }

    public String showThisBillServices() {
        StringBuilder thisBillServices = new StringBuilder();
        if (this.services.isEmpty())
            return "";
        for (Service s : this.services) {
            thisBillServices.append(s.getName());
            thisBillServices.append(",");
        }
        return thisBillServices.toString();
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public Promotion getAppliedPromotion() {
        return appliedPromotion;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public static int getBillCount() {
        return billCount;
    }

    @Override
    public String toString() {
        String appliedPromotionName;
        if (this.appliedPromotion != null) {
            appliedPromotionName = appliedPromotion.getName();
        }
        else appliedPromotionName = "Chưa áp dụng";

        return String.format("%03d | %s | %d | %s | %.0f | %s | %s",
                getBillID(), getBooking().getCustomerName(), getBooking().getRoomID(),
                showThisBillServices(), getFinalPrice(), getEmployeeName(), appliedPromotionName);
    }
}
