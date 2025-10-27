import java.util.*;
import java.io.*;

public class BillManager implements FileHandler{
    private String filename = "data/Bill";
    private List<Bill> billList;
    private ServiceManagement serviceManager;
    private PromotionManager promotionManager;
    private BookingManager bookingManager;

    public BillManager(ServiceManagement serviceManager, PromotionManager promotionManager, BookingManager bookingManager) {
        this.serviceManager = serviceManager;
        this.promotionManager = promotionManager;
        this.bookingManager = bookingManager;
        billList = new ArrayList<>();
    }

    // ======== HIỂN THỊ BILL ========
    public void displayAllBills() {
        if (billList.isEmpty()) {
            System.out.println(" Chưa có Bill nào.");
            return;
        }
        for (Bill b : billList)
            System.out.println(b);
    }
//
    // ======== TÌM BILL ========
    public Bill findBillById(int id) {
        for (Bill b : billList)
            if (b.getBillID() == id) return b;
        return null;
    }

    public List<Bill> findBillsByCustomerName(String name) {
        List<Bill> result = new ArrayList<>();
        for (Bill b : billList)
            if (b.getBooking().getCustomerName().equalsIgnoreCase(name))
                result.add(b);
        return result;
    }

    public List<Bill> findBillsByRoomId(int roomId) {
        List<Bill> result = new ArrayList<>();
        for (Bill b : billList)
            if (b.getBooking().getRoomID() == roomId)
                result.add(b);
        return result;
    }

    // ======== XÓA BILL ========
    public void removeBill(int billID) {
        Bill b = findBillById(billID);
        if (b != null) {
            billList.remove(b);
            System.out.println(" Đã xóa Bill.");
        } else System.out.println(" Không tìm thấy Bill!");
    }

    // ======== GHI FILE ========
    public void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            String header = "ID Bill | Khách | ID phòng | dịch vụ sử dụng | tổng tiền | tên lễ tân | CTKM đã áp dụng";
            bw.write(header);
            bw.newLine();

            for (Bill b : billList) {
                String line = String.format("%03d,%s,%d,%s,%.0f,%s,%s",
                        b.getBillID(), b.getBooking().getCustomerName(), b.getBooking().getRoomID(),
                        b.showThisBillServices(), b.getFinalPrice(), b.getEmployeeName(), b.getAppliedPromotion());

                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("\nLỗi ghi dữ liệu vào file Bill\n");
        }
    }

    // ======== ĐỌC FILE ========
    public void readFromFile() {
        billList.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] info = line.split(",");

                int billID = Integer.parseInt(info[0].trim());
                String customerName = info[1].trim();

                Booking booking = bookingManager.searchBookingByCustomerName(customerName);

                double finalPrice = Double.parseDouble(info[info.length-3].trim());
                String employeeName = info[info.length-2].trim();
                Promotion promotion = promotionManager.findPromotionByName(info[info.length-1].trim());

                ArrayList<Service> services = new ArrayList<>();
                for (int i = 3; i < info.length-3; i++) {
                    Service thisBillService = serviceManager.searchServiceByName(info[i].trim());
                    if (thisBillService != null) {
                        services.add(thisBillService);
                    }
                }

                Bill newBill = new Bill(booking, services, finalPrice, promotion, employeeName);
                billList.add(newBill);
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc dữ liệu từ file Bill");
        }
    }

    // ======== MENU CONSOLE ========
    public void showMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n=== QUẢN LÝ BILL ===");
            System.out.println("1. Hiển thị tất cả Bill");
            System.out.println("2. Tìm Bill theo ID");
            System.out.println("3. Tìm Bill theo tên khách");
            System.out.println("4. Tìm Bill theo ID phòng");
            System.out.println("5. Xóa Bill");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            choice = sc.nextInt(); sc.nextLine();

            switch(choice) {
                case 1:
                    displayAllBills();
                    break;
                case 2:
                    System.out.print("Nhập ID Bill: ");
                    Bill b = findBillById(Integer.parseInt(sc.nextLine().trim()));
                    System.out.println(b != null ? b : " Không tìm thấy Bill!");
                    break;
                case 3:
                    System.out.print("Nhập tên khách: ");
                    List<Bill> list1 = findBillsByCustomerName(sc.nextLine());
                    if (list1.isEmpty()) System.out.println(" Không tìm thấy Bill!");
                    else list1.forEach(System.out::println);
                    break;
                case 4:
                    System.out.print("Nhập ID phòng: ");
                    List<Bill> list2 = findBillsByRoomId(Integer.parseInt(sc.nextLine().trim()));
                    if (list2.isEmpty()) System.out.println(" Không tìm thấy Bill!");
                    else list2.forEach(System.out::println);
                    break;
                case 5:
                    System.out.print("Nhập mã Bill muốn xóa: ");
                    int billID = Integer.parseInt(sc.nextLine().trim());
                    removeBill(billID);
                    break;
                case 0:
                    saveToFile();
                    break;
                default:
                    System.out.println(" Lựa chọn không hợp lệ!");
            }
        } while(choice != 0);
    }
}
