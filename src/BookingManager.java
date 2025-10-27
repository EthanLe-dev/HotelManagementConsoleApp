import java.io.*;
import java.util.*;

public class BookingManager implements FileHandler{
    private static final File f = new File("data/BookingData");
    private final List<Booking> bookingList;
    private final Scanner sc;
    private final CustomerManager customerManager;
    private final RoomManager roomManager;

    public BookingManager(CustomerManager customerManager, RoomManager roomManager, Scanner sc) {
        this.customerManager = customerManager;
        this.roomManager = roomManager;
        bookingList = new ArrayList<>();
        this.sc = sc;
    }

    // ====== Sinh ID tự động ======
    private int generateNewBookingID() {
        return bookingList.stream().mapToInt(Booking::getBookingID).max().orElse(0) + 1;
    }

    // ====== Tính giảm giá theo hạng khách ======
    private double calculateDiscount(String rank) {
        if (rank.equalsIgnoreCase("Silver")) return 0.05;
        if (rank.equalsIgnoreCase("Gold")) return 0.10;
        return 0;
    }

    // ====== Kiểm tra phòng có trùng lịch hoặc đang còn trong thời gian đặt ======
    private boolean isRoomAvailable(int roomID, String checkInDate, int numDays) {
        for (Booking b : bookingList) {
            if (b.getRoomID() == roomID) {
                // Nếu phòng trùng ID => kiểm tra xem còn trong thời gian đặt
                // Đơn giản hóa: chỉ cần trùng ngày check-in hoặc chưa hết số ngày là báo trùng
                if (b.getCheckInDate().equalsIgnoreCase(checkInDate)) {
                    return false; // trùng ngày check-in
                }
            }
        }
        return true;
    }

    // ====== Thêm booking ======
    public void addBooking() {
        System.out.print("Nhập tên khách hàng: ");
        String name = sc.nextLine();
        Customer customer = customerManager.findCustomerByName(name);

        if (customer == null) {
            System.out.println("❌ Không tìm thấy khách hàng trong hệ thống. Vui lòng thêm khách hàng trước.");
            return;
        }

        System.out.println("✅ Khách hàng tìm thấy: " + customer);

        // Nếu khách này đã có booking => hiển thị thông tin các booking hiện có
        for (Booking b : bookingList) {
            if (b.getCustomerName().equalsIgnoreCase(name)) {
                System.out.printf("BookingID: %d | Phòng: %d | CheckIn: %s | Số ngày: %d | Tổng tiền: %.0f\n",
                        b.getBookingID(), b.getRoomID(), b.getCheckInDate(), b.getNumOfDays(), b.getTotalPrice());
            }
        }

        System.out.print("Bạn có muốn đặt thêm phòng? (Y/N): ");
        String opt = sc.nextLine().trim().toUpperCase();
        if (!opt.equals("Y")) {
            System.out.println("Thoát khỏi chức năng đặt phòng.");
            return;
        }

        int roomID;
        String checkInDate;
        int days;

        while (true) {
            System.out.print("Nhập ID phòng: ");
            roomID = Integer.parseInt(sc.nextLine());

            Room room = roomManager.searchByID(String.valueOf(roomID));
            if (room == null) {
                System.out.println("❌ Không tìm thấy phòng với ID này. Nhập lại!");
                continue;
            }

            System.out.print("Nhập ngày check-in (dd/mm/yyyy): ");
            checkInDate = sc.nextLine();

            System.out.print("Nhập số ngày đặt: ");
            days = Integer.parseInt(sc.nextLine());

            if (!isRoomAvailable(roomID, checkInDate, days)) {
                System.out.println("⚠️ Phòng này đã có người đặt hoặc chưa trống. Vui lòng chọn phòng khác!");
            } else {
                break;
            }
        }

        // Tính tiền
        double roomPrice = roomManager.searchByID(String.valueOf(roomID)).getRoomPrice();
        double discount = calculateDiscount(customer.getMemberRank());
        double total = roomPrice * days * (1 - discount);

        int bookingID = generateNewBookingID();
        Booking newBooking = new Booking(bookingID, name, roomID, checkInDate, days, total);
        bookingList.add(newBooking);

        // Tăng usageCount cho khách
        customer.setUsageCount(customer.getUsageCount() + 1);
        customer.updateMemberRank();

        System.out.println("🎉 Đặt phòng thành công! Mã đặt phòng: " + bookingID);
        System.out.printf("Tổng tiền: %.0f (Giảm %.0f%% theo hạng %s)\n",
                total, discount * 100, customer.getMemberRank());
    }

    // ====== Sửa booking ======
    public void updateBooking() {
        System.out.print("Nhập Booking ID cần sửa: ");
        int id = Integer.parseInt(sc.nextLine());
        for (Booking b : bookingList) {
            if (b.getBookingID() == id) {
                System.out.printf("BookingID: %d | %s | Phòng %d | CheckIn: %s | Ngày: %d | Tổng: %.0f\n",
                        b.getBookingID(), b.getCustomerName(), b.getRoomID(), b.getCheckInDate(), b.getNumOfDays(), b.getTotalPrice());
                System.out.print("Nhập số ngày mới: ");
                int days = Integer.parseInt(sc.nextLine());
                b.setNumOfDays(days);
                System.out.println("✅ Cập nhật thành công!");
                return;
            }
        }
        System.out.println("❌ Không tìm thấy Booking ID này!");
    }

    // ====== Xóa booking ======
    public void deleteBooking() {
        System.out.print("Nhập Booking ID cần xóa: ");
        int id = Integer.parseInt(sc.nextLine());
        boolean removed = bookingList.removeIf(b -> b.getBookingID() == id);
        if (removed) System.out.println("🗑️ Đã xóa đặt phòng thành công!");
        else System.out.println("❌ Không tìm thấy mã đặt phòng!");
    }

    // ====== Xem lịch đặt theo tên khách ======
    public void viewBookingByCustomer() {
        System.out.print("Nhập tên khách hàng: ");
        String name = sc.nextLine();
        boolean found = false;
        for (Booking b : bookingList) {
            if (b.getCustomerName().equalsIgnoreCase(name)) {
                System.out.println(b);
                found = true;
            }
        }
        if (!found) System.out.println("❌ Không tìm thấy lịch đặt phòng cho khách này!");
    }

    public void showAllBookings() {
        for(Booking b : bookingList) {
            System.out.println(b);
        }
    }

    public Booking searchBookingByID(int ID) {
        for (Booking b : bookingList) {
            if (b.getBookingID() == ID)
                return b;
        }
        return null;
    }

    public Booking searchBookingByCustomerName(String name) {
        for (Booking b : bookingList) {
            if (b.getCustomerName().equalsIgnoreCase(name))
                return b;
        }
        return null;
    }

    // ====== MENU ======
    public void showMenu() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ ĐẶT PHÒNG ---");
            System.out.println("1. Thêm đặt phòng");
            System.out.println("2. Sửa đặt phòng");
            System.out.println("3. Xóa đặt phòng");
            System.out.println("4. Xem lịch đặt theo khách hàng");
            System.out.println("5. Xem tất cả lịch đặt");
            System.out.println("0. Lưu & Thoát");
            System.out.print("Chọn: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> addBooking();
                case "2" -> updateBooking();
                case "3" -> deleteBooking();
                case "4" -> viewBookingByCustomer();
                case "5" -> showAllBookings();
                case "0" -> {
                    saveToFile();
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ====== Đọc dữ liệu booking từ file ======
    public void readFromFile() {
        bookingList.clear();
        if (!f.exists()) {
            System.out.println("Không tìm thấy file dữ liệu đặt phòng");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            br.readLine(); // bỏ tiêu đề
            String line;
            while ((line = br.readLine()) != null) {
                String[] info = line.split(",");
                int bookingID = Integer.parseInt(info[0]);
                String customerName = info[1];
                int roomID = Integer.parseInt(info[2]);
                String checkInDate = info[3];
                int numDays = Integer.parseInt(info[4]);
                double totalPrice = Double.parseDouble(info[5]);

                bookingList.add(new Booking(bookingID, customerName, roomID, checkInDate, numDays, totalPrice));
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc dữ liệu từ file BookingData");
        }
    }

    // ====== Lưu dữ liệu booking vào file ======
    public void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            bw.write("BookingID,CustomerName,RoomID,CheckInDate,NumOfDays,TotalPrice");
            bw.newLine();

            for (Booking b : bookingList) {
                String line = String.format("%d,%s,%d,%s,%d,%.2f",
                        b.getBookingID(), b.getCustomerName(), b.getRoomID(),
                        b.getCheckInDate(), b.getNumOfDays(), b.getTotalPrice());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi lưu dữ liệu vào file BookingData");
        }
    }
}