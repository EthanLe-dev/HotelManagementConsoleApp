import java.util.*;

public class Bill {
    private String billId;
    private Customer customer;
    private Room room;
    private List<Service> services;
    private int days;
    private double total;

    public Bill(String billId, Customer customer, Room room, List<Service> services, int days, double total) {
        this.billId = billId;
        this.customer = customer;
        this.room = room;
        this.services = services;
        this.days = days;
        this.total = total;
    }

    public Customer getCustomer() { return customer; }
    public Room getRoom() { return room; }
    public List<Service> getServices() { return services; }
    public double getTotal() { return total; }

    public void printBill() {
        System.out.println("===== BILL INFORMATION =====");
        System.out.println("Bill ID: " + billId);
        System.out.println("Customer: " + customer.getName());
        System.out.println("Room: " + room.getRoomId());
        System.out.println("Days: " + days);
        System.out.println("Services used:");
        for (Service s : services) {
            System.out.println("  - " + s.getServiceName() + " (" + s.getPrice() + ")");
        }
        System.out.println("Total: " + total);
        System.out.println("============================");
    }
}
