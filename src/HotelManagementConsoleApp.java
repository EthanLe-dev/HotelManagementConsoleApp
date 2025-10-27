import java.util.Scanner;

public class HotelManagementConsoleApp {
    public static void main(String[] args) {
        // Đối tượng chung cho cả chương trình
        Scanner sc = new Scanner(System.in);
        ClearConsoleScreen cleaner = new ClearConsoleScreen();    // Đối tượng dùng để xóa trắng màn hình

        // Khởi tạo tất cả đối tượng Quản lý
        RoomManager rm = new RoomManager(sc, cleaner);
        EmployeeManager em = new EmployeeManager(sc, cleaner);
        ServiceManagement sm = new ServiceManagement(sc, cleaner);
        PromotionManager pm = new PromotionManager();
        CustomerManager cm = new CustomerManager(sc);
        BookingManager bm = new BookingManager(cm, rm, sc);
        BillManager billManager = new BillManager(sm, pm, bm);

        // Đọc tất cả file DATA
        readAllData(rm, em, sm, pm, cm, bm, billManager);

        // Lưu dữ liệu lần cuối trước khi thoát chương trình
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nĐang lưu dữ liệu trước khi thoát...");
            saveAllData(rm, em, sm ,pm, cm, bm, billManager);
        }));

        // Menu chính
        int choice;
        while (true) {
            cleaner.clearScreen();
            System.out.println("------Ứng dụng quản lý khách sạn Nhóm 11------");
            System.out.println("1.Quản lý phòng");          // Quý
            System.out.println("2.Quản lý nhân viên");      // Quý
            System.out.println("3.Quản lý khách hàng");     // Phú
            System.out.println("4.Quản lý đặt phòng");      // Phú
            System.out.println("5.Quản lý hóa đơn");        // Thịnh
            System.out.println("6.Quản lý dịch vụ");        // Thái
            System.out.println("7.Quản lý ưu đãi");         // Thái
            System.out.println("0.Thoát chương trình");

            try {
                System.out.print("Nhập lựa chọn: ");
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số từ 0-7");
                continue;
            }

            switch (choice) {
                case 1:
                    rm.showMenu();
                    break;
                case 2:
                    em.showMenu();
                    break;
                case 3:
                    cm.showMenu();
                    break;
                case 4:
                    bm.showMenu();
                    break;
                case 5:
                    billManager.showMenu();
                    break;
                case 6:
                    sm.showMenu();
                    break;
                case 7:
                    pm.showMenu();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Vui lòng nhập số từ 0-7");
            }
        }
    }

    public static void readAllData(RoomManager rm, EmployeeManager em, ServiceManagement sm, PromotionManager pm,
                                   CustomerManager cm, BookingManager bm, BillManager billManager) {
        rm.readFromFile();
        em.readFromFile();
        sm.readFromFile();
        pm.readFromFile();
        cm.readFromFile();
        bm.readFromFile();
        billManager.readFromFile();
    }

    public static void saveAllData(RoomManager rm, EmployeeManager em, ServiceManagement sm, PromotionManager pm,
                                   CustomerManager cm, BookingManager bm, BillManager billManager) {
        rm.saveToFile();
        em.saveToFile();
        sm.saveToFile();
        pm.saveToFile();
        cm.saveToFile();
        bm.saveToFile();
        billManager.saveToFile();
    }
}
