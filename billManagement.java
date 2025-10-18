import java.io.*;
import java.util.*;

// CLASS KHÁCH HÀNG
class KhachHang {
    private String customerId;
    private String customerName;

    public KhachHang(String customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public String getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }

    public void hienThiThongTin() {
        System.out.println("Mã KH: " + customerId + " | Tên: " + customerName);
    }
}

// CLASS PHÒNG
class Phong {
    private String roomId;
    private double roomPrice;

    public Phong(String roomId, double roomPrice) {
        this.roomId = roomId;
        this.roomPrice = roomPrice;
    }

    public String getRoomId() { return roomId; }
    public double getRoomPrice() { return roomPrice; }

    public void hienThiThongTin() {
        System.out.println("Phòng: " + roomId + " | Giá: " + roomPrice);
    }
}

// CLASS DỊCH VỤ
class DichVu {
    private String serviceName;
    private double servicePrice;

    public DichVu(String serviceName, double servicePrice) {
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
    }

    public String getServiceName() { return serviceName; }
    public double getServicePrice() { return servicePrice; }

    public void hienThiThongTin() {
        System.out.println("Dịch vụ: " + serviceName + " | Giá: " + servicePrice);
    }
}

// CLASS HÓA ĐƠN
class HoaDon {
    private String billId;
    private KhachHang customer;
    private Phong room;
    private ArrayList<DichVu> serviceList;
    private int rentalDays;
    private double totalAmount;

    public HoaDon(String billId, KhachHang customer, Phong room, ArrayList<DichVu> serviceList, int rentalDays) {
        this.billId = billId;
        this.customer = customer;
        this.room = room;
        this.serviceList = serviceList;
        this.rentalDays = rentalDays;
        tinhTongTien();
    }

    public void tinhTongTien() {
        totalAmount = room.getRoomPrice() * rentalDays;
        for (DichVu s : serviceList) {
            totalAmount += s.getServicePrice();
        }
    }

    public double getTotalAmount() { return totalAmount; }
    public KhachHang getCustomer() { return customer; }
    public Phong getRoom() { return room; }

    public void hienThiHoaDon() {
        System.out.println("\n HÓA ĐƠN KHÁCH SẠN ");
        System.out.println("Mã hóa đơn: " + billId);
        System.out.println("Tên khách: " + customer.getCustomerName());
        System.out.println("Phòng: " + room.getRoomId() + " | Giá: " + room.getRoomPrice());
        System.out.println("Số ngày thuê: " + rentalDays);
        System.out.println(" Dịch vụ sử dụng ");
        for (DichVu s : serviceList) {
            s.hienThiThongTin();
        }
        System.out.println("TỔNG CỘNG: " + totalAmount + " VND");
        System.out.println("========================\n");
    }

    public void hienThiNgan() {
        System.out.printf("%-10s | %-15s | %-8s | %-10.0f\n",
                billId, customer.getCustomerName(), room.getRoomId(), totalAmount);
    }

    public String toFileString() {
        return billId + ";" +
                customer.getCustomerId() + ";" +
                customer.getCustomerName() + ";" +
                room.getRoomId() + ";" +
                room.getRoomPrice() + ";" +
                rentalDays + ";" +
                totalAmount;
    }
}

// CLASS QUẢN LÝ KHÁCH SẠN
public class quanlyhoadon {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<KhachHang> customerList = new ArrayList<>();
    static ArrayList<Phong> roomList = new ArrayList<>();
    static ArrayList<HoaDon> billList = new ArrayList<>();

    // 1️⃣ Tạo dữ liệu mẫu
    public static void sampleData() {
        customerList.add(new KhachHang("KH01", "Nguyen Van A"));
        customerList.add(new KhachHang("KH02", "Tran Thi B"));

        roomList.add(new Phong("P101", 500000));
        roomList.add(new Phong("P102", 700000));
    }

    // 2️⃣ Thêm hóa đơn
    public static void addBill() {
        System.out.print("Nhập mã hóa đơn: ");
        String billId = sc.nextLine();

        System.out.print("Nhập ID khách hàng: ");
        String customerId = sc.nextLine();
        KhachHang c = findCustomerById(customerId);
        if (c == null) {
            System.out.println("❌ Không tìm thấy khách hàng!");
            return;
        }

        System.out.print("Nhập ID phòng: ");
        String roomId = sc.nextLine();
        Phong r = findRoomById(roomId);
        if (r == null) {
            System.out.println("❌ Không tìm thấy phòng!");
            return;
        }

        System.out.print("Nhập số ngày thuê: ");
        int days = Integer.parseInt(sc.nextLine());

        ArrayList<DichVu> serviceList = new ArrayList<>();
        while (true) {
            System.out.print("Thêm dịch vụ (nhập 'x' để dừng): ");
            String serviceName = sc.nextLine();
            if (serviceName.equalsIgnoreCase("x")) break;
            System.out.print("Giá dịch vụ: ");
            double price = Double.parseDouble(sc.nextLine());
            serviceList.add(new DichVu(serviceName, price));
        }

        HoaDon b = new HoaDon(billId, c, r, serviceList, days);
        billList.add(b);
        System.out.println("✅ Thêm hóa đơn thành công!");
        b.hienThiHoaDon();
    }

    // 3️⃣ Tìm khách hàng theo ID
    public static KhachHang findCustomerById(String id) {
        for (KhachHang c : customerList)
            if (c.getCustomerId().equalsIgnoreCase(id)) return c;
        return null;
    }

    // 4️⃣ Tìm phòng theo ID
    public static Phong findRoomById(String id) {
        for (Phong r : roomList)
            if (r.getRoomId().equalsIgnoreCase(id)) return r;
        return null;
    }

    // 5️⃣ Hiển thị tất cả hóa đơn
    public static void showAllBills() {
        if (billList.isEmpty()) {
            System.out.println("❗ Danh sách hóa đơn trống!");
            return;
        }
        System.out.println("\n===== DANH SÁCH HÓA ĐƠN =====");
        System.out.printf("%-10s | %-15s | %-8s | %-10s\n", "Mã HĐ", "Tên KH", "Phòng", "Tổng tiền");
        for (HoaDon b : billList) {
            b.hienThiNgan();
        }
    }

    // 6️⃣ Tìm hóa đơn theo tên khách hoặc ID phòng
    public static void searchBill() {
        System.out.print("Nhập tên khách hoặc ID phòng cần tìm: ");
        String key = sc.nextLine();
        boolean found = false;

        for (HoaDon b : billList) {
            if (b.getCustomer().getCustomerName().equalsIgnoreCase(key)
                    || b.getRoom().getRoomId().equalsIgnoreCase(key)) {
                b.hienThiHoaDon();
                found = true;
            }
        }

        if (!found) System.out.println("❌ Không tìm thấy hóa đơn!");
    }

    // 7️⃣ Tính tổng doanh thu
    public static void calculateTotalRevenue() {
        double total = 0;
        for (HoaDon b : billList) total += b.getTotalAmount();
        System.out.println("💰 Tổng doanh thu: " + total + " VND");
    }

    // 8️⃣ Ghi file
    public static void writeToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("hoadon.txt"))) {
            for (HoaDon b : billList) {
                bw.write(b.toFileString());
                bw.newLine();
            }
            System.out.println("💾 Ghi file thành công!");
        } catch (IOException e) {
            System.out.println("❌ Lỗi khi ghi file!");
        }
    }

    // 9️⃣ Đọc file
    public static void readFromFile() {
        billList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("hoadon.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(";");
                if (p.length >= 7) {
                    KhachHang c = new KhachHang(p[1], p[2]);
                    Phong r = new Phong(p[3], Double.parseDouble(p[4]));
                    ArrayList<DichVu> dvList = new ArrayList<>();
                    HoaDon b = new HoaDon(p[0], c, r, dvList, Integer.parseInt(p[5]));
                    billList.add(b);
                }
            }
            System.out.println("📂 Đọc file thành công!");
        } catch (IOException e) {
            System.out.println("❌ Lỗi khi đọc file!");
        }
    }

    // 🔟 Menu chính
    public static void menu() {
        sampleData();
        while (true) {
            System.out.println("\n QUẢN LÝ KHÁCH SẠN ");
            System.out.println("1. Thêm hóa đơn");
            System.out.println("2. Hiển thị danh sách hóa đơn");
            System.out.println("3. Tìm hóa đơn theo tên hoặc ID phòng");
            System.out.println("4. Tính tổng doanh thu");
            System.out.println("5. Ghi file hóa đơn");
            System.out.println("6. Đọc file hóa đơn");
            System.out.println("0. Thoát");
            System.out.print("👉 Chọn: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1": addBill(); break;
                case "2": showAllBills(); break;
                case "3": searchBill(); break;
                case "4": calculateTotalRevenue(); break;
                case "5": writeToFile(); break;
                case "6": readFromFile(); break;
                case "0": System.out.println("🚪 Thoát chương trình!"); return;
                default: System.out.println("❗ Lựa chọn không hợp lệ!");
            }
        }
    }

    public static void main(String[] args) {
        menu();
    }
}

