import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BillManager {
    private List<Bill> billList = new ArrayList<>();
    private CustomerManager customerManager;
    private RoomManager roomManager;
    private EmployeeManager employeeManager;

    public BillManager(CustomerManager customerManager, RoomManager roomManager, EmployeeManager employeeManager) {
        this.customerManager = customerManager;
        this.roomManager = roomManager;
        this.employeeManager = employeeManager;
    }

    // ====== 1. Thêm hóa đơn ======
    public void addBill() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhập tên khách hàng: ");
        String name = sc.nextLine();
        Customer c = customerManager.searchByName(name);
        if (c == null) {
            System.out.println("⚠️ Khách hàng chưa tồn tại. Tạo mới...");
            c = customerManager.addNewCustomer();
        }

        System.out.print("Nhập ID phòng: ");
        String roomId = sc.nextLine();
        Room r = roomManager.searchByID(roomId);
        if (r == null) {
            System.out.println("❌ Không tìm thấy phòng!");
            return;
        }

        System.out.print("Nhập số ngày thuê: ");
        int days = Integer.parseInt(sc.nextLine());

        List<Service> usedServices = new ArrayList<>();
        System.out.println("Nhập tên dịch vụ sử dụng (gõ 'x' để dừng): ");
        while (true) {
            String sName = sc.nextLine();
            if (sName.equalsIgnoreCase("x")) break;
            Service s = employeeManager.searchServiceByName(sName);
            if (s != null) {
                usedServices.add(s);
                System.out.println("✅ Đã thêm dịch vụ: " + s.getServiceName());
            } else {
                System.out.println("❌ Không tìm thấy dịch vụ này!");
            }
        }

        double total = r.getPrice() * days;
        for (Service s : usedServices) total += s.getPrice();

        Bill bill = new Bill(UUID.randomUUID().toString(), c, r, usedServices, days, total);
        billList.add(bill);

        System.out.println("✅ Hóa đơn đã được tạo thành công!");
        bill.printBill();
    }

    // ====== 2. Tìm hóa đơn ======
    public void findBillByNameOrRoom() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập tên khách hàng hoặc ID phòng cần tìm: ");
        String key = sc.nextLine();

        boolean found = false;
        for (Bill bill : billList) {
            if (bill.getCustomer().getName().equalsIgnoreCase(key)
                    || bill.getRoom().getRoomId().equalsIgnoreCase(key)) {
                bill.printBill();
                found = true;
            }
        }
        if (!found) System.out.println("❌ Không tìm thấy hóa đơn nào phù hợp!");
    }

    // ====== 3. Lưu thống kê ra file ======
    public void saveStatisticToFile() {
        double totalRevenue = billList.stream().mapToDouble(Bill::getTotal).sum();

        Map<String, Integer> roomCount = new HashMap<>();
        Map<String, Integer> customerCount = new HashMap<>();
        Map<String, Integer> serviceCount = new HashMap<>();

        for (Bill b : billList) {
            roomCount.merge(b.getRoom().getRoomId(), 1, Integer::sum);
            customerCount.merge(b.getCustomer().getName(), 1, Integer::sum);
            for (Service s : b.getServices()) {
                serviceCount.merge(s.getServiceName(), 1, Integer::sum);
            }
        }

        try (FileWriter w = new FileWriter("HotelData.txt")) {
            w.write("=== THỐNG KÊ KHÁCH SẠN ===\n");
            w.write("Tổng doanh thu: " + totalRevenue + " VND\n");
            w.write("Phòng được đặt nhiều nhất: " + findMost(roomCount) + "\n");
            w.write("Phòng được đặt ít nhất: " + findLeast(roomCount) + "\n");
            w.write("Dịch vụ được dùng nhiều nhất: " + findMost(serviceCount) + "\n");
            w.write("Dịch vụ được dùng ít nhất: " + findLeast(serviceCount) + "\n");
            w.write("Khách hàng đặt nhiều nhất: " + findMost(customerCount) + "\n");
            w.write("Khách hàng đặt ít nhất: " + findLeast(customerCount) + "\n");
            System.out.println("✅ Đã lưu thống kê ra file HotelData.txt!");
        } catch (IOException e) {
            System.out.println("❌ Lỗi khi ghi file!");
        }
    }

    private String findMost(Map<String, Integer> map) {
        return map.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Không có dữ liệu");
    }

    private String findLeast(Map<String, Integer> map) {
        return map.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Không có dữ liệu");
    }
}
